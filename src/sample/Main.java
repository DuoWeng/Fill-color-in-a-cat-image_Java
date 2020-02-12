package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * This class is provided for you - it controls the GUI portion of the assignment
 * You DO NOT need to touch anything in this class
 *
 * It uses javafx (including an xml file: sample.fxml to describe the user interface)
 *
 * Swing and javafx are both valid choices for a GUI you might have seen one or the other of those in CS 1920
 *
 */

public class Main extends Application implements ImageUpdater {

    //only one reference to the controller
    static Controller mControllerHandle;

    public static final int IMAGE_WIDTH = 200;
    public static final int IMAGE_HEIGHT = 280;


    private Scene mMainScene;


    /*
        some constants for the integer representation of colors
    */
    final int INDIGO = 0xFF3f51b5;
    final int DEEP_PURPLE = 0xFF673ab7;
    final int PURPLE = 0xFF9c27b0;
    final int PINK = 0xFFe91e63;
    final int RED = 0xFFf44336;

    final int GREEN = 0xFF4caf50;
    final int TEAL = 0xFF009688;
    final int CYAN = 0xFF00bcd4;
    final int LIGHT_BLUE = 0xFF03a9f4;
    final int BLUE = 0xFF2196f3;

    final int ORANGE = 0xFFff9800;
    final int AMBER = 0xFFffc107;
    final int YELLOW = 0xFFffeb3b;
    final int LIME = 0xFFcddc39;
    final int LIGHT_GREEN = 0xFF8bc34a;

    final int BLACK = 0xFF000000;
    final int BLUE_GREY = 0xFF607d8b;
    final int GREY = 0xFF9e9e9e;
    final int BROWN = 0xFF795548;
    final int DEEP_ORANGE = 0xFFff5722;


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

        Parent root = loader.load();

        mControllerHandle = (Controller) loader.getController();
        //start with a default pen color
        mControllerHandle.setPen(INDIGO);



        primaryStage.setTitle("Cat Coloring");
        mMainScene = new Scene(root, 400, 300);

        ImageView catIm = (ImageView) mMainScene.lookup("#catIm");
        catIm.setImage(new Image("Images/cat.png"));

        primaryStage.setScene(mMainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        MouseClickHandler mch = new MouseClickHandler(this);

        root.setOnMouseClicked(mch);
    }

    /**
     * Replaces the cat image on the screen
     * hopefully with another cat image but with different colors
     *
     * @param image the new image to be drawn on the screen
     */

    public void updateImage(int[][] image)
    {
        ImageView catIm = (ImageView) mMainScene.lookup("#catIm");
        WritableImage newIm = new WritableImage (Main.IMAGE_WIDTH, Main.IMAGE_HEIGHT);
        PixelWriter pw = newIm.getPixelWriter();
        for(int r = 0; r < Main.IMAGE_HEIGHT; r++)
        {
            for (int c = 0; c < Main.IMAGE_WIDTH; c++)
            {
                pw.setArgb(c,r,image[r][c]);

            }
        }
        catIm.setImage(newIm);

    }

    private class MouseClickHandler implements EventHandler<MouseEvent>
    {
        private ImageUpdater mImageHolder;

        /**
         * Constructor
         * @param imageHolder where the image resides that will be updated
         */
        public MouseClickHandler(ImageUpdater imageHolder)
        {
            mImageHolder = imageHolder;
        }

        /**
         * handle mouse click events by determining where on the screen the event occurred
         * and passing the appropriate information to the controller
         * @param event the click
         */

        @Override
        public void handle(MouseEvent event) {

            double x = event.getSceneX();
            double y = event.getSceneY();

            //Clicked the color palette?
            //Hard coded values here
            if (x < 342 && x > 250 && y < 200 && y > 23) {


                //HARD CODED MAPPING of SCREEN LOCATIONS
                ImageView iv = (ImageView) mMainScene.lookup("#colorpalette");

                Bounds b = iv.getBoundsInParent();

                double startX = b.getMinX();
                double startY = b.getMinY();
                int ROW_COLORS = 5;
                int COL_COLORS = 4;

                double deltaY = b.getHeight() / ROW_COLORS;
                double deltaX = b.getWidth() / COL_COLORS;

                double mouseClickInImageX = x - startX;
                double mouseClickInImageY = y - startY;

                int col = (int) ((mouseClickInImageX / deltaX));
                int row = (int) (mouseClickInImageY / deltaY);


                //colors from materialuicolors.co
                //hard coded to match the palette image
                int colorPalette[][] = {
                        {INDIGO, GREEN, ORANGE, BLACK},
                        {DEEP_PURPLE, TEAL, AMBER, BLUE_GREY},
                        {PURPLE, CYAN, YELLOW, GREY},
                        {PINK, LIGHT_BLUE, LIME, BROWN},
                        {RED, BLUE, LIGHT_GREEN, DEEP_ORANGE}};


                System.out.println("User selected a new pen color");
                mControllerHandle.setPen(colorPalette[row][col]);

            }

            else if (x < Main.IMAGE_WIDTH && y < Main.IMAGE_HEIGHT)
            {
                System.out.println("user clicked on the image");
                //convert x and y to the appropriate scale

                mControllerHandle.floodFill((int)y,(int) x, Main.this);

                //pass the coordinates of the mouse click to controller
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}