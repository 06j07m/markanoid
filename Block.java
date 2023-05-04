/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * Block.java
 * 
 * Creates and stores information for a block
 */

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Block {
    // x and y position (top left), number of points that hitting it will give you, height and width of the block
    private int x, y, points, h, w;

    // List of sprite file names (white, orange, blue, green, red, dark blue, pink, yellow, silver, gold)
    private String[] imgs = {"img/block01.png","img/block02.png","img/block03.png","img/block04.png","img/block05.png","img/block06.png","img/block07.png","img/block08.png","img/block09.png","img/block10.png"};

    // List of points corresponding to the different sprite colours
    private int[] pts = {50, 60, 70, 80, 90, 100, 110, 120, 1, 0};

    // Sprite
    private Image icon;


    /*
     * CONSTRUCTOR: creates a block
     * Parameters: list of 3 integers (x position, y position, and index representing the colour/points)
     * and the round number
     */
    public Block(int[] properties, int roundNumber) {
        // Set x and y position
        x = properties[0];
        y = properties[1];

        // Set point value as 50 x the round number if the block is silver (index 8)
        // Otherwise set it to the regular point value based on colour
        points = properties[2] == 8 ? 50 * roundNumber : pts[properties[2]];


        // Set sprite
        icon = new ImageIcon(imgs[properties[2]]).getImage();

        // Set height and width to that of the sprite
        h = icon.getHeight(null);
        w = icon.getWidth(null);
    }

    
    /*
     * Returns rectangle the same size and position as the block
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
     * Returns point value of block
     */
    public int getPts() { return points; }
    

    /*
     * Returns a powerup value
     */
    public int getPowerUp() {
        // List of possible values (0 - no powerup) (each type of powerup has 10% chance of being chosen)
        int[] powerups = {0,0,0,0,0,0,0,1,2,3};

        // Get a random index and return the value at that index
        Random n = new Random();
        return powerups[n.nextInt(powerups.length)]; 
    }


    /*
     * Draw block
     */
    public void draw(Graphics g){
        g.drawImage(icon, x, y, null);
    }
}
