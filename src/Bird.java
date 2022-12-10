// modified from project 1 solution written by Betty Lin
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;


/**
 * Bird class, contains attributes for bird
 */
public class Bird {
    private final Image WING_DOWN_IMAGE0 = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_IMAGE0 = new Image("res/level-0/birdWingUp.png");
    private final Image WING_UP_IMAGE1 = new Image("res/level-1/birdWingUp.png");
    private final Image WING_DOWN_IMAGE1 = new Image("res/level-1/birdWingDown.png");
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private boolean isLevelUp = false;

    private boolean holdWeapon = false;

    // Constructor
    /**
     * basic setting for bird
     */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WING_UP_IMAGE0.getBoundingBoxAt(new Point(X, y));
        isLevelUp = false;
    }


    /**
     *
     * @param input
     * @return Rectangle
     */
    public Rectangle update(Input input) {
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            if (!isLevelUp){
                WING_DOWN_IMAGE0.draw(X, y);

            }
            else {
                WING_DOWN_IMAGE1.draw(X, y);
            }
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                if (!isLevelUp){
                    WING_UP_IMAGE0.draw(X, y);
                    boundingBox = WING_UP_IMAGE0.getBoundingBoxAt(new Point(X, y));

                }
                else {
                    WING_UP_IMAGE1.draw(X, y);
                    boundingBox = WING_UP_IMAGE1.getBoundingBoxAt(new Point(X, y));

                }
            }
            else {
                if (!isLevelUp){
                    WING_DOWN_IMAGE0.draw(X, y);
                    boundingBox = WING_DOWN_IMAGE0.getBoundingBoxAt(new Point(X, y));

                }
                else {
                    WING_DOWN_IMAGE1.draw(X, y);
                    boundingBox = WING_DOWN_IMAGE1.getBoundingBoxAt(new Point(X, y));
                }
            }
        }
        y += yVelocity;
        return boundingBox;
    }


    // Getters
    /**
     * get bird current y coordinate
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * get current x coordinate
     * @return double
     */
    public double getX() {
        return X;
    }

    /**
     * get bird box
     * @return Rectangle
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * get current isLevelUp boolean
     * @return boolean
     */
    public boolean isLevelUp() { return isLevelUp; }

    /**
     * get current isHoldWeapon boolean, check whether the bird holding a weapon
     * @return boolean
     */
    public boolean isHoldWeapon() { return holdWeapon; }



    // Setters
    /**
     * set y coordinate
     * @param y
     */
    public void setY(double y) { this.y = y; }


    /**
     * set LevelUp boolean
     * @param levelUp
     */
    public void setLevelUp(boolean levelUp) { isLevelUp = levelUp; }


    /**
     * set isHoldWeapon boolean
     * @param holdWeapon
     */
    public void setHoldWeapon(boolean holdWeapon) { this.holdWeapon = holdWeapon; }


}