/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffark;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reza
 */
public class LuffarK {

    JButton[] buttonArray = new JButton[400];
    PrintServer pServer = new PrintServer();
    ReadServer rServer = new ReadServer();
    String playerName;
    VarHolder varHolder = new VarHolder();
    int playerNumber;
    boolean canClick ;
    Dimension prefSize;
    JLabel dragLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    public static int move = 1;
    
    
    public LuffarK() {
        
        for(int x = 0 ; x<400 ; x++){
            buttonArray[x] = new JButton();
            buttonArray[x].setFocusPainted(false);
            buttonArray[x].setBackground(Color.CYAN);
        }
        
        
        
        LuffarNorth ln = new LuffarNorth(buttonArray, canClick, playerNumber, pServer, rServer,varHolder, dragLabel, timeLabel);
        LuffarWest lw = new LuffarWest(buttonArray, canClick, playerNumber, pServer, rServer,varHolder, ln, dragLabel, timeLabel);
        LuffarView lv = new LuffarView(ln, lw);
        
        
    }
    
    public static void main(String[] args) {
        
        LuffarK lk = new LuffarK();
        
    }
    
}
