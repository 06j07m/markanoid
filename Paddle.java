/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * Paddle.java
 * 
 * Creates and moves the paddle object for the game
 */

import java.awt.*;
import javax.swing.*;

public class Paddle {

    // x and y position (top left), left and right key listeners, height and width of image, powerup value
    private int x, y, left, right, h, w, hasPowerup;

    // List of sprite image filenames
    private String[] imgs = {"img/paddle01.png", "img/paddle02.png"};

    // Sprite
    private Image icon;


    /*
     * CONSTRUCTOR: Makes new paddle object 
     * Parameters: new x position, keylistener for moving left and right, integer value 
     * representing whether it has a powerup (0 - no, 1 - yes)
     */
    public Paddle(int xx, int leftK, int rightK, int isLong) {
        // Set x position, key listeners, and powerup from parameters
        // Y position is always 550 (at the bottom of the screen)
        x = xx;
        y = 550;
        left = leftK;
        right = rightK;
        hasPowerup = isLong;

        // Set image based on whether paddle has a powerup (long sprite) or not (regular sprite)
        icon = new ImageIcon(imgs[hasPowerup]).getImage();
        h = icon.getHeight(null);
        w = icon.getWidth(null);
    }


    /*
     * Moves paddle
     * Parameters: list of booleans representing whether each key is being pressed
     */
    public void move(boolean[] keys) {
        // Moves paddle left if left key is pressed
        if (keys[left]) {
            x -= 7;
        }
        
        // Moves paddle right if right key is pressed
        if (keys[right]) {
            x += 7;
        }

        // Prevent paddle from going off screen on the left
        if (x < 0) {
            x = 0;
        }
        
        // Prevent paddle from going off screen on the right
        if (x + w > 715) {
            x = 715 - w;
        }
    }


    /*
     * Returns rectangle of the same size and position as paddle
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }

    
    /*
     * Returns x coordinate of left edge
     */
    public int getLeft() { return x; }


    /*
     * Returns y coordinate of top edge
     */
    public int getTop() { return y; }


    /*
     * Returns y coordinate of bottom edge
     */
    public int getBottom() { return y + h; }


    /*
     * Returns x coordinate of right edge
     */
    public int getRight() { return x + w; }


    /*
     * Returns value of powerup
     */
    public int getPowerup() { return hasPowerup;}
    

    /*
     * Draws sprite
     */
    public void draw(Graphics g) {
        g.drawImage(icon, x, y, null);
    }
}
