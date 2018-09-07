/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mines;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author cstuser
 */
public class MinesweeperGUI extends JFrame implements KeyListener {

    private JButton startButton;
    private JLabel gamesWon;
    private JLabel gamesLost;
    private JLabel gameStatus;

    //Setting an instance to output the correct message displaying the state of the game
    private static MinesweeperGUI instance = null;

    //Making a 
    private int[][] gameBoard;
    boolean DEBUG_MODE;

    private JButton[][] mineClick = new JButton[8][8];

    private static JMenuBar menuBar;
    private JCheckBoxMenuItem cbMenuItem;
    private JMenu menu;

    public MinesweeperGUI() {
        super("MineSweeper");
        //Make the start menu window 400 x 400
        setSize(400, 400);

        menu();

        //Initialize JLabels and the start button
        startButton = new JButton("Start Game");
        gamesWon = new JLabel(" Games Won");
        gamesLost = new JLabel(" Games Lost");
        gameStatus = new JLabel(" No instance found");

        JPanel start = new JPanel();
        start.add(startButton);

        //Initialize the container 
        Container pane = getContentPane();

        //Adding all the elements to the pane
        pane.setLayout(new GridLayout(4, 1));

        pane.add(startButton);
        pane.add(gamesWon);
        pane.add(gamesLost);
        pane.add(gameStatus);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinesweeperGUI window = new MinesweeperGUI("Minesweeper", 800, 800);
                window.setVisible(true);
                displayMessage("Game In Progress");

            }

        });

        instance = this;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    public MinesweeperGUI(String title, int width, int height) {
        super(title);

        //Set size of the window
        setSize(width, height);

        //Creating a container for the game board
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(8, 8));

        board();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton button = new JButton();
                pane.add(button);

                if (gameBoard[i][j] == -1 && DEBUG_MODE) {
                    button.setText("Mine");
                }
                int i1 = i;
                int j1 = j;
                button.addActionListener((ActionEvent e) -> {
                    if (gameBoard[i1][j1] != -1 && gameBoard[i1][j1] != 0) {
                        button.setText(String.valueOf(gameBoard[i1][j1]));
                    } else if (gameBoard[i1][j1] == -1) {
                        button.setText("Mine");
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
                });
                mineClick[i][j] = button;
//                if (gameBoard[i][j] != -1) {
//                    button.setText(String.valueOf(gameBoard[i][j]));
//                }
            }
        }

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        displayMessage("No game open");

    }

    public void menu() {
        menuBar = new JMenuBar();

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        cbMenuItem = new JCheckBoxMenuItem("DEBUG_MODE");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);

        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DEBUG_MODE = true) {
                    DEBUG_MODE = false;
                } else {
                    DEBUG_MODE = true;
                }
            }
        }
        );
        menu.add(cbMenuItem);
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

    public static void displayMessage(String message) {
        if (instance != null) {
            instance.gameStatus.setText(message);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
