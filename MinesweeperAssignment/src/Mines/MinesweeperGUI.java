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

    //Declaring JLabels to format the main menu
    private JLabel placeHolder;
    private JLabel placeHolder1;
    private JLabel placeHolder2;
    private JLabel placeHolder3;
    private JLabel placeHolder4;
    private static MinesweeperGUI instance = null;
    
    private boolean DEBUG_MODE;

    public MinesweeperGUI() {
        super("Title");
        //Make the start menu window 400 x 400
        setSize(400, 400);

        //Initialize JLabels and the start button
        startButton = new JButton("Start Game");
        gamesWon = new JLabel("Won");
        gamesLost = new JLabel("Lost");
        gameStatus = new JLabel("Progress");

        //Creating new JLabels to format the main menu
        placeHolder = new JLabel();
        placeHolder1 = new JLabel();
        placeHolder2 = new JLabel();
        placeHolder3 = new JLabel();
        placeHolder4 = new JLabel();

        JPanel start = new JPanel();
        start.add(startButton);

        //Initialize the container 
        Container pane = getContentPane();

        //Adding all the elements to the pane
        pane.setLayout(new GridLayout(3, 3));
        pane.add(placeHolder);
        pane.add(placeHolder1);
        pane.add(placeHolder2);
        pane.add(gamesWon);
        pane.add(startButton);
        pane.add(gamesLost);
        pane.add(placeHolder3);
        pane.add(gameStatus);
        pane.add(placeHolder4);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MinesweeperGUI window = new MinesweeperGUI("Minesweeper", 800, 800);
                window.setVisible(true);
            }

        });

        instance = this;

    }

    public MinesweeperGUI(String title, int width, int height) {
        super(title);
        setSize(width, height);

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton button = new JButton();
                pane.add(button);

            }
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
