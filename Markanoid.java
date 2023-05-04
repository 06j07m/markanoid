/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * Markanoid.java
 * 
 * Creates ands run an instance of my game
 */

import javax.swing.*;

public class Markanoid extends JFrame{

    // New game
    MarkanoidGame game = new MarkanoidGame();

    // CONSTRUCTOR
    // Create a new window
    public Markanoid() {
        // Set title and tell it to exit when you close the window
        super("Markanoid (:");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add game to window and display
        add(game);
        pack();
        setVisible(true);
    }

    public static void main(String[] arguments) {
        //Run window
        Markanoid window = new Markanoid();
    }
}