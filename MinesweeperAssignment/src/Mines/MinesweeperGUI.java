/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author cstuser
 */
public class MinesweeperGUI extends JFrame implements KeyListener {

  

    

    //Making a 


    private int[][] gameBoard;
    public static boolean DEBUG_MODE = false;
    private boolean ctrlPressed = false;

    private JButton[][] mineClick = new JButton[8][8];

    private static int numOfClicks;

   

    public MinesweeperGUI(String title, int width, int height) {
        super(title);

        //Set size of the window
        setSize(width, height);
        
        numOfClicks = 64;
        
        //Creating a container for the game board
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(8, 8));

        board();
;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton button = new JButton();
                pane.add(button);
                
                if (DEBUG_MODE && gameBoard[i][j] == -1) {
                    button.setText("Mine");
                }
                    
                
                int i1 = i;
                int j1 = j;

                setFocusable(true);
                addKeyListener(this);

                button.addKeyListener(this);
                
                

                button.addActionListener((ActionEvent e) -> {
                    
                    
                    
                    
                    if (ctrlPressed && button.getText().isEmpty()) {
                        button.setText("Mine!");

                    } 
                    else if (ctrlPressed && button.getText().contains("Mine!")) {
                        button.setText("");

                    }
                    
                    else if (gameBoard[i1][j1] == 0) {
                       button.setText("");
                       numOfClicks --; 
                       button.getModel().setPressed(true);
                       mineClick[i1][j1].setEnabled(false);
                        
                        System.out.println(numOfClicks);
                        for (int i2 = (i1 - 1); i2 < (i1 + 2); i2++) {
                            if (i2 >= 0 && i2 <= 7) {
                                for (int j2 = (j1 - 1); j2 < (j1 + 2); j2++) {
                                    if (j2 >= 0 && j2 <= 7) {
                                        if (!mineClick[i2][j2].getModel().isPressed()) {

                                            mineClick[i2][j2].doClick(0);
                                            mineClick[i2][j2].setEnabled(false);

                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (gameBoard[i1][j1] != -1 && gameBoard[i1][j1] != 0) {
                        numOfClicks--;
                        button.setText(String.valueOf(gameBoard[i1][j1]));
                        mineClick[i1][j1].setEnabled(false);
                    } 
                    else if (gameBoard[i1][j1] == -1) {
                        button.setText("Mine");
                        MainWindow.lostCounter ++;
                        try {
                            playLoseSound();
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(MinesweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        MainWindow.gameStatus.setText("No instance found");
                        MainWindow.gamesLost.setText("Games Lost : " + MainWindow.lostCounter);
                        setVisible(false);
                        for (int h = 0; h < 8; h++) {
                            for (int k = 0; k < 8; k++) {
                                if (mineClick[h][k].getText().isEmpty()) {
                                    if (gameBoard[h][k] == -1) {
                                        mineClick[h][k].setText("Mine");
                                    }
                                    mineClick[h][k].setEnabled(false);
                                    
                                }
                            }
                        }
                    }
                    if(numOfClicks == 10){
                        setVisible(false);
                        MainWindow.winCounter++;
                        MainWindow.gameStatus.setText("No instance found");
                        MainWindow.gamesWon.setText("Games won : " + MainWindow.winCounter);
                    }

                });
                this.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosed(WindowEvent we) {
                       MainWindow.gameStatus.setText("No instance found");
                    }
                    
                });
                
                mineClick[i][j] = button;
//                if (gameBoard[i][j] != -1) {
//                    button.setText(String.valueOf(gameBoard[i][j]));
//                }
            }
        }

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

            int xCoordinate = (int) (Math.random() * 7);
            int yCoordinate = (int) (Math.random() * 7);

            if (gameBoard[xCoordinate][yCoordinate] == 0) {
                gameBoard[xCoordinate][yCoordinate] = -1;
                k++;
            }

        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] == -1) {
                    for (int i1 = (i - 1); i1 < (i + 2); i1++) {
                        if (i1 >= 0 && i1 <= 7) {
                            for (int j1 = (j - 1); j1 < (j + 2); j1++) {
                                if (j1 >= 0 && j1 <= 7) {
                                    if (gameBoard[i1][j1] != -1) {
                                        gameBoard[i1][j1] += 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    
    
    public void playLoseSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
       
        File wavFile = new File("Thehighground.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
       
    }
    
    
    


    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

}
