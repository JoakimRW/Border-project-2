/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarserver;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reza
 */


public class DataBaseConnection {
    
    //instance variables
    
    
    private final String USERNAME="root";
    private final String PASSWORD="password";
    private final String CONN_STRING="jdbc:mysql://localhost:3306/luffarschack";
    private Connection conn = null;
    private PreparedStatement pre = null;
    private ResultSet rs = null;
    private String sql1 = "";
    private String sql2 = "";
    private String sql3 = "";
    private String sql4 = "";
    private ArrayList<HighScore> arraylist = new ArrayList<HighScore>();
    private int highScoreTableLength = 0;
    
    
    public ArrayList<HighScore> readDB(){
       
        
        //calling the method connectDB 
        conn = ConnectDB();
        
        
        sql4="SELECT COUNT(*) FROM highscore;";
            
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql4);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    highScoreTableLength = Integer.parseInt(rs.getString(1));
                    System.out.println("count= " + highScoreTableLength);
                }else{
                    System.out.println("Access denied");
                }
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
        
        
        for ( int i = 1 ; i <= highScoreTableLength ; i++){
            
            HighScore hs = new HighScore();
            
            sql1="SELECT username FROM highscore WHERE id = " + i ;
            
            hs.setId(i);
            
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql1);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    
                    hs.setUser(rs.getString(1));
                }else{
                    System.out.println("Access denied");
                }
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
       
            
            
            sql2="SELECT moveswon FROM highscore WHERE id = " + i ;
            
       
        
        
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql2);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    
                    
                    hs.setMoves(Integer.parseInt(rs.getString(1)));
                }else{
                    System.out.println("Access denied");
                }
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
       
            
            sql3="SELECT timeswon FROM highscore WHERE id = " + i ;
            
       
       
        
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql3);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    
                    hs.setTime(rs.getString(1));
                }
                else{
                    System.out.println("Access denied");
                }
            }
            catch(Exception e){
                System.out.println("Error: "+e);
            }
       
            
            
            arraylist.add(hs);
       
        } 
        
        return arraylist;
    }
    
    //this method establishes the connection to the database by specifying the driver, and by using the address,username and password
    public Connection ConnectDB(){
        try{
            //Driver name
            Class.forName("com.mysql.jdbc.Driver");
            
            //Connection
            Connection con=DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
            return con;
        }
        catch(Exception e){
            System.out.println("Error!" + e);
            return null;
        }
        
    }
    
    
    
    
    public void writeHighScore(String username, int moveswon, String time){
        
        //calling the connectDB-method to establish a connection
        conn = ConnectDB();
        
        arraylist = readDB();
        
        sql4="SELECT COUNT(*) FROM highscore;";
            
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql4);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    highScoreTableLength = Integer.parseInt(rs.getString(1));
                    System.out.println("count= " + highScoreTableLength);
                }else{
                    System.out.println("Access denied");
                }
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
            
        highScoreTableLength++;
        
        HighScore highscore = new HighScore();
        highscore.setId(highScoreTableLength);
        highscore.setUser(username);
        highscore.setMoves(moveswon);
        highscore.setTime(time);
        arraylist.add(highscore);
            
        ArrayList<HighScore> arraylist2 = new ArrayList<HighScore>();
        
        arraylist2 = sortHighScore(arraylist);
        
        //statement
        Statement stmt = null;
        
        highScoreTableLength = getHighScoreLength();
         
        conn = ConnectDB();
        for(int x = 0 ; x < highScoreTableLength ; x++){
            
            //conn = ConnectDB();
            
            String sqlDel="DELETE FROM highscore " + "WHERE id = " + (x+1) ;
            System.out.println(sqlDel);
        
            try{
                
                stmt = conn.createStatement();
                stmt.executeUpdate(sqlDel);
           
                
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
        }
            
             for(int x = 0 ; x < highScoreTableLength ; x++){
                conn = ConnectDB();
                String sql="insert into highscore values (" + (x+1) + ",'" + arraylist2.get(x).getUser() + "'," + arraylist2.get(x).getMovesWon() + ",'" + arraylist2.get(x).getTime() + "');" ;
            //System.out.println(sql);
        
            try{
                //statement and update-query
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }
            catch(Exception e){
                System.out.println("Error: "+e);
            
            }
             
        }
        
        
    }
    
    public ArrayList<HighScore> sortHighScore(ArrayList<HighScore> arrlist){
        
        arraylist = arrlist;
        //int indexOfHighest = 0;
        
        /*
        for(int i = 0 ; i < (arraylist.size())  ; i++){
            
            if(arraylist.get(i).getMovesWon() > arraylist.get(indexOfHighest).getMovesWon()){
                
                indexOfHighest = i;
                System.out.println("indexofhighest = " + indexOfHighest);
            }
           
        }*/
        
        //arraylist.remove(indexOfHighest);
        
        Collections.sort(arraylist);
        
        arraylist.remove(arraylist.size()-1);
        
        return arraylist;
    }
    
    
    public int getHighScoreLength(){
        
        //calling the method connectDB 
        conn = ConnectDB();
        
        
        sql4="SELECT COUNT(*) FROM highscore;";
            
            try{
                //prepare statement and execute query
                pre = conn.prepareStatement(sql4);
                rs = pre.executeQuery();
           
                if(rs.next()){
                    highScoreTableLength = Integer.parseInt(rs.getString(1));
                    System.out.println("count= " + highScoreTableLength);
                }else{
                    System.out.println("Access denied");
                }
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
            
            return highScoreTableLength;
        
    }

    /*
    public int compareTo(HighScore comparestu) {
        int comparemoves=((HighScore)comparestu).getMovesWon();
        return comparemoves;
    }*/

    /*
    @Override
    public int compare(HighScore t, HighScore t1) {
        return Integer.compare(t.getMovesWon(), t1.getMovesWon());
    }*/

    

    

   
    
}
