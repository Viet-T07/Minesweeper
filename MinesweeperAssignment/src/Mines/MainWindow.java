package Mines;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Viet tran
 */
public class MainWindow extends JFrame {

    private static JMenuBar menuBar;
    private JCheckBoxMenuItem cbMenuItem;
    private JMenu menu;
    private static MainWindow instance = null;
    public static JButton startButton;
    public static JLabel gamesWon;
    public static JLabel gamesLost;
    public static JLabel gameStatus;
    public static int winCounter;
    public static int lostCounter;

    public MainWindow() {
        super("MineSweeper");
        //Make the start menu window 400 x 400
        setSize(400, 400);

        //Call on the menu method
        menu();
        //Set the menu bar on the main window
        setJMenuBar(menuBar);

        //Initialize JLabels and the start button
        startButton = new JButton("Start Game");
        gamesWon = new JLabel(" Games Won :  " + winCounter);
        gamesLost = new JLabel(" Games Lost :  " + lostCounter);
        gameStatus = new JLabel(" No instance found");

        //Initialize the container 
        Container pane = getContentPane();

        //Add a layout to the pane
        pane.setLayout(new GridLayout(4, 1));

        //Add diverse elements to the pane
        pane.add(startButton);
        pane.add(gamesWon);
        pane.add(gamesLost);
        pane.add(gameStatus);

        //Add an action listener to the start button
        startButton.addActionListener(new ActionListener() {
            //method that opens the game window
            @Override
            public void actionPerformed(ActionEvent e) {
                MinesweeperGUI window = new MinesweeperGUI("Minesweeper", 800, 800);
                window.setVisible(true);
                displayMessage("Game In Progress");
                

            }

        });

        instance = this;
        //Set the window to terminate the process on close
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    //Create the menu
    public void menu() {
        //Initialize the menu bar
        menuBar = new JMenuBar();

        //Change the name of the menu
        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        //Add the menu to the menubar
        menuBar.add(menu);
        //Create a check box item name debug mode
        cbMenuItem = new JCheckBoxMenuItem("DEBUG_MODE");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);

        //Add an action listener to the debug button
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MinesweeperGUI.DEBUG_MODE = !MinesweeperGUI.DEBUG_MODE;

            }
        }
        );
        //Add the checkbox item to the menu
        menu.add(cbMenuItem);
    }

    public static void displayMessage(String message) {
        if (instance != null) {
            instance.gameStatus.setText(message);
            startButton.setEnabled(false);
        }

    }

}
