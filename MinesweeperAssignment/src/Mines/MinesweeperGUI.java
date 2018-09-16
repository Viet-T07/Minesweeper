
package Mines;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 *
 * @author Viet Tran
 */
public class MinesweeperGUI extends JFrame implements KeyListener {

    //Initializing the variables
    private int[][] gameBoard;
    public static boolean DEBUG_MODE = false;
    private boolean ctrlPressed = false;
    private JButton[][] mineClick = new JButton[8][8];
    private static int numOfClicks;
    
    
    public MinesweeperGUI(String title, int width, int height) {
        //Set title of the GUI
        super(title);

        //Set size of the window
        setSize(width, height);
        
        //Set the total number of tiles
        numOfClicks = 64;

        //Creating a container for the game board
        Container pane = getContentPane();
        //Set the layout of the container
        pane.setLayout(new GridLayout(8, 8));
        
        //Call on the method that will create a gameboard for the backend
        board();
        //Outer loop that will create the columns of the GUI
        for (int xCoordinate = 0; xCoordinate < 8; xCoordinate++) {
            //Inner loop that will create the rows of the GUI
            for (int yCoordinate = 0; yCoordinate < 8; yCoordinate++) {
                //Create a Jbutton
                JButton button = new JButton();
                //Add the button to the container
                pane.add(button);
                
                //Show the mines at the specified location only if Debug mode is active
                if (DEBUG_MODE && gameBoard[xCoordinate][yCoordinate] == -1) {
                    button.setText("Mine");
                }
                
                //Initialize variables for the action listener
                int column = xCoordinate;
                int row = yCoordinate;
                
                this.setFocusable(true);
                
                //Add key Listener for flagging mechanic
                this.addKeyListener(this);
                //Add key Listener for flagging mechanic
                button.addKeyListener(this);
                
                //Add an action Listener for each button
                button.addActionListener((ActionEvent e) -> {
                    
                    //If ctrl is pressed then flag the desired tile with the text Mine!
                    if (ctrlPressed && button.getText().isEmpty()) {
                        button.setText("Mine!");                    
                    }
                    //Remove the tagged cell if the user click on it again while holding control
                    else if (ctrlPressed && button.getText().contains("Mine!")) {
                        button.setText("");
                    }
                    //Specifies the actions that the program takes when the clicked button contains the value of 0
                    else if (gameBoard[column][row] == 0) {
                        //Sets the text of the button to blank
                        button.setText("");
                        //Decrements the number of clicks 
                        numOfClicks--;
                        
                        //Set the button to pressed
                        button.getModel().setPressed(true);
                        //Disable the button that was pressed
                        mineClick[column][row].setEnabled(false);
                        
                        //Outer loop cycles through the colums that are found next to the 0
                        for (int x = (column - 1); x < (column + 2); x++) {
                            //Verifies that the loop does not go out of bound
                            if (x >= 0 && x <= 7) {
                                //Inner loop that cycles throug the rows located above and below from the 0 
                                for (int y = (row - 1); y < (row + 2); y++) {
                                    //Verifies that the loop does not go out of bound
                                    if (y >= 0 && y <= 7) {
                                        //Statement that check if the button is pressed
                                        if (!mineClick[x][y].getModel().isPressed()) {
                                            //Click the buttons around the 0
                                            mineClick[x][y].doClick(0);
                                            //Disable the clicked buttons
                                            mineClick[x][y].setEnabled(false);

                                        }
                                    }
                                }
                            }
                        }
                    }
                    //Specifies the actions that the program takes when the clicked button contains the value other than -1, 0
                    else if (gameBoard[column][row] != -1 && gameBoard[column][row] != 0) {
                        //decrement the number of clicks
                        numOfClicks--;
                        //Set the button text field to the value at the specified position
                        button.setText(String.valueOf(gameBoard[column][row]));
                        //Disable the clicked button
                        mineClick[column][row].setEnabled(false);
                    }
                    //Specifies the actions that the program takes when the clicked button contains the value of -1
                    else if (gameBoard[column][row] == -1) {
                        //Set the text at the specified button to Mine
                        button.setText("Mine");
                        //Increment the lost counter
                        MainWindow.lostCounter++;
                        
                        //Play the losing sound
                        try {
                            playLostSound();
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Set the main window text
                        MainWindow.gameStatus.setText("No instance found");
                        //Update the text on the main window
                        MainWindow.gamesLost.setText("Games Lost : " + MainWindow.lostCounter);
                        //Close the game window
                        setVisible(false);
                        //Outer loop that cycles through the columns
                        for (int h = 0; h < 8; h++) {
                            //Inner loop that cycles through the rows
                            for (int k = 0; k < 8; k++) {
                                
                                if (mineClick[h][k].getText().isEmpty()) {
                                    //Set the specified text to mine if the value of that tile is -1
                                    if (gameBoard[h][k] == -1) {
                                        mineClick[h][k].setText("Mine");
                                    }
                                    //Disable all the tiles
                                    mineClick[h][k].setEnabled(false);

                                }
                            }
                        }
                    }
                    //Verify if the win condition is met 
                    if (numOfClicks == 10) {
                        //Close the game window
                        setVisible(false);
                        //Increment the win counter
                        MainWindow.winCounter++;
                        
                        //Update the text of the main window
                        MainWindow.gameStatus.setText("No instance found");
                        MainWindow.gamesWon.setText("Games won : " + MainWindow.winCounter);
                        
                        //Play the win sound
                        try {
                            playWinSound();
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                });
                //Add a window Listener to the game window
                this.addWindowListener(new WindowAdapter() {
                    //If the window is closed changed the message
                    @Override
                    public void windowClosed(WindowEvent we) {
                        MainWindow.gameStatus.setText("No instance found");
                    }

                });
                //Fill the double array of buttons with buttons 
                mineClick[xCoordinate][yCoordinate] = button;
                
//                //Method that allows the user to view all of the numbers on the board
//                if (gameBoard[xCoordinate][yCoordinate] != -1 && DEBUG_MODE) {
//                    button.setText(String.valueOf(gameBoard[xCoordinate][yCoordinate]));
//                }
            }
        }
        //When the window is closed the process terminates
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void board() {

        //Creating a gameboard to determine whether the outcome of the game
        gameBoard = new int[8][8];

        //Initialize the variable that will increment if there are  mines around the tile
        //Initialize the loop variable
        int k = 0;
        //Add 10 mines to the gameBoard
        while (k < 10) {
            //Randomize the xCoordinate of the mine
            int xCoordinate = (int) (Math.random() * 7);
            //Randomize the yCoordinate of the mine
            int yCoordinate = (int) (Math.random() * 7);
            //Make the tile correspond to negative 1 only if the tile has not already been used
            if (gameBoard[xCoordinate][yCoordinate] != -1) {
                gameBoard[xCoordinate][yCoordinate] = -1;
                //Loop that checks the different columns
                for (int column = (xCoordinate - 1); column < (xCoordinate + 2); column++) {
                    //Verifies that the x coordinate does not go out of bounds
                    if (column >= 0 && column <= 7) {
                        //Loop that checks the different rows
                        for (int row = (yCoordinate - 1); row < (yCoordinate + 2); row++) {
                            //Verifies that the y coordinate does not go out of bounds
                            if (row >= 0 && row <= 7) {
                                //Increment the cell by 1 as long as the tile does not contain the value of -1
                                if (gameBoard[column][row] != -1) {
                                    gameBoard[column][row] += 1;
                                }
                            }
                        }
                    }
                }
                //Increment the variable if the mine was created
                k++;
            }

        }
    }
    
    //Method that allows the import of sound files
    public void playLostSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        File wavFile = new File("Exclamation.wav");
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            ais.close();
        }

    }
    //Method that allows the import of sound files
    public void playWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File wavFile = new File("ReadyForThis.wav");
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            ais.close();
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }
    //Method that changes the value of ctrlPressed if the button is pressed
    @Override
    public void keyPressed(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }
    //Method that changes the value of ctrlPressed if the button is released
    @Override
    public void keyReleased(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

}
