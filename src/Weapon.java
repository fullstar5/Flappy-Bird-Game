import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;


/**
 * Weapon class, contains attributes and adjustment for weapons
 */
public class Weapon {
    private double weaponFlySpeed = 5;
    private double weaponX = Window.getWidth();
    private boolean isPickUP = false;
    private boolean isShoot = false;
    private boolean isDisappear = false;
    private boolean isOverLap = false;
    private int distance = 0;

    private double weaponY;
    private final Image WEAPON_IMAGE;
    private double randomPosition;
    private double nextNumberForRand;

    private final double WEAPON_START_Y = 100;
    private final double WEAPON_END_Y = 400;


    // Constructor
    /**
     * Initialised weapons
     * @param weaponImage
     */
    public Weapon(Image weaponImage){
        this.WEAPON_IMAGE = weaponImage;
        nextNumberForRand = new Random().nextDouble();
        randomPosition = WEAPON_START_Y + (nextNumberForRand * WEAPON_END_Y);
        weaponY = randomPosition;
    }


    /**
     * update weapon state as user press 'S' and how it influenced by bird and timeScale
     * @param input
     * @param bird
     * @param timeScale
     */
    public void update(Input input, Bird bird, int timeScale){
        updateSpeed(timeScale);
        // while weapon is not pick up
        if (!isPickUP){
            this.weaponX -= weaponFlySpeed;
        }
        // when weapon pick up
        else {
            if (bird.isHoldWeapon() && !isShoot){
                if (input.wasPressed(Keys.S)){
                    bird.setHoldWeapon(false);
                    this.isShoot = true;
                }
                setWeaponX(bird.getBox().right());
                setWeaponY(bird.getY());
            }
        }
    }


    /**
     * update weapon fly speed
     * @param timeScale
     */
    public void updateSpeed(int timeScale){
        // calculate fly speed when timeScale change
        weaponFlySpeed = 5;
        for (int i = 0; i < timeScale; i++){
            weaponFlySpeed = weaponFlySpeed * 1.5;
        }
    }


    /**
     * draw weapon in different situation
     * @param shootRange
     */
    public void renderWeapon(int shootRange){
        if (isShoot){
            // if shoot weapon, fly until reach shootRange
            if (distance <= shootRange){
                this.weaponX += weaponFlySpeed;
                distance += 5;
            }
            else {
                this.isDisappear = true;
            }
        }
        if (!isDisappear && !isOverLap){
            WEAPON_IMAGE.draw(weaponX, weaponY);
        }
    }






    // Getters
    /**
     * get weapon box
     * @return Rectangle
     */
    public Rectangle getWeaponBox() { return WEAPON_IMAGE.getBoundingBoxAt(new Point(weaponX, weaponY)); }

    /**
     * get isOverLap boolean, used to check whether weapon overlap with pipe
     * @return boolean
     */
    public boolean isOverLap() { return isOverLap; }

    /**
     * get isPickUp boolean, check whether current being picked up
     * @return boolean
     */
    public boolean isPickUP() { return isPickUP; }

    /**
     * get isDisappear boolean
     * @return boolean
     */
    public boolean isDisappear() { return isDisappear; }

    /**
     * get isShoot boolean
     * @return boolean
     */
    public boolean isShoot() { return isShoot; }

    /**
     * get Weapon Image
     * @return Image
     */
    public Image getWEAPON_IMAGE() { return WEAPON_IMAGE; }






    // Setters

    /**
     * set Weapon x
     * @param weaponX
     */
    public void setWeaponX(double weaponX) { this.weaponX = weaponX; }

    /**
     * set weapon y
     * @param weaponY
     */
    public void setWeaponY(double weaponY) { this.weaponY = weaponY; }

    /**
     * set current isOverLap boolean
     * @param overLap
     */
    public void setOverLap(boolean overLap) { isOverLap = overLap; }

    /**
     * set current isPickUp boolean
     * @param pickUP
     */
    public void setPickUP(boolean pickUP) { isPickUP = pickUP; }

    /**
     * set current isDisappear boolean
     * @param disappear
     */
    public void setDisappear(boolean disappear) { isDisappear = disappear; }

    /**
     * set current isShoot boolean
     * @param shoot
     */
    public void setShoot(boolean shoot) { isShoot = shoot; }
}
