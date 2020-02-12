package sample;
import java.util.*;
import java.awt.*;

/**
 * This class controls the program
 * responsible for coloring the image appropriately and passing
 * colored images back to the Main class
 *
 */
public class Controller
{
    final int EMPTY = 0xFFFFFFFF;
    // variable to store the color information
    int pen;
    //create an array to help check if a point has been put into queue
    boolean check[][];
    // an image instance
    ModelingImage catImage;
    /**
     * Constructor
     *
     * load the model image (cat.txt file)
     * a bit of the file io is shown for you here
     *
     */
    public Controller()
    {
        //instantiate
        catImage = new ModelingImage();
        check = new boolean[Main.IMAGE_HEIGHT][Main.IMAGE_WIDTH];
        pen = EMPTY;
    }


    /**
     * Change the color of the pen so that future drawing uses this pen color
     *
     * @param color the color to set the pen to
     */
    public void setPen(int color)
    {
        pen = color;
    }


    /**
     * flood fill an empty region of an image starting at (row, col)
     *
     * roughly implements Textbook exercise: E15.22 and the algorithm described therein
     *
     * if (row, col) is not an EMPTY pixel - flood fill does nothing
     *
     * this calls imageHolder.updateImage(int [][]) to update the image on the screen
     *
     * @param row the starting row position
     * @param col the starting col position
     * @param imageHolder the owner of the image that wants the new colors
     */
    public void floodFill(int row, int col, ImageUpdater imageHolder)
    {
        System.out.println("Starting flood fill from row: " + row + " col: " + col);

        Queue<Point> queue = new LinkedList<>();
        //if it is an empty pixel (white pixel) add the (row, column) onto the queue


            queue.add(new Point(row,col));
            while(queue.size() > 0)
            {
                //remove the top (row, column) off the queue
                Point p = queue.remove();
                //make sure only empty point can be painted
                //color it the desired color
                if(catImage.getImage()[p.x][p.y] == EMPTY)
                catImage.getImage()[p.x][p.y] = pen;
                //add any empty neighbouring pixels (row, column) from north, south, east and west neighbours onto the queue
                if(isValid(p.x+1,p.y))
                {

                    queue.add(new Point(p.x+1,p.y));
                }
                if(isValid(p.x-1,p.y))
                {
                    queue.add(new Point(p.x-1,p.y));
                }
                if(isValid(p.x,p.y+1))
                {
                    queue.add(new Point(p.x,p.y+1));
                }
                if(isValid(p.x,p.y-1))
                {
                    queue.add(new Point(p.x,p.y-1));
                }
            }



        imageHolder.updateImage(catImage.getImage());
    }

    /**
     * check the point if it is a white point and has not yet been put into queue
     * @param x the x coordinate of point
     * @param y the y coordinate of point
     * @return boolean value to decide which point will be put into queue
     */
    public boolean isValid (int x,int y)
    {
        if(check[x][y])
        {return false;}
        check[x][y] = true;

        return catImage.getImage()[x][y] == EMPTY;
    }

}
