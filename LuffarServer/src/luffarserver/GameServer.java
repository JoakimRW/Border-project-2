
package luffarserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reza
 */

public class GameServer implements Runnable{
    
//instance variables
    
    //this var holds the max number of players, if you change only this var the program works good 
    public static final int maxNumberOfClients = 2;
    
    //this var holds the client iterator
    public static int x = 0;
    
    //this is the current thread in this instance (for every client)
    private Thread t ;
    
    //an array of this class 
    public static GameServer[] v = new GameServer[maxNumberOfClients];
    
    //socket for every instance of quiz, that means a socket connection for every client
    public Socket connection;
    
    //a stream for every client
    private PrintStream writeToClient ;
    
    //another client iterator which is used in this and other classes
    public static int number = 0;
    
    //an int that holds the current question number and is shared between the classes
    public static int questionNr = 1;
    
    //an int used to count the current question number and is shared between the classes
    public static int test = 1;
    
    //scanner for scanning the input from user
    private Scanner sc;
    
    //name of the client in this instance of the quiz
    public String name = "";
    public int playerNumber = 0;
    
    //this flag is used to determine when to send the welcome message to the clients newly connected
    private boolean flag = false;
		
    String msgReply = "250";
    String msgFromClient = "";
    
    public GameServer(){
        //if this is the first time the quiz runs, start the serverloop
        if(number == 0){
            
            serverLoop();
            
        }
    }
    
    public GameServer(Socket so, int plNum) throws IOException{
        //set the quiz-instance socket
        this.connection = so;
        this.playerNumber = plNum;
        
    }
    
    //the loop-method which will run until the maximum number of clients is reached
        public void serverLoop(){
            
            try {
			
			System.out.println("Startar server...");
                        
                        //start the server socket on port 3004
			ServerSocket server = new ServerSocket(3004);
			
				
                                //continue until the maximum number of clients is reached
                                //x is the index of current client
                                while(x<maxNumberOfClients){
                                    
                                    //create a new socket, connection
                                    connection = new Socket();
                                    
                                    //accept the new socket by the server
                                    connection = server.accept();
                                    
                                    //increase number
                                    number++;
                                    
                                    
                                    //print the client address
                                    System.out.println(connection.getInetAddress());
                                    
                                    //a new instance of the quiz class
                                    v[x] = new GameServer(connection,number);
                                    
                                    //new thread and sending the new quiz-object as a argument
                                    t = new Thread(v[x]);
                                    
                                    //start the thread
                                    t.start();
                                    
                                        
                                    //increase x
                                    x++;
                                    
                                }
                                
				
		} 
			
                //handling exception
		catch (IOException e) {
			System.out.println("Exception som kastades: " + e);
		}
            
        }

    //when the thread starts and runs, this will happen
    @Override
    public void run() {
        
        //a loop that always runs
        while(true){
            try {
                
                //if the client has been connected  
                if(flag == false){
                    
                    //a new input stream for the client
                    InputStream stream  = connection.getInputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(
                    connection.getOutputStream());
                    //String messageFromClient = stream.readUTF() ;
                    //scanning the inputstream
                    sc = new Scanner(stream);
        
                    //waiting for the client to write something as a username
                    while(name.equals("")){
                    String s = sc.next();
                    //dataOutputStream.writeUTF(msgReply);
                    //setting this clients username
                    this.name = s;
                   
                    }
                    
                    System.out.println(name + " är nu uppkopplad! " + "spelarnummer = " + playerNumber);
                    
                    String msgPlayerNumberToPlayer = "";
                    PrintWriter printWriter = new PrintWriter (v[playerNumber-1].connection.getOutputStream());
                    printWriter.println(msgPlayerNumberToPlayer);
                    printWriter.flush();
                    
                    //set flag to true 
                    flag = true;
                    
                }
                
                if(!name.equals("")){
                    //System.out.println("name is not empty");
                    try (Scanner sc = new Scanner(connection.getInputStream())){
                        while(sc.hasNextLine()){
                            msgFromClient = sc.nextLine();
                            System.out.println("msgFromClient: " + msgFromClient);
                            
                            String serverMsg = playerNumber + msgFromClient;
                            
                            for(int y = 0 ; y < number ; y++){
                                
                                PrintWriter printWriter = new PrintWriter (v[y].connection.getOutputStream());
                                printWriter.println(serverMsg);
                                printWriter.flush();
                                
                            }
                            
                            
                        }
                        Thread.sleep(100);
                    }
                    catch (IOException e) {
			e.printStackTrace();
		}
                }
                
                
                
                //thread wait 0.1 seconds
                 Thread.sleep(100);
                 
            //exception handling     
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
	
    
    
}
