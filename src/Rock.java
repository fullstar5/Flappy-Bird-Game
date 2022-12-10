import bagel.Image;
import bagel.Input;

/**
 * sub-class of weapon
 */
public class Rock extends Weapon{
    private final int SHOOT_RANGE = 125;
    private final Image rock = new Image("res/level-1/rock.png");

    // Constructor
    /**
     * basic setting for Rock
     * @param rock
     */
    public Rock(Image rock){ super(rock); }


    /**
     * update rock state, same as bomb
     * @param input
     * @param bird
     * @param timeScale
     */
    public void update(Input input, Bird bird, int timeScale){
        super.update(input, bird, timeScale);
        super.renderWeapon(SHOOT_RANGE);
    }

}
