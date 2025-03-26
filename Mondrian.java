/*
 * Veer Desai
 * CSE 123
 * C2: Mondrian Art
 * TA: Isayiah Lim
 * 2/17/25
 */
import java.util.*;
import java.awt.*;

/*
 * This class imitates and creates Mondrian style art. It can devise art with completely random 
 *      colors or can devise art which puts in bias for colors appearing in certain areas
 *      of the canvas.
 */
public class Mondrian {

    /*
     * This helps to build a basic mondrian painting with completely random colors 
     *      in each location.
     * Parameters: 
     *      - pixels: helps to create a standard image of a painting fully contained
     *          tiny pixels
     * Exceptions: 
     *      - IllegalArgumentException(): gets thrown if the pixels are null or if either the
     *          height or width of the painting is less than the size of 300
     */
    public void paintBasicMondrian(Color[][] pixels) {
        if (pixels == null) {
            throw new IllegalArgumentException("the pixels cannot be null");
        }
        if (pixels.length < 300 || pixels[0].length < 300) {
            throw new IllegalArgumentException("invalid dimensions");
        }
        divideCanvas(pixels, 1, 1, pixels[0].length - 1, pixels.length - 1, false);
    }

    /*
     * This helps to build a mondrian painting with colorings based on location biases based on 
     *      where regions are around the canvas. 
     *  Parameters: 
     *      - pixels: helps to create a standard image of a painting fully contained
     *          tiny pixels
     * Exceptions: 
     *      - IllegalArgumentException(): gets thrown if the pixels are null or if either the
     *          height or width of the painting is less than the size of 300
     * 
     */
    public void paintComplexMondrian(Color[][] pixels) {
        if (pixels == null) {
            throw new IllegalArgumentException("the pixels cannot be null");
        }
        if (pixels.length < 300 || pixels[0].length < 300) {
            throw new IllegalArgumentException("invalid dimensions");
        }
        divideCanvas(pixels, 1, 1, pixels[0].length - 1, pixels.length - 1, true);
    }

    /*
     * This method puts color in every single finalized region of the basic mondriuan canvas.
     *      The choosing of numbers is completely random with no bias at all. 
     * Parameters: 
     *      - pixels: helps to create a standard image of a painting fully contained
     *          tiny pixels
     *      - int x1, y1: the x and y coordinates for the top left corner of the selected region
     *      - int x2, y2: the x and y coordinates for the bottom right corner of 
     *          the selected region
     */
    private void fillBasicCanvas(Color[][] pixels, int x1, int y1, int x2, int y2) {
        int picker = (int) (Math.random() * 4);
        Color color = null;
        if (picker == 0) {
            color = Color.RED;
        }
        else if (picker == 1) {
            color = Color.YELLOW;
        }
        else if (picker == 2) {
            color = Color.CYAN;
        }
        else {
            color = Color.WHITE;
        }
        for (int i = x1 + 1; i < x2 - 1; i++) {
            for (int j = y1 + 1; j < y2 - 1; j++) {
                pixels[j][i] = color;
            }
        }
    }

    /*
     * This divides the Mondrian art canvas into seperate regions. Each dividided region gets 
     *      divided again until it reaches a certain littlest point. If a region's width and height are 
     *      both greater than one-fourth of the whole canvas width and height, then the region gets split 
     *      by itself into four smaller regions. If only height or width are greater, 
     *      then the region only gets split to two different subregions. When it is small enough,
     *      that region gets to be filled with color. Also, the regions get divided at a random
     *      point.
     * Parameters: 
     *      - int x1, y1: the top left point of the region
     *      - int x2, y2: the bottom right point of the region
     *      - extension: set to true if complex mondrian is being painted or false if it is basic
     */
    private void divideCanvas(Color[][] pixels, int x1, int y1, int x2, int y2, boolean extension) {
        int width = x2 - x1;
        int height = y2 - y1;
        int vertX = x1 + 10 + (int)(Math.random() * Math.abs((width - 20)));
        int horY = y1 + 10 + (int)(Math.random() * Math.abs((height - 20)));
        if (width >= (0.25) * pixels[0].length  && height >= (0.25) * pixels.length) {
            divideCanvas(pixels,  x1, y1, vertX, horY, extension);
            divideCanvas(pixels, vertX, y1, x2, horY, extension);
            divideCanvas(pixels, x1, horY, vertX, y2, extension);
            divideCanvas(pixels, vertX, horY, x2, y2, extension);
        }
        else if (height >= (0.25) * pixels.length) {
            divideCanvas(pixels, x1, y1, x2, horY, extension);
            divideCanvas(pixels, x1, horY, x2, y2, extension);
        }
        else if (width >= (0.25) * pixels[0].length) {
            divideCanvas(pixels, x1, y1, vertX, y2, extension);
            divideCanvas(pixels, vertX, y1, x2, y2, extension);
        }
        else { 
            if (!extension) {
                fillBasicCanvas(pixels, x1, y1, x2, y2);
            }
            else {
                fillComplexCanvas(pixels, x1, y1, x2, y2);
            }    
        }
    }

