/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffark;

import java.util.Observable;

/**
 *
 * @author JRW
 */
public class VarHolder implements Runnable {
    
    private String message,playerName;
    private int playerNumber;
    private boolean cValue =false, isAfterFirstGame = false;
  
    
    public VarHolder(){
        
    }
    
    public Boolean getIsAfterFirstGame (){
        return isAfterFirstGame;
    }
    public void setIsAfterFirstGame(boolean bol){
        isAfterFirstGame = bol;
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
            //setChanged();
            //notifyObservers();
            System.out.println("luffarklient.VarHolder.setMessage()");
        }
    }

    @Override
    public void run() {
        while(true){
            
            if (cValue == true){
                
                
                
            }
            
            
            
        }
        
        
    }
    
    
}