/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * Ball.java
 * 
 * Creates and moves the ball object for the game
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Ball {
    // x and y position (top left), horizontal and vertical speed, height and width of image
    private int x, y, vx, vy, h, w;

    // List of sprite image filenames
    private String[] imgs = {"img/ball.png", "img/clone.png"};

    // Sprite
    private Image icon;

    // Sound for when it hits the paddle
    private SoundEffect catchSound = new SoundEffect("img/hitPaddle.wav");


    /*
     * CONSTRUCTOR: makes new ball object
     * Parameters: new x and y position, new horizontal and vertical speed, integer representing
     * whether it's a clone (either 0 - not a clone, or 1 - is a clone)
     */
    public Ball(int xx, int yy, int vxx, int vyy, int isClone) {
        // Set position and speed from parameters
        x = xx;
        y = yy;
        vx = vxx;
        vy = vyy;

        // Set sprite based on whether it is a clone
        // (Not clone - blue sprite, clone - red sprite)
        icon = new ImageIcon(imgs[isClone]).getImage();

        // Set height and width using sprite dimensions
        h = icon.getHeight(null);
        w = icon.getWidth(null);
    }


    /*
     * Moves the ball when it isn't hitting any blocks
     * Parameters: boolean representing whether the game has started, paddle object
     */
    public void move(boolean start, Paddle paddle){
        // When game has started (ball is moving)
        if (start) {
            // Change position by horizontal and vertical speed
            x += vx;
            y += vy;

            // Keep track of which velocities need to be changed
            boolean changeX = false;
            boolean changeY = false;
                  
            // Reverse vertical direction if it hits the paddle from the top 
            if(getRect().intersects(paddle.getRect()) && !getPrevYRect().intersects(paddle.getRect())) {
                changeY = true;
                catchSound.play();
            }

            // Also reverse vertical direction if it hits the top wall
            if (y < 40) {
                changeY = true;
            }

            // Reverse horizontal direction if it hits the left/right wall
            if (x < 0 || x + w > 715){
                changeX = true;
            }
            
            // Change velocities after checking all cases
            vy = changeY ? vy * -1 : vy;
            vx = changeX ? vx * -1 : vx;
        }

        // When game hasn't started (ball is sitting on paddle)
        else {
            // Set x (left) to half of one ball width to the left of the middle of the paddle
            // so that the ball is in the middle of the paddle
            x = (paddle.getRight() + paddle.getLeft()) / 2 - w / 2 ;

            // Set y (top) to one ball height above the top of paddle
            y = paddle.getTop() - h;
        }
    }


    /*
     * Returns boolean representing whether the next block in the opposite direction that the ball 
     * is going is indestructable (gold/0 points)
     * Parameters: current block, list of all blocks
     */
    private boolean nextToGold(Block b, ArrayList<Block> blocks) {
        // Index of adjcacent block
        // Set to index of current block in case there are no adjacent blocks
        int toCheck = blocks.indexOf(b);
        
        // Ball is going right
        if (getXSpeed() > 0) {
            // Change the index to the block to the left if there is one
            if (toCheck != 1) {
                toCheck = toCheck - 1;
            }
        }
        
        // Ball is going left
        else {
            // Change the index to the block to the right if there is one
            if (toCheck != (blocks.size() - 1)) {
                toCheck = toCheck + 1;
            }
        }

        return blocks.get(toCheck).getPts() == 0;
    }


    /*
     * Moves ball when it hits a block
     * Parameters: block that it hit, list of all blocks
     */
    public void bounce(Block b, ArrayList<Block> blocks) {
        // Keep track of which velocities need to be changed
        boolean changeX = false;
        boolean changeY = false;

        // Reverse horizontal direction if ball hits block from the left/right and it isn't part of
        // the "wall" of indestructable blocks
        if (!getPrevXRect().intersects(b.getRect()) && !nextToGold(b, blocks)) {
            changeX = true;
        }
        
        // Reverse vertical direction if ball hits block from the top/bottom
        if (!getPrevYRect().intersects(b.getRect())) {
            changeY = true;
        }

        // Change velocities after checking all cases
        vy = changeY ? vy * -1 : vy;
        vx = changeX ? vx * -1 : vx;
    }


    /*
     * Decreases horizontal and vertical velocity of ball
     */
    public void slowDown() {
        vx = vx < 0 ? -1 : 1;
        vy = vy < 0 ? -6 : 6;
    }


    /*
     * Increases horizontal and vertical velocity of ball
     */
    public void speedUp() {
        vx = vx < 0 ? -2 : 2;
        vy = vy < 0 ? -8 : 8;
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
     * Returns horizontal velocity
     */
    public int getXSpeed() { return vx; }


    /*
     * Returns vertical velocity
     */
    public int getYSpeed() { return vy; }
    

    /*
     * Returns rectangle the same size and position as the ball
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }
    

    /*
     * Returns rectangle of the same size and vertical position, but at the previous horizontal position
     */
    private Rectangle getPrevXRect() {
        return new Rectangle(x - vx, y, w, h);
    }


    /*
     * Returns rectangle of the same size and horizontal position, but at the previous vertical position
     */
    private Rectangle getPrevYRect() {
        return new Rectangle(x, y - vy, w, h);
    }


    /*
     * Draw ball sprite
     */
    public void draw(Graphics g){
        g.drawImage(icon, x, y, null);
    }
}