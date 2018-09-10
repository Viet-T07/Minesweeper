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
import javax.swing.*;

/**
 *
 * @author Admin
 */
public class MainWindow extends JFrame {
    
    private static JMenuBar menuBar;
    private JCheckBoxMenuItem cbMenuItem;
    private JMenu menu;
    
    
    //Setting an instance to output the correct message displaying the state of the game
    public static MainWindow instance = null;
    
    
    public static JButton startButton;
    public static JLabel gamesWon;
    public static JLabel gamesLost;
    public static JLabel gameStatus;
    
    public static int winCounter;
    public static int lostCounter;

    
    
    
    public MainWindow(){
        super("MineSweeper");
        //Make the start menu window 400 x 400
        setSize(400, 400);

        menu();
        setJMenuBar(menuBar);
        //Initialize JLabels and the start button
        startButton = new JButton("Start Game");
        gamesWon = new JLabel(" Games Won :  " + winCounter);
        gamesLost = new JLabel(" Games Lost :  " + lostCounter);
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
                System.out.println(MinesweeperGUI.DEBUG_MODE);
                MinesweeperGUI.DEBUG_MODE = !MinesweeperGUI.DEBUG_MODE;
                
            }
        }
        );
        menu.add(cbMenuItem);
    }
    
    
    
        public static void displayMessage(String message) {
        if (instance != null) {
            instance.gameStatus.setText(message);
        }

    }
    
}
