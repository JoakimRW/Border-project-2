/*
 * Varholder holds variables for the klient such as messages from the server 
 * the player number and name
 */
package luffark;


/**
 *
 * @author JRW
 */
public class VarHolder  {
    
    private String message,playerName;
    private int playerNumber;
    
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
            System.out.println("luffarklient.VarHolder.setMessage()");
        }
    }
    
}