    /*
     * This method fills in the regions if the complex mondrian canvas. However, there is bias 
     *      in color towards specific regions of the canvas. There is a greater chance for the 
     *      top left area of the canvas to be red. Greater chance for the top right area 
     *      to be yellow. Greater chance for the bottom left area to be cyan. Greater chance for
     *      the bottom right area to be pink. Everything else in the middle has a greater
     *      chance to be magenta. However, there is no guarantee that it will for sure be those
     *      in the areas; there is just a greater chance of it being that color in the area. 
     * Parameters: 
     *      - pixels: helps to create a standard image of a painting fully contained
     *          tiny pixels
     *      - int x1, y1: the x and y coordinates for the top left corner of the selected region
     *      - int x2, y2: the x and y coordinates for the bottom right corner of 
     *          the selected region
     */
    private void fillComplexCanvas(Color[][] pixels, int x1, int y1, int x2, int y2) {
        double xMid = x1 + ((x2 - x1) / 2);
        double yMid = y1 + ((y2 - y1) / 2);
        if (xMid <= ((0.25) * (pixels[0].length)) && yMid <= ((0.25) * (pixels.length))) {
            colorComplexCanvas(pixels, x1, y1, x2, y2, Color.RED);
        }
        else if (xMid >= ((0.75) * (pixels[0].length)) && yMid <= ((0.25) * (pixels.length))) {
            colorComplexCanvas(pixels, x1, y1, x2, y2, Color.YELLOW);
        }
        else if (xMid <= ((0.25) * (pixels[0].length)) && yMid >= ((0.75) * (pixels.length))) {
            colorComplexCanvas(pixels, x1, y1, x2, y2, Color.CYAN);
        }
        else if (xMid >= ((0.75) * (pixels[0].length)) && yMid >= ((0.75) * (pixels.length))) {
            colorComplexCanvas(pixels, x1, y1, x2, y2, Color.PINK);
        }
        else {
            colorComplexCanvas(pixels, x1, y1, x2, y2, Color.MAGENTA);
        }
    }

    /*
     * This method actually deals with providing the color to the region of the canvas. 
     * Parameters: 
     *      - pixels: helps to create a standard image of a painting fully contained
     *          tiny pixels
     *      - int x1, y1: the x and y coordinates for the top left corner of the selected region
     *      - int x2, y2: the x and y coordinates for the bottom right corner of 
     *          the selected region
     */
    private void colorComplexCanvas(Color[][] pixels, int x1, int y1, int x2, int y2, Color biasFactor) {
        Color[] something = new Color[]{biasFactor, biasFactor, biasFactor, biasFactor, Color.RED, 
                Color.YELLOW, Color.CYAN, Color.PINK, Color.MAGENTA, biasFactor, biasFactor, 
                    biasFactor, biasFactor, biasFactor, biasFactor};
        int randIndex = (int) (Math.random() * something.length);
        Color chosenColor = something[randIndex];
        for (int i = x1 + 1; i < x2 - 1; i++) {
            for (int j = y1 + 1; j < y2 - 1; j++) {
                pixels[j][i] = chosenColor;
            }
        }      
    }             
}

          

