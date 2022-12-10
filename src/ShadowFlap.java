import bagel.*;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * SWEN20003 Project 2, Semester 2, 2021 (modified from project 1 solution written by Betty Lin)
 *
 * @author YiFei ZHANG
 */


/**
 * The main class of game
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE0 = new Image("res/level-0/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private Bird bird;
    private int score;
    private boolean gameOn;
    private boolean collision;
    private boolean flameCollision;
    private boolean win;


    // new attributes for A2
    private final Image PLASTIC_PIPE = new Image("res/level/plasticPipe.png");
    private final Image STEEL_PIPE = new Image("res/level-1/steelPipe.png");
    private final Image BACKGROUND_IMAGE1 = new Image("res/level-1/background.png");
    private final Image ROCK = new Image("res/level-1/rock.png");
    private final Image BOMB = new Image("res/level-1/bomb.png");
    private final String LEVEL_UP_MESSAGE = "LEVEL-UP!";
    private final String SHOOT_INSTRUCTION = "PRESS 'S' TO SHOOT";
    private boolean isLevelUP = false;
    private ArrayList<Pipe> plasticPipeSet = new ArrayList<>();
    private ArrayList<Pipe> level1PipeSet = new ArrayList<>();
    private int frameCount = 0;
    private boolean levelUp = false;
    private int levelUpCount = 0;
    private Health LifeBar = new Health();

    private int currentTimeScale = 1;
    private final int MAX_TIMESCALE = 5;
    private final int MIN_TIMESCALE = 1;

    private List<Integer> timeScaleSwitch = new ArrayList<>();
    private ArrayList<Weapon> weaponSets = new ArrayList<>();


    /**
     * Initialised basic game set
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        score = 0;
        gameOn = false;
        collision = false;
        win = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        // add time scale (pipe generate speed) value when game start
        timeScaleSwitch.add(100);
        timeScaleSwitch.add((int)(100 / 1.5));
        timeScaleSwitch.add((int)(100 / 1.5 / 1.5));
        timeScaleSwitch.add((int)(100 / 1.5 / 1.5 / 1.5));
        timeScaleSwitch.add((int)(100 / 1.5 / 1.5 / 1.5 / 1.5));

        if (!isLevelUP){
            BACKGROUND_IMAGE0.draw(Window.getWidth()/2, Window.getHeight()/2);
        }
        else {
            BACKGROUND_IMAGE1.draw(Window.getWidth()/2, Window.getHeight()/2);
        }

        if (input.wasPressed(Keys.ESCAPE)) { Window.close(); }

        // game has not started
        if (!gameOn) { renderInstructionScreen(input); }

        // game over
        if (LifeBar.getCurrentLife() == 0){ renderGameOverScreen(); }

        // out of bound
        if (birdOutOfBound()){
            if (LifeBar.getCurrentLife() > 0){
                LifeBar.setCurrentLife(LifeBar.getCurrentLife() - 1);
                bird.setY(350);
            }
        }

        // game won
        if (win) { renderWinScreen(); }

        // game is active
        if (gameOn && !(LifeBar.getCurrentLife() == 0) && !win) {
            // Level 0
            if (!levelUp){
                updateTimeScaleValue(input);
                if (frameCount % (timeScaleSwitch.get(currentTimeScale - 1)) == 0){
                    plasticPipeSet.add(new PlasticPipe(PLASTIC_PIPE, isLevelUP));
                }
                frameCount++;
                bird.update(input);
                Rectangle birdBox = bird.getBox();
                updatePipeSets(plasticPipeSet, birdBox);
                LifeBar.update();
            }

            // Level 1
            if (isLevelUP){
                updateTimeScaleValue(input);
                if (frameCount % (timeScaleSwitch.get(currentTimeScale - 1)) == 0){
                    randomAddPipe();
                }
                if (frameCount % (weaponGenerateTime() + 1) == 0){
                    randomAddWeapon();
                    weaponSets.get(weaponSets.size() - 1)
                            .setOverLap(overLapDetection(weaponSets.get(weaponSets.size() - 1).getWeaponBox(), level1PipeSet));
                }
                frameCount += 1;
                bird.update(input);
                Rectangle birdBox = bird.getBox();
                updateWeapons(birdBox, input);
                updatePipeSets(level1PipeSet, birdBox);
                LifeBar.update();
            }

        }

        // reset game when level up
        if (levelUp && !isLevelUP){
            renderLeveUpScreen();
            levelUpCount++;
            if (levelUpCount == 150){
                isLevelUP = true;
                gameOn = false;
                bird.setLevelUp(true);
                currentTimeScale = 1;
                LifeBar.setMaxLife(6);
                LifeBar.setCurrentLife(6);
                score = 0;
                frameCount = 0;
                bird.setY(350);
            }
        }
    }


    /**
     * Check whether bird out of bound
     * @return boolean, return true if bird out of bound
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * paint the instruction on screen
     * @param input user's input
     */
    public void renderInstructionScreen(Input input) {
        FONT.drawString(INSTRUCTION_MSG, (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (isLevelUP){
            FONT.drawString(SHOOT_INSTRUCTION, (Window.getWidth()/2 - (FONT.getWidth(SHOOT_INSTRUCTION)/2)),
                    (Window.getHeight()/2 - (FONT_SIZE/2) + 68));
        }
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * show gameover screen when all hearts depletes
     */
    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }


    /**
     * show final win screen when score reach 30
     */
    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }


    /**
     * used to detect whether bird (or other box) collide with other boxes
     * @param birdBox
     * @param topPipeBox
     * @param bottomPipeBox
     * @return boolean, return true if it has collision occur
     */
    public boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        return birdBox.intersects(topPipeBox) || birdBox.intersects(bottomPipeBox);
    }


    /**
     * used to record and update the scores, also detect whether it's level up or win the game
     * @param pipeSet
     */
    public void updateScore(Pipe pipeSet) {
        // update score when passing the pipe
        if (bird.getX() > pipeSet.getTopBox().right() && !pipeSet.isPass()) {
            Rectangle birdBox = bird.getBox();
            Rectangle pipeTopBox = pipeSet.getTopBox();
            Rectangle pipeBottomBox = pipeSet.getBottomBox();
            if (!detectCollision(birdBox, pipeTopBox, pipeBottomBox)){
                score += 1;

            }
            pipeSet.setPass(true);
        }
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, 100, 100);

        // detect level/win
        if (score == 10 && !isLevelUP) {
            levelUp = true;
        }
        if (score == 30){
            win = true;
        }
    }



    /**
     * shows level up screen
     */
    public void renderLeveUpScreen() {
        FONT.drawString(LEVEL_UP_MESSAGE, (Window.getWidth()/2.0-(FONT.getWidth(LEVEL_UP_MESSAGE)/2.0)), (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * allows the game to add random types of pipe
     */
    public void randomAddPipe(){
        Random random = new Random();
        if (random.nextInt(2) == 1){
            level1PipeSet.add(new SteelPipe(STEEL_PIPE, isLevelUP));
        }
        else {
            level1PipeSet.add(new PlasticPipe(PLASTIC_PIPE, isLevelUP));
        }
    }

    /**
     * used to generate weapons randomly
     */
    public void randomAddWeapon(){
        Random random = new Random();
        if (random.nextInt(2) == 1){
            weaponSets.add(new Rock(ROCK));
        }
        else {
            weaponSets.add(new Bomb(BOMB));
        }
    }


    /**
     * used to record and update current timeScale value
     * @param input
     */
    public void updateTimeScaleValue(Input input){
        if (input.wasPressed(Keys.K) && currentTimeScale > MIN_TIMESCALE){
            currentTimeScale -= 1;
        }
        if (input.wasPressed(Keys.L) && currentTimeScale < MAX_TIMESCALE){
            currentTimeScale += 1;
        }
    }


    /**
     * generate a number that used to generate weapons
     * @return int
     */
    public int weaponGenerateTime(){
        Random random = new Random();
        return random.nextInt(2500);
    }


    /**
     * update pipe/health/collision/flame/game state
     * @param pipeSets
     * @param birdBox
     */
    public void updatePipeSets(ArrayList<Pipe> pipeSets, Rectangle birdBox){
        for (Pipe pipeSet: pipeSets){
            // For plastic pipe
            pipeSet.update(currentTimeScale);
            Rectangle topPipeBox = pipeSet.getTopBox();
            Rectangle bottomPipeBox = pipeSet.getBottomBox();
            // detect collision, only can hit pipe once
            collision = detectCollision(birdBox, topPipeBox, bottomPipeBox);
            if (collision && !pipeSet.isCollide()){
                LifeBar.setCurrentLife(LifeBar.getCurrentLife() - 1);
                pipeSet.setCollide(true);
            }
            updateScore(pipeSet);

            // For steel pipe
            if (pipeSet.getPIPE_IMAGE().equals(STEEL_PIPE)){
                SteelPipe steelPipe = (SteelPipe) pipeSet;
                Rectangle topFlameBox = steelPipe.getTopFlameBox();
                Rectangle bottomFlameBox = steelPipe.getBottomFlameBox();
                if (steelPipe.getFrameCount() % 20 == 0){
                    // detect collision for flame
                    flameCollision = detectCollision(birdBox, topFlameBox, bottomFlameBox);
                    if (flameCollision && !steelPipe.isCollideWithFlame() && !pipeSet.isCollide()){
                        LifeBar.setCurrentLife(LifeBar.getCurrentLife() - 1);
                        steelPipe.setCollideWithFlame(true);
                        pipeSet.setCollide(true);
                    }
                }
            }
        }
    }


    /**
     * update current weapon state
     * @param birdBox rectangle
     * @param input
     */
    public void updateWeapons(Rectangle birdBox, Input input){
        for (Weapon weapon: weaponSets){
            Rectangle weaponBox = weapon.getWeaponBox();
            // if weapon is not being picked up/ overlap
            if (birdBox.intersects(weaponBox) && !weapon.isPickUP() && !weapon.isOverLap()){
                if (!bird.isHoldWeapon()){
                    bird.setHoldWeapon(true);
                    weapon.setPickUP(true);
                }
            }
            weapon.update(input, bird, currentTimeScale);
            checkWeaponPipeCollision(weapon);
        }
    }


    /**
     * Check whether weapon collide with pipe and reaction
     * @param weapon
     */
    public void checkWeaponPipeCollision(Weapon weapon){
        Rectangle weaponBox = weapon.getWeaponBox();
        for (Pipe pipe: level1PipeSet){
            Rectangle topPipeBox = pipe.getTopBox();
            Rectangle bottomPipeBox = pipe.getBottomBox();
            if (!weapon.isDisappear() && weapon.isShoot()){
                boolean weaponCollision = detectCollision(weaponBox, topPipeBox, bottomPipeBox);
                // if weapon collide with pipe, reaction depends on weapon type
                if (weapon.getWEAPON_IMAGE().equals(ROCK) && pipe.getPIPE_IMAGE().equals(PLASTIC_PIPE) && weaponCollision){
                    weapon.setDisappear(true);
                    pipe.setCollide(true);
                    pipe.setPass(true);
                    score += 1;
                }
                if (weapon.getWEAPON_IMAGE().equals(BOMB) && weaponCollision){
                    weapon.setDisappear(true);
                    pipe.setCollide(true);
                    pipe.setPass(true);
                    score += 1;
                }
            }
        }
    }


    /**
     * Check whether weapons overlap with pipe, if so, don't generate the weapon
     * @param weaponBox
     * @param pipeSets
     * @return boolean
     */
    public boolean overLapDetection(Rectangle weaponBox, ArrayList<Pipe> pipeSets){
        // make sure weapon don't overlap with pipe
        for (Pipe pipe: pipeSets){
            Rectangle topPipeBox = pipe.getTopBox();
            Rectangle bottomPipeBox = pipe.getBottomBox();
            boolean overLap = detectCollision(weaponBox, topPipeBox, bottomPipeBox);
            if (overLap) {
                return true;
            }
        }
        return false;
    }


}
