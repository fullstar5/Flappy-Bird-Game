// modified from project 1 solution written by Betty Lin
import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * The pipe class, used to set and get most conditions of pipe
 */
public class Pipe {
    private  Image PIPE_IMAGE;
    private final int PIPE_GAP = 168;
    private double pipeSpeed = 5;
    private double TOP_PIPE_Y;
    private double BOTTOM_PIPE_Y;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();

    private final double LEVEL1_START_Y = 100;
    private final double LEVEL1_END_Y = 400;
    private boolean levelUP;
    private double RANDOM_POSITION;
    private double nextNumberForRand;


    private boolean isCollide = false;
    private boolean isPass = false;


    // Constructor
    /**
     * Initialised basic pipe statement
     * @param pipeImage
     * @param levelUP
     */
    public Pipe(Image pipeImage, boolean levelUP) {
        this.levelUP = levelUP;
        PIPE_IMAGE = pipeImage;
        // randomly generate pipe position for level 0
        if (!levelUP){
            Random random = new Random();
            nextNumberForRand = random.nextInt(3);
            if (nextNumberForRand == 0){
                RANDOM_POSITION = 100;
            }
            else if (nextNumberForRand == 1){
                RANDOM_POSITION = 300;
            }
            else {
                RANDOM_POSITION = 500;
            }
        }
        // randomly generate pipe position for level 1
        else{
            nextNumberForRand = new Random().nextDouble();
            RANDOM_POSITION = LEVEL1_START_Y + (nextNumberForRand * LEVEL1_END_Y);
        }
        TOP_PIPE_Y = RANDOM_POSITION - PIPE_IMAGE.getHeight()/2;
        BOTTOM_PIPE_Y = RANDOM_POSITION + PIPE_GAP + PIPE_IMAGE.getHeight()/2;
    }


    /**
     * If the pipe hasn't been collided, draw it
     */
    public void renderPipeSet() {
        if (!isCollide) {
            PIPE_IMAGE.draw(pipeX, TOP_PIPE_Y);
            PIPE_IMAGE.draw(pipeX, BOTTOM_PIPE_Y, ROTATOR);
        }
    }

    /**
     * update pipe and timeScale state
     * @param timeScale
     */
    public void update(int timeScale) {
        renderPipeSet();
        updateSpeed(timeScale);
    }

    /**
     * update the pipe fly speed when timeScale value change
     * @param timescale
     */
    public void updateSpeed(int timescale){
        pipeSpeed = 5;
        for (int i = 0; i < timescale; i++){
            pipeSpeed = pipeSpeed * 1.5;
        }
        pipeX -= pipeSpeed;
    }












    // Getters
    /**
     * get top pipe rectangle box
     * @return Rectangle
     */
    public Rectangle getTopBox() { return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TOP_PIPE_Y)); }

    /**
     * get bottom pipe rectangle box
     * @return Rectangle
     */
    public Rectangle getBottomBox() { return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BOTTOM_PIPE_Y)); }

    /**
     * get current isCollide boolean
     * @return boolean
     */
    public boolean isCollide() {
        return isCollide;
    }

    /**
     * get current isPass boolean
     * @return boolean
     */
    public boolean isPass() {
        return isPass;
    }

    /**
     * get x coordinate of pipe
     * @return double
     */
    public double getPipeX() { return pipeX; }

    /**
     * get top pipe y coordinate
     * @return double
     */
    public double getTOP_PIPE_Y() { return TOP_PIPE_Y; }

    /**
     * get bottom pipe y coordinate
     * @return double
     */
    public double getBOTTOM_PIPE_Y() { return BOTTOM_PIPE_Y; }

    /**
     * get pipe image
     * @return Image
     */
    public Image getPIPE_IMAGE() { return PIPE_IMAGE; }




    // Setters
    /**
     * set current isCollide boolean
     * @param collide
     */
    public void setCollide(boolean collide) {
        isCollide = collide;
    }

    /**
     * set current isPass boolean
     * @param pass
     */
    public void setPass(boolean pass) {
        isPass = pass;
    }
}
