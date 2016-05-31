/*
 * The Controller class for the client program
 */
package luffark;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Reza
 */
public class LuffarK {
    
    //Instance Variables
    private JButton[] buttonArray = new JButton[400];
    private PrintServer pServer = new PrintServer();
    private ReadServer rServer = new ReadServer();
    private VarHolder varHolder = new VarHolder();
    private int playerNumber;
    private boolean canClick ;
    private JLabel dragLabel = new JLabel();
    private JLabel timeLabel = new JLabel();
    private JLabel turnLabel = new JLabel();
    public static int move = 1;
    
    
    public LuffarK() {
        //creating the array of buttons that is the playfield
        for(int x = 0 ; x<400 ; x++){
            buttonArray[x] = new JButton();
            buttonArray[x].setFocusPainted(false);
            buttonArray[x].setBackground(Color.CYAN);
        }
        
        
        //Structuring the design of the game and sending objects as parameters 
        LuffarNorth ln = new LuffarNorth(buttonArray, canClick, playerNumber, pServer, rServer,varHolder, dragLabel, timeLabel,turnLabel);
        LuffarWest lw = new LuffarWest(buttonArray, canClick, playerNumber, pServer, rServer,varHolder, ln, dragLabel, timeLabel);
        LuffarView lv = new LuffarView(ln, lw);
        
        
    }
    
    public static void main(String[] args) {
        
        LuffarK lk = new LuffarK();
        
    }
    
}
