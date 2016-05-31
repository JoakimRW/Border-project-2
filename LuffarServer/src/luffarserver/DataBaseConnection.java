/*
 * This class handles the connection to the database
 * It reads and writes to the database
 * 
 */
package luffarserver;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

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
    private static boolean writeToDbFlag = true;
    
    //this method reads the records in the database
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
        
        //this loop reads the database and stores the username, moveswon and time for every record in the database
        //the data is stored in a new highscore object
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
       
            
            //add the highscore-object to the arraylist
            arraylist.add(hs);
       
        } 
        //return the list
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
    
    
    
    //this method gets username, moves and time of the winning client, adds it to the list and sorts 
    //and writes the new list into the database
    public void writeHighScore(String username, int moveswon, String time){
        
        if(writeToDbFlag == true){
        
        //calling the connectDB-method to establish a connection
        conn = ConnectDB();
        
        //clear the list, because the same list is used in this class
        arraylist.clear();
        
        //read the database and store all in the list
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
        
        //add current winners data to a new hs-object
        HighScore highscore = new HighScore();
        highscore.setId(highScoreTableLength);
        highscore.setUser(username);
        highscore.setMoves(moveswon);
        highscore.setTime(time);
        
        //add the hs-object to the list
        arraylist.add(highscore);
            
        ArrayList<HighScore> arraylist2 = new ArrayList<HighScore>();
        
        //call the sort-method to sort the list
        arraylist2 = sortHighScore(arraylist);
        
        //statement
        Statement stmt = null;
        
        highScoreTableLength = getHighScoreLength();
         
        conn = ConnectDB();
        
        //a loop that deletes all the records in the database
        for(int x = 0 ; x < highScoreTableLength ; x++){
            
            String sqlDel="DELETE FROM highscore " + "WHERE id = " + (x+1) ;
            System.out.println(sqlDel);
        
            try{
                
                stmt = conn.createStatement();
                stmt.executeUpdate(sqlDel);
           
                
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
        }
            
        System.out.println("Writing to database:");
        
            // a loop that inserts the new list (the sorted) into the database 
             for(int x = 0 ; x < arraylist2.size() ; x++){
                conn = ConnectDB();
                String sql="insert into highscore values (" + (x+1) + ",'" + arraylist2.get(x).getUser() + "'," + arraylist2.get(x).getMovesWon() + ",'" + arraylist2.get(x).getTime() + "');" ;
                System.out.println((x+1) + ",'" + arraylist2.get(x).getUser() + "'," + arraylist2.get(x).getMovesWon() + ",'" + arraylist2.get(x).getTime());
        
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
             
        writeToDbFlag = false;
        
        }
    }
    
    // a method that sorts the list
    public ArrayList<HighScore> sortHighScore(ArrayList<HighScore> arrlist){
        
        arraylist = arrlist;
        
        Collections.sort(arraylist);
        
        //we want to keep only top 5 highscores
        if(arraylist.size()>5){
            arraylist.remove(arraylist.size()-1);
        }
        
        
        return arraylist;
    }
    
    //a method that counts number of rows in the database
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

    
}
