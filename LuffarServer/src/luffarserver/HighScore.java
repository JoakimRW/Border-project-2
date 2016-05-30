/*
 * The HighScore class is a database class for interacting with the database
 */
package luffarserver;

/**
 *
 * @author Reza
 */
public class HighScore {
    
    private int id;
    private String username;
    private int moveswon;
    private String timeswon;
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setUser(String name){
        this.username = name;
    }
    
    public String getUser(){
        return username;
    }
    
    public void setMoves(int moves){
        this.moveswon = moves;
    }
    
    public int getMovesWon(){
        return moveswon;
    }
    
    public void setTime(String time){
        this.timeswon = time;
    }
    
    public String getTime(){
        return timeswon;
    }
    
}
