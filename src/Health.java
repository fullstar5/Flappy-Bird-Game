import bagel.Image;

/**
 * Health class, contain attributes and adjustment of health bar
 */
public class Health {
    private final Image FULL_LIFE  = new Image("res/level/fullLife.png");
    private final Image NO_LIFE = new Image("res/level/noLife.png");
    private int maxLife;
    private int currentLife;
    private final int Heart_Y = 15;
    private int heart_X = 100;
    private final int HEART_GAP = 50;


    // Constrictor
    /**
     * Initialised health for level 0
     */
    public Health(){
        maxLife = 3;
        currentLife = 3;
    }



    // Getters
    /**
     * get current health
     * @return
     */
    public int getCurrentLife() {
        return currentLife;
    }



    // Setters
    /**
     * set max life value when level up
     * @param maxLife
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * set currentLife value when hitting the pipe/flame
     * @param currentLife
     */
    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }


    /**
     * update number of full heart and empty heart
     */
    public void update(){
        for (int i = 0; i < maxLife; i++){
            if (i < currentLife){
                FULL_LIFE.drawFromTopLeft(heart_X + (i * HEART_GAP), Heart_Y);
            }
            else {
                NO_LIFE.drawFromTopLeft(heart_X + (i * HEART_GAP), Heart_Y);
            }
        }

    }


}
