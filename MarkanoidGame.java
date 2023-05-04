/*
 * ICS4U Simple game assignment: Arkanoid
 * Mona Liu
 * 
 * MarkanoidGame.java
 * 
 * Displays and controls all components of the main game
 */

import java.awt.event.*;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class MarkanoidGame extends JPanel implements ActionListener, KeyListener {
    // array for whether each key is being pressed
    private boolean[] keys;
    
    // final variables for different screens
    private static final int INTRO = 0, LVL1 = 1, LVL2 = 2, LOSE = 3, WIN = 4;

    // variables for points earned, lives remaining, high score, current screen, previous screen (for replay), and number of blocks remaining
    private int score, lives, highScore, curScreen, prevScreen, curNumBlocks;
    
    // array of countdowns for each powerup that has a limited time
    private int[] timers = {0,0};

    // represents whether the game has started (ball is moving)
    private boolean gameStart;

    // images for screens
    private Image intro, background, life, win, lose;

    // menu selectors for intro screen and win/lose screen
    private Selector introSel, endSel;

    // Sound effects
    private SoundEffect selSound, hitSound, powerupSound, startSound, endSound;

    // objects
    private Ball ball;
    private Paddle paddle;

    // List of blocks, powerups, and clones for each new try
    private ArrayList<Block> curBlocks;
    private ArrayList<PowerUp> curPowerups;
    private ArrayList<Ball> clones;

    //timer :|
    private Timer timer;


    /*
     * Resets all variables that are associated with individual tries
     */
    private void resetGame() {
        // Reset points, lives, and reload high score from file
        score = 0;
        lives = 2;
        highScore = loadHighScore("scores.txt");

        // Reset ball, paddle, and lists of powerups, blocks, and clones
        ball = new Ball(410, 540, 1, -4, 0);
        paddle = new Paddle(350, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 0);
        curBlocks = new ArrayList<Block>();
        curPowerups = new ArrayList<PowerUp>();
        clones = new ArrayList<Ball>();


        // Stop game
        gameStart = false;
    }


    /*
     * CONSTRUCTOR: creates new game
     */
    public MarkanoidGame() {
        // Make list of keys
        keys = new boolean[KeyEvent.KEY_LAST + 1];

        // Get images
        intro = new ImageIcon("img/intro.png").getImage();
        background = new ImageIcon("img/gameBackground.png").getImage();
        win = new ImageIcon("img/win.png").getImage();
        lose = new ImageIcon("img/lose.png").getImage();
        life = new ImageIcon("img/life.png").getImage();

        // Get files for sounds
        selSound = new SoundEffect("img/select.wav");
        hitSound = new SoundEffect("img/hitBlock.wav");
        powerupSound = new SoundEffect("img/powerup.wav");
        endSound = new SoundEffect("img/gameOver.wav");
        startSound = new SoundEffect("img/gameStart.wav");
        
        // Make selectors
        introSel = new Selector(2, 255, 100);
        endSel = new Selector(2, 355, 100);

        // Make new timer
        timer = new Timer(20, this);
        timer.start();

        resetGame();
        
        // Set window size
        setPreferredSize(new Dimension(715,600));
        
        // Get focus and add a keylistener
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        
    }



    /*
     * Sets up a new game try
     * Parameters: integer representing which level the new game is
     */
    private void makeLvl(int lvl) {
        // starting x and y positon of block "grid"
        int startX = 0;
        int startY = 70;

        // 2d array with information for every block
        int[][] blocksInfo = null;

        // Set 2d array of blocks, how far apart the blocks are horizontally and vertically, and 
        // number of remaining (destructable) blocks depending on level
        if (lvl == 1) {
            int col = 65;
            int row = 22;
            blocksInfo = new int[][]{
                {startX + 0 * col, startY + 0 * row, 8}, {startX + 1 * col, startY + 0 * row, 8}, {startX + 2 * col, startY + 0 * row, 8}, {startX + 3 * col, startY + 0 * row, 8}, {startX + 4 * col, startY + 0 * row, 8}, {startX + 5 * col, startY + 0 * row, 8}, {startX + 6 * col, startY + 0 * row, 8}, {startX + 7 * col, startY + 0 * row, 8}, {startX + 8 * col, startY + 0 * row, 8}, {startX + 9 * col, startY + 0 * row, 8}, {startX + 10 * col, startY + 0 * row, 8},
                {startX + 0 * col, startY + 1 * row, 4}, {startX + 1 * col, startY + 1 * row, 4}, {startX + 2 * col, startY + 1 * row, 4}, {startX + 3 * col, startY + 1 * row, 4}, {startX + 4 * col, startY + 1 * row, 4}, {startX + 5 * col, startY + 1 * row, 4}, {startX + 6 * col, startY + 1 * row, 4}, {startX + 7 * col, startY + 1 * row, 4}, {startX + 8 * col, startY + 1 * row, 4}, {startX + 9 * col, startY + 1 * row, 4}, {startX + 10 * col, startY + 1 * row, 4},
                {startX + 0 * col, startY + 2 * row, 5}, {startX + 1 * col, startY + 2 * row, 5}, {startX + 2 * col, startY + 2 * row, 5}, {startX + 3 * col, startY + 2 * row, 5}, {startX + 4 * col, startY + 2 * row, 5}, {startX + 5 * col, startY + 2 * row, 5}, {startX + 6 * col, startY + 2 * row, 5}, {startX + 7 * col, startY + 2 * row, 5}, {startX + 8 * col, startY + 2 * row, 5}, {startX + 9 * col, startY + 2 * row, 5}, {startX + 10 * col, startY + 2 * row, 5},
                {startX + 0 * col, startY + 3 * row, 7}, {startX + 1 * col, startY + 3 * row, 7}, {startX + 2 * col, startY + 3 * row, 7}, {startX + 3 * col, startY + 3 * row, 7}, {startX + 4 * col, startY + 3 * row, 7}, {startX + 5 * col, startY + 3 * row, 7}, {startX + 6 * col, startY + 3 * row, 7}, {startX + 7 * col, startY + 3 * row, 7}, {startX + 8 * col, startY + 3 * row, 7}, {startX + 9 * col, startY + 3 * row, 7}, {startX + 10 * col, startY + 3 * row, 7},
                {startX + 0 * col, startY + 4 * row, 6}, {startX + 1 * col, startY + 4 * row, 6}, {startX + 2 * col, startY + 4 * row, 6}, {startX + 3 * col, startY + 4 * row, 6}, {startX + 4 * col, startY + 4 * row, 6}, {startX + 5 * col, startY + 4 * row, 6}, {startX + 6 * col, startY + 4 * row, 6}, {startX + 7 * col, startY + 4 * row, 6}, {startX + 8 * col, startY + 4 * row, 6}, {startX + 9 * col, startY + 4 * row, 6}, {startX + 10 * col, startY + 4 * row, 6},
                {startX + 0 * col, startY + 5 * row, 3}, {startX + 1 * col, startY + 5 * row, 3}, {startX + 2 * col, startY + 5 * row, 3}, {startX + 3 * col, startY + 5 * row, 3}, {startX + 4 * col, startY + 5 * row, 3}, {startX + 5 * col, startY + 5 * row, 3}, {startX + 6 * col, startY + 5 * row, 3}, {startX + 7 * col, startY + 5 * row, 3}, {startX + 8 * col, startY + 5 * row, 3}, {startX + 9 * col, startY + 5 * row, 3}, {startX + 10 * col, startY + 5 * row, 3}
            };
            curNumBlocks = 66;
        }

        else if (lvl == 2) {
            int col = 65;
            int row = 44;
            blocksInfo = new int[][]{
                {startX + 0 * col, startY + 0 * row, 3}, {startX + 1 * col, startY + 0 * row, 3}, {startX + 2 * col, startY + 0 * row, 3}, {startX + 3 * col, startY + 0 * row, 3}, {startX + 4 * col, startY + 0 * row, 3}, {startX + 5 * col, startY + 0 * row, 3}, {startX + 6 * col, startY + 0 * row, 3}, {startX + 7 * col, startY + 0 * row, 3}, {startX + 8 * col, startY + 0 * row, 3}, {startX + 9 * col, startY + 0 * row, 3}, {startX + 10 * col, startY + 0 * row, 3},
                {startX + 0 * col, startY + 1 * row, 0}, {startX + 1 * col, startY + 1 * row, 0}, {startX + 2 * col, startY + 1 * row, 0}, {startX + 3 * col, startY + 1 * row, 9}, {startX + 4 * col, startY + 1 * row, 9}, {startX + 5 * col, startY + 1 * row, 9}, {startX + 6 * col, startY + 1 * row, 9}, {startX + 7 * col, startY + 1 * row, 9}, {startX + 8 * col, startY + 1 * row, 9}, {startX + 9 * col, startY + 1 * row, 9}, {startX + 10 * col, startY + 1 * row, 9},
                {startX + 0 * col, startY + 2 * row, 4}, {startX + 1 * col, startY + 2 * row, 4}, {startX + 2 * col, startY + 2 * row, 4}, {startX + 3 * col, startY + 2 * row, 4}, {startX + 4 * col, startY + 2 * row, 4}, {startX + 5 * col, startY + 2 * row, 4}, {startX + 6 * col, startY + 2 * row, 4}, {startX + 7 * col, startY + 2 * row, 4}, {startX + 8 * col, startY + 2 * row, 4}, {startX + 9 * col, startY + 2 * row, 4}, {startX + 10 * col, startY + 2 * row, 4},
                {startX + 0 * col, startY + 3 * row, 9}, {startX + 1 * col, startY + 3 * row, 9}, {startX + 2 * col, startY + 3 * row, 9}, {startX + 3 * col, startY + 3 * row, 9}, {startX + 4 * col, startY + 3 * row, 9}, {startX + 5 * col, startY + 3 * row, 9}, {startX + 6 * col, startY + 3 * row, 9}, {startX + 7 * col, startY + 3 * row, 9}, {startX + 8 * col, startY + 3 * row, 0}, {startX + 9 * col, startY + 3 * row, 0}, {startX + 10 * col, startY + 3 * row, 0},
                {startX + 0 * col, startY + 4 * row, 6}, {startX + 1 * col, startY + 4 * row, 6}, {startX + 2 * col, startY + 4 * row, 6}, {startX + 3 * col, startY + 4 * row, 6}, {startX + 4 * col, startY + 4 * row, 6}, {startX + 5 * col, startY + 4 * row, 6}, {startX + 6 * col, startY + 4 * row, 6}, {startX + 7 * col, startY + 4 * row, 6}, {startX + 8 * col, startY + 4 * row, 6}, {startX + 9 * col, startY + 4 * row, 6}, {startX + 10 * col, startY + 4 * row, 6},
                {startX + 0 * col, startY + 5 * row, 5}, {startX + 1 * col, startY + 5 * row, 5}, {startX + 2 * col, startY + 5 * row, 5}, {startX + 3 * col, startY + 5 * row, 9}, {startX + 4 * col, startY + 5 * row, 9}, {startX + 5 * col, startY + 5 * row, 9}, {startX + 6 * col, startY + 5 * row, 9}, {startX + 7 * col, startY + 5 * row, 9}, {startX + 8 * col, startY + 5 * row, 9}, {startX + 9 * col, startY + 5 * row, 9}, {startX + 10 * col, startY + 5 * row, 9},
                {startX + 0 * col, startY + 6 * row, 5}, {startX + 1 * col, startY + 6 * row, 5}, {startX + 2 * col, startY + 6 * row, 5}, {startX + 3 * col, startY + 6 * row, 5}, {startX + 4 * col, startY + 6 * row, 5}, {startX + 5 * col, startY + 6 * row, 5}, {startX + 6 * col, startY + 6 * row, 5}, {startX + 7 * col, startY + 6 * row, 5}, {startX + 8 * col, startY + 6 * row, 5}, {startX + 9 * col, startY + 6 * row, 5}, {startX + 10 * col, startY + 6 * row, 5},
                {startX + 0 * col, startY + 7 * row, 9}, {startX + 1 * col, startY + 7 * row, 9}, {startX + 2 * col, startY + 7 * row, 9}, {startX + 3 * col, startY + 7 * row, 9}, {startX + 4 * col, startY + 7 * row, 9}, {startX + 5 * col, startY + 7 * row, 9}, {startX + 6 * col, startY + 7 * row, 9}, {startX + 7 * col, startY + 7 * row, 9}, {startX + 8 * col, startY + 7 * row, 5}, {startX + 9 * col, startY + 7 * row, 5}, {startX + 10 * col, startY + 7 * row, 5}
            };
            curNumBlocks = 56;
        }

        resetGame();
        
        // Create and add each block from array to arraylist of blocks for current try
        for (int i = 0; i < blocksInfo.length; i++) {
            Block b = new Block(blocksInfo[i], 2);
            curBlocks.add(b);
        }
        
    }


    /*
     * Checks if a ball has hit a block; removes block from the list if it can be destroyed and
     * randomly generates a powerup
     * Parameters: ball object, list of remaining blocks
     */
    private void checkHitBlocks(Ball ball, ArrayList<Block> blocks) {
        // Temporary arraylist containing blocks to be removed
        ArrayList<Block> toRemove = new ArrayList<Block>();

        // Loop through remaining blocks
        for (Block b : blocks) {

            //Ball hits block
            if (ball.getRect().intersects(b.getRect())) {
                
                // Move the ball
                ball.bounce(b, curBlocks);

                // Block is destructable (not gold)
                if (b.getPts() != 0) {
                    hitSound.play();

                    // Add points from block to total points
                    score += b.getPts();

                    // Add block to 'remove' list and decrease number of remaining blocks
                    toRemove.add(b);
                    curNumBlocks --;

                    // Temporary random powerup value
                    int randPowerUp = b.getPowerUp();

                    // Generated value isn't 0 (got a powerup)
                    if (randPowerUp != 0) {
                        // Create a powerup object depending on generated value 
                        // Add it to arraylist of powerups
                        PowerUp p = new PowerUp(b.getLeft()+10, b.getTop()+1, randPowerUp);
                        curPowerups.add(p);
                    }

                    //doesn't need to check the remaining blocks
                    break; 
                }
            }
        }
        // Remove any block that was hit from arraylist
        blocks.removeAll(toRemove);
    }


    /*
    * Moves clones and checks if they hit a block
    * Parameters: list of clones, paddle object, list of remaining blocks
    */
    private void moveClones(ArrayList<Ball> clones, Paddle paddle, ArrayList<Block> blocks) {
        // Temporary arraylist of clones to remove
        ArrayList<Ball> toRemove = new ArrayList<Ball>();

        // Loop through list of clones
        for (Ball clone : clones) {
            //Move each clone
            clone.move(gameStart, paddle);

            // Add it to 'remove' list if it falls below the paddle
            if (clone.getTop() > 570) {
                toRemove.add(clone);
                break;
            }

            // Otherwise check if it hit a block (same as for the regular ball)
            checkHitBlocks(clone, curBlocks);
        }

        // Remove any clones in 'remove' list from arraylist
        clones.removeAll(toRemove);
    }


    /*
     * Checks whether you caught a powerup; activates and removes it from the list if needed
     * Parameters: paddle object, arraylist of current (falling) powerups
     */
    private void checkPowerups(Paddle paddle, ArrayList<PowerUp> powerups) {
        // Temporary arraylist of powerups that need to be removed
        ArrayList<PowerUp> toRemove = new ArrayList<PowerUp>();

        // Loop through remaining powerups
        for (PowerUp p : powerups) {
            // Move each one
            p.move();
            
            // Add it to the 'remove' list and activates it if it hits the paddle
            if (p.getRect().intersects(paddle.getRect())) {
                toRemove.add(p);
                activatePowerup(p.getPowerUp());
                break;
            }

            // Also add it to the 'remove' list if it falls below the paddle
            else if (p.getTop() > 600) {
                toRemove.add(p);
                break;
            }
        }

        // Remove any powerups in 'remove' list from arraylist of powerups
        powerups.removeAll(toRemove);
    }


    /*
     * Activates a powerup
     * Parameters: integer represnting type of powerup
     */
    private void activatePowerup(int powerup) {

        // Powerup type is 1 (makes the paddle longer)
        if (powerup == 1) {
            powerupSound.play();

            // Make new paddle if it isn't already activated
            if (paddle.getPowerup() != 1 && timers[0] == 0) {
                // x position is half the difference in length to the left of the
                // current position so that the middle of the paddle stays where it is
                paddle = new Paddle(paddle.getLeft() - 14, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 1);
            }

            // Start countdown (or restart if it's already activated)
            timers[0] = 200;
        }


        // Powerup type is 2 (makes ball slow down)
        else if (powerup == 2) {
            powerupSound.play();

            // Slows down the ball and clones if it isn't already slow
            if (timers[1] == 0) {
                ball.slowDown();
                for (Ball clone : clones) {
                    clone.slowDown();
                }
            }

            // Start or restart countdown
            timers[1] = 200;
        }


        // Powerup type is 3 (make clones)
        // Only activates if there are currently no clones
        else if (powerup == 3 && clones.isEmpty()) {
            powerupSound.play();
            clones.add(new Ball(ball.getLeft(), ball.getTop(), -1, -4, 1));
            clones.add(new Ball(ball.getLeft(), ball.getTop(), 1, -4, 1));
        }
    }


    /*
     * Deactivates a powerup
     * Parameters: integer representing type of powerup
     */
    private void deactivatePowerups(int powerup) {
        // Powerup type is 1 (paddle goes back to normal)
        if (powerup == 1 && paddle.getPowerup() != 0) {
            // Only make new paddle if current paddle has a powerup
            // New x value is half the difference in length to the right of the current x value
            // to keep the middle where it is
            paddle = new Paddle(paddle.getLeft() + 14, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 0);
        }

        // Powerup type is 2 (ball goes back to regular speed)
        else if (powerup == 2) {
            ball.speedUp();
            for (Ball clone : clones) {
                clone.speedUp();
            }
        }

        // Powerup type is 3 (clones are removed)
        else if (powerup == 3) {
            clones.clear();
        }
    }


    /*
     * Draws points and icons for remaining lives at the top of the screen
     * Parameters: graphics object, nubmer of lives remaining
     */
    private void drawScore(Graphics g, int lives) {
        g.setFont(new Font("Lucida Console", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("SCORE:", 30, 30);
        g.setColor(Color.WHITE);
        g.drawString("" + score, 130, 30);

        // Draws lives starting from the right so each one is 40 px to the LEFT of the previous
        for (int i = 0; i < lives; i++) {
            g.drawImage(life, 650 - 40 * i, 20, null);
        }
    }


    /*
     * Draws score and high score on win/lose screen
     * Parameters: graphics object, points, and high score
     */
    private void drawStats(Graphics g, int curScore, int hiScore) {
        g.setFont(new Font("Lucida Console", Font.BOLD, 35));
        g.setColor(Color.RED);
        g.drawString("SCORE:", 220, 270);
        g.drawString("HIGH SCORE:", 110, 320);
        g.setColor(Color.WHITE);
        g.drawString("" + score, 370, 270);
        g.drawString("" + hiScore, 370, 320);
    }


    /*
     * Reads and returns high score from file
     * Parameters: file name/path of high score file
     */
    private int loadHighScore(String fileName) {
        try{
            Scanner fin = new Scanner(new FileReader(fileName));
            int prevScore = Integer.parseInt(fin.nextLine());
            fin.close();
            return prevScore;
        }
        catch(FileNotFoundException ex){
            System.out.println("Error: high score could not be loaded");
            return 0;
        }
    }


    /*
     * Changes current high score and writes it to file if the current score is higher than the current high score
     * Parameters: file name/path of high score file
     */
    private void saveHighScore(String fileName) {
        if (score > highScore) {
            highScore = score;
            try{
                PrintWriter fout = new PrintWriter(new FileWriter(fileName));
                fout.println(highScore);
                fout.close();
            }

            catch(FileNotFoundException ex) {
                System.out.println("Error: high score could not be saved");
            }

            catch(IOException ex) {
                System.out.println("Error: high score could not be saved");
            }
        }
    }


    /*
     * Changes current screen
     * Parameters: integer representing the screen to change it to
     */
    private void changeScreen(int newScreen) {
        // Change to game screen
        if (newScreen == LVL1 || newScreen == LVL2) {
            startSound.play();

            // Make new try of corresponding level
            makeLvl(newScreen);
        }


        // Change to win/lose screen
        else if (newScreen == WIN || newScreen == LOSE) {
            endSound.play();
            saveHighScore("scores.txt");
        }

        // Update previous and current screen
        prevScreen = curScreen;
        curScreen = newScreen;
        
    }


    /*
     * Moves all objects in the game
     */
    private void move() {
        // Ball fell below the paddle
        if(ball.getTop() > 575){
            // Decrease a life
            lives --;

            // Go to lose screen if no lives are left
            if (lives <= 0) {
                changeScreen(LOSE);
            }

            // Stop game and clear powerups
            gameStart = false;
            curPowerups.clear();
            clones.clear();
            for (int i = 0; i < 2; i++) {
                timers[i] = 0;
            }

            // Make new ball and paddle with default values
            ball = new Ball(410, 560, 1, -4, 0);
            paddle = new Paddle(350, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 0);
        }

        // Move paddle, ball, clones, and powerups
        paddle.move(keys);
        ball.move(gameStart, paddle);
        moveClones(clones, paddle, curBlocks);
        checkHitBlocks(ball, curBlocks);
        checkPowerups(paddle, curPowerups);
        
        // Loop through powerup countdowns and decrease each one
        for (int i = 0; i < 2; i++) {
            // Deactivate corresponding powerup if its countdown reaches 0
            if (timers[i] == 0) {
                deactivatePowerups(i + 1);
            }
            else {
                timers[i] --;
            }
        }

        // Go to win screen if all blocks are destroyed
        if (curNumBlocks == 0) {
            changeScreen(WIN);
        }
    }


    /*
     * Checks for any event
     * Parameters: ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Only move game objects in game screens
        if (curScreen == LVL1 || curScreen == LVL2) {
            move();
        }
        repaint();
    }


    /*
     * Checks for key being released
     * Parameters: KeyEvent object
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        // Integer representing which key was released
        int key = ke.getKeyCode();

        // Set corresponding boolean in list of keys to false because the key is no longer being pressed
        keys[key] = false;
        
        // On intro screen
        if (curScreen == INTRO) {
            // Change screen depending on what is currently selected when enter is pressed and released
            if (key == KeyEvent.VK_ENTER) {
                //First option (index 0) - level 1, second option (index 1) - level 2
                changeScreen(introSel.getCurrent() == 0 ? LVL1 : LVL2);
            }

            // Move selector up or down depending on which arrow key is pressed and released
            else if (key == KeyEvent.VK_DOWN) {
                introSel.increase();
                selSound.play();
            }
            else if (key == KeyEvent.VK_UP) {
                introSel.decrease();
                selSound.play();
            }

        }
        

        // In game screen
        else if (curScreen == LVL1 || curScreen == LVL2) {
            // Start game when space key is pressed and released
            if (key == KeyEvent.VK_SPACE && !gameStart) {
                gameStart = true;
            }
        }

        // In win/lose screen
        else if (curScreen == WIN || curScreen == LOSE) {
            // Change screen depending on what is currently selected when enter is pressed and released
            if (key == KeyEvent.VK_ENTER) {
                //First option (index 0) - replay, second option (index 1) - back to intro
                changeScreen(endSel.getCurrent() == 0 ? prevScreen : INTRO);
            }

            // Move selector up or down depending on which arrow key is pressed and released
            else if (key == KeyEvent.VK_DOWN) {
                endSel.increase();
                selSound.play();
            }
            else if (key == KeyEvent.VK_UP) {
                endSel.decrease();
                selSound.play();
            }
        }
    }


    /*
     * Checks for key being pressed
     * Parameters: KeyEvent object
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        // Integer representing which key was released
        int key = ke.getKeyCode();

        // Set corresponding boolean in list of keys to true
        keys[key] = true;
        
    }


    /*
     * Checks for key being typed (not needed)
     * Parameters: KeyEvent object
     */
    @Override
    public void keyTyped(KeyEvent ke) { }


    /*
     * Draws everything in the game
     * Parameters: graphics object
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(background,0,0,null);

        if (curScreen == INTRO){
            g.drawImage(intro,0,0,null);
            introSel.draw(g);
        }

        else if (curScreen == LVL1 || curScreen == LVL2) {
            paddle.draw(g);
            ball.draw(g);
            for (Ball clone : clones) {
                clone.draw(g);
            }
            for (Block b : curBlocks) {
                b.draw(g);
            }
            for (PowerUp p : curPowerups) {
                p.draw(g);
            }
            drawScore(g, lives);
        }

        else if (curScreen == LOSE){
            g.drawImage(lose,0,0,null);
            drawStats(g, score, highScore);
            endSel.draw(g);
        }

        else if (curScreen == WIN) {
            g.drawImage(win, 0, 0, null);
            drawStats(g, score, highScore);
            endSel.draw(g);

        }

    }
}
