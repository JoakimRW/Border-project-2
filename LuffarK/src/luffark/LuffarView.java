/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public LuffarView(LuffarNorth north, LuffarWest west){
        
        BorderLayout b = new BorderLayout();
        setLayout(b);
        
        add(north,BorderLayout.NORTH);
        
        add(west,BorderLayout.WEST);
        
        //add(east,BorderLayout.EAST);
        
        //add(south,BorderLayout.SOUTH);
        
        //settings of the frame
        //this.setResizable(false);
        setLocation(400,50);
        setSize(1216,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
