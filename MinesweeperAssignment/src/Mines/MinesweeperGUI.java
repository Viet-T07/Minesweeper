/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mines;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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
    private static MinesweeperGUI instance = null;

    public MinesweeperGUI() {
        super("Title");
        //Make the start menu window 400 x 400
        setSize(400,400);
        
        //Initialize JLabels and the start button
        startButton = new JButton("Start Game");
        gamesWon = new JLabel("Message");
        gamesLost = new JLabel("Message");
        gameStatus = new JLabel("Message");
        
      
        
        JPanel start = new JPanel();
        start.add(startButton);
        
        
        //Initialize the container 
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(3,3));
        pane.add(startButton);
        pane.add(gamesWon);
        pane.add(gamesLost);
        pane.add(gameStatus);
        
        
        instance = this;
        
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
