/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * Selector.java
 * 
 * Creates and moves selector for the intro and win/lose screen menus
 */

import java.awt.*;
import javax.swing.*;

public class Selector {
    // Number of options, current selected option, starting y position and height of each row (for drawing)
    private int total, current, y, row;

    // Selector image
    private Image icon;
    

    /*
     * CONSTRUCTOR: makes a selector with default option selected
     * Parameters: number of options, y position when the topmost option is selected, and distance between
     * y position of each option
     */
    public Selector(int numItems, int startY, int rowHeight) {
        // Set number of option, starting y, and row height from parameters
        total = numItems;
        y = startY;
        row = rowHeight;

        // Set default starting option to the first one
        current = 0;

        // Load image
        icon = new ImageIcon("img/selector.png").getImage();
    }


    /*
     * Changes the currently selected option to the next one
     */
    public void increase() {
        current ++;
    }


    /*
     * Changes the currently selected option to the previous one
     */
    public void decrease() {
        current --;

        // Make sure it does not become negative to prevent it selecting a nonexistent option
        if (current < 0) {
            current *= -1;
        }
    }


    /*
     * Return integer representing the currently selected option if all the options were in an array
     */
    public int getCurrent() {
        return current % total;
    }


    /*
     * Draw the selector
     */
    public void draw(Graphics g) {
        g.drawImage(icon, 190, y + getCurrent() * row, null);
    }


}
