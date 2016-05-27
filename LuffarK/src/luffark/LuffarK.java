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
import javax.swing.JPanel;

/**
 *
 * @author Reza
 */
public class LuffarK {

    JButton[] buttonArray = new JButton[400];
    PrintServer pServer = new PrintServer();
    ReadServer rServer = new ReadServer();;
    String playerName;
    VarHolder varHolder = new VarHolder();
    int playerNumber;
    boolean canClick ;
    Dimension prefSize;
    
    
    
    public LuffarK() {
        
        for(int x = 0 ; x<400 ; x++){
            buttonArray[x] = new JButton();
            buttonArray[x].setFocusPainted(false);
            buttonArray[x].setBackground(Color.CYAN);
        }
        
        
        
        LuffarNorth ln = new LuffarNorth(buttonArray, canClick, playerNumber, pServer, rServer,varHolder);
        LuffarWest lw = new LuffarWest(buttonArray, canClick, playerNumber, pServer, rServer,varHolder, ln);
        LuffarView lv = new LuffarView(ln, lw);
        
        
    }
    
    public static void main(String[] args) {
        
        LuffarK lk = new LuffarK();
        
    }
    
}
