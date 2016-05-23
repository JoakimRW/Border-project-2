/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarserver;

/**
 *
 * @author JRW
 */
public class LuffarServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        //GameServer gs = new GameServer();
        GameHandler hand = new GameHandler();
        hand.checkWin(85, 2);
        
    }
    
}
