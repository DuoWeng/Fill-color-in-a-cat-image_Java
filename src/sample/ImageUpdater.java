package sample;

/**
 * Interface used for updating an on-screen image from a 2D array of a image
 *
 */
public interface ImageUpdater {

    /**
     * update the on-screen image from a 2d array
     * @param image a 280x200 array with pixel (int) colors in each array position
     */
    public void updateImage(int[][] image);
}
