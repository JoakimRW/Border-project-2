/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.graphtest;

import java.util.Observable;

/**
 *
 * @author JRW
 */
public class VarHolder extends Observable {
    
    private String message,playerName;
    private int playerNumber;
    private boolean cValue =false;
  
    
    public VarHolder(){
        
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        
        if (this.message != null && this.message.length() > 1){
            setChanged();
            notifyObservers();
            System.out.println("luffarklient.VarHolder.setMessage()");
        }
    }

    
    
    
}
