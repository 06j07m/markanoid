/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * PowerUp.java
 * 
 * Creates and moves the powerups that (sometimes) appear when you hit a block
 */


 import java.awt.*;
import javax.swing.*;

public class PowerUp {
    // x and y position (top left), powerup value
    private int x, y, powerup;

    // List of sprite image filenames 
    private String[] imgs = {"img/pu01.png", "img/pu02.png", "img/pu03.png"};
    
    // Sprite
    private Image icon;


    /*
     * CONSTRUCTOR: makes a new powerup
     * Parameters: new x and y position, integer representing what type of powerup it is
     * (1 - makes paddle long, 2 - slow down ball, 3 - make 2 clones of ball)
     */
    public PowerUp(int xx, int yy, int val){
        // Set new x and y position and powerup value based on parameters
        x = xx;
        y = yy;
        powerup = val;

        // Set sprite based on what kind of powerup it is
        icon = new ImageIcon(imgs[val-1]).getImage();
    }


    /*
     * Moves powerup (makes it fall down at a steady speed)
     */
    public void move() {
        y += 4;
    }


    /*
     * Returns rectangle the same size and position as the powerup
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, 20, 30);
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
     * Returns value of powerup
     */
    public int getPowerUp() { return powerup; }


    /*
     * Draws sprite
     */
    public void draw(Graphics g) {
        g.drawImage(icon, x, y, null);
    }
}