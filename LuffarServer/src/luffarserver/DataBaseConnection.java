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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Reza
 */

//this class handles the readWrite-part to the database, it reads the database for team-specification (from the tables klubblag and landslag) 
//and stores it in an arraylist and sends the list to the calling method, it has the method openread that reads the desired database
//this class has also methods for reading and writing the users balance to and from the table saldo
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
    
    
    public ArrayList<HighScore> openRead(){
       
        
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
    
    
    
    //this method writes the users latest balance to the table saldo in the database after clicking the ratta-button
    public void writeHighScore(String username, int moveswon, String time){
        
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
        
        //statement
        Statement stmt = null;
         
        //calling the connectDB-method to establish a connection
        conn = ConnectDB();
        
        highScoreTableLength++;
        
        //the query for update
        String sql="insert into highscore values (" + highScoreTableLength + ",'" + username + "'," + moveswon + ",'" + time + "');" ;
        
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
