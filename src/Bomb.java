import bagel.Image;
import bagel.Input;

/**
 * sub-class of Weapon, same as Rock
 */
public class Bomb extends Weapon{
    private final int SHOOT_RANGE = 250;
    private final Image bomb = new Image("res/level-1/bomb.png");

    // Constructor
    /**
     * basic setting
     * @param bomb
     */
    public Bomb(Image bomb){ super(bomb); }


    /**
     * update bomb state, how it influenced by user's input, bird and timeScale
     * @param input
     * @param bird
     * @param timeScale
     */
    public void update(Input input, Bird bird, int timeScale){
        super.update(input, bird, timeScale);
        super.renderWeapon(SHOOT_RANGE);
    }

}
