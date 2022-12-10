import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.DrawOptions;

/**
 * sub-class of Pipe, contains flame's attributes
 */
public class SteelPipe extends Pipe{
    private final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private final Image STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");
    private final double SWITCH_FLAME = 20;
    private int frameCount = 0;
    private int flameDuration = 0;
    private boolean collideWithFlame = false;
    private double topFlameY;
    private double bottomFLameY;


    // Constructor
    /**
     * set basic SteelPipe conditions
     * @param pipeImage
     * @param isLevelUP
     */
    public SteelPipe(Image pipeImage, boolean isLevelUP){
        super(pipeImage, isLevelUP);
        topFlameY = super.getTOP_PIPE_Y() + pipeImage.getHeight()/2 + 10;
        bottomFLameY = super.getBOTTOM_PIPE_Y() - pipeImage.getHeight()/2 - 10;
    }


    /**
     * update flame state and when it influenced by timeScale change
     * @param timeScale
     */
    public void update(int timeScale){
        if (frameCount % SWITCH_FLAME == 0){
            // frameCount - 1 to make sure this update can run for 30 times
            if (!super.isCollide() && flameDuration < 30){
                renderFlame();
                frameCount -= 1;
                flameDuration += 1;
            }
        }
        frameCount += 1;
        if (flameDuration == 30){
            flameDuration = 0;
            frameCount += 30;
        }
        super.update(timeScale);
    }


    /**
     * draw the flame
     */
    public void renderFlame(){
        FLAME_IMAGE.draw(super.getPipeX(), super.getTOP_PIPE_Y() + STEEL_PIPE_IMAGE.getHeight()/2.0 + 10);
        FLAME_IMAGE.draw(super.getPipeX(), super.getBOTTOM_PIPE_Y() - Window.getHeight()/2 - 10, new DrawOptions().setRotation(Math.PI));
    }





    // Getters
    /**
     * get top flame rectangle box
     * @return Rectangle
     */
    public Rectangle getTopFlameBox(){ return FLAME_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), topFlameY)); }


    /**
     * get bottom flame rectangle box
     * @return Rectangle
     */
    public Rectangle getBottomFlameBox(){ return FLAME_IMAGE.getBoundingBoxAt(new Point(super.getPipeX(), bottomFLameY)); }

    /**
     * get frameCount values in this class
     * @return
     */
    public int getFrameCount() { return frameCount; }


    /**
     * get current isCollideWithFlame boolean
     * @return boolean
     */
    public boolean isCollideWithFlame() { return collideWithFlame; }




    // Setters
    /**
     * set current isCollideWithFlame boolean
     * @param collideWithFlame
     */
    public void setCollideWithFlame(boolean collideWithFlame) { this.collideWithFlame = collideWithFlame; }
}
