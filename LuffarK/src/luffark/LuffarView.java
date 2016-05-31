/*
 * The view Class that holds frame of the program
 */
package luffark;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Reza
 */
public class LuffarView extends JFrame{
    
    //constructor that adds the 2 graphical objects to the JFrame
    public LuffarView(LuffarNorth north, LuffarWest west){
        
        BorderLayout b = new BorderLayout();
        setLayout(b);
        
        add(north,BorderLayout.NORTH);
        
        add(west,BorderLayout.WEST);
        
        
        
        //settings of the frame
        setLocation(400,50);
        setSize(1216,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
