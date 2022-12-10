import bagel.Image;

/**
 * sub-class of Pipe, contains attributes for plastic pipe
 */
public class PlasticPipe extends Pipe{

    // Constructor
    /**
     * set basic attributes for plastic pipe
     * @param pipeImage
     * @param isLevelUP
     */
    public PlasticPipe(Image pipeImage, boolean isLevelUP){
        super(pipeImage, isLevelUP);
    }

    /**
     * update pipe state
     * @param timeScale
     */
    public void update(int timeScale){
        super.update(timeScale);
    }
}
