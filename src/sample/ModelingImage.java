package sample;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class read the image information from file
 */

public class ModelingImage
{
     //hexadecimal value of white and black (stored as an int)
     final int EMPTY = 0xFFFFFFFF;
     final int BLACK = 0xFF000000;
     // an array to store the image information
     int image[][];
     //constructor
     public ModelingImage()
     {
          //instantiate
          image = new int[Main.IMAGE_HEIGHT][Main.IMAGE_WIDTH];
          //load the file and store into image array
          loadFile(new File("src/Images/cat.txt"));
     }

     /**
      * read data from file and store the color information into an array
      * @param file the file contains the data
      */
     public void loadFile(File file)
     {
          try
          {
               Scanner read = new Scanner(file);
               for (int r = 0; r < Main.IMAGE_HEIGHT; r++)
               {
                    for (int c = 0; c < Main.IMAGE_WIDTH; c++)
                    {
                         if(read.nextInt() == 1 )
                         {image[r][c] = EMPTY;}
                         else
                         {image[r][c] = BLACK;}
                    }
               }
          }
          catch (IOException exception) { exception.printStackTrace();}
     }



     /**
      * get an array store image information
      * @return  an 2D array stores image information
      */
     public int[][] getImage()
     {return image;}
}
