/*
 * The main server class handling the connection between server and client
 * it contains the server-loop
 */
package luffarserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reza
 */
public class GameServer implements Runnable {

    //instance variables
    //this var holds the max number of players, if you change only this var the program works good 
    private static final int maxNumberOfClients = 2;

    //this var holds the client iterator
    private static int x = 0;

    //this is the current thread in this instance (for every client)
    private Thread t;

    //an array of this class 
    private static GameServer[] v = new GameServer[maxNumberOfClients];

    //socket for every instance of this class, that means a socket connection for every client
    private Socket connection;

    //a stream for every client
    private PrintStream writeToClient;

    //another client iterator which is used in this and other classes
    private static int number = 0;
    
    //scanner for scanning the input from user
    private Scanner sc;

    //name of the client in this instance of the game
    private String name = "";
    private int playerNumber = 0;

    //this flag is used to determine when to name the clients newly connected
    private boolean flag = false;
    
    //this flag is used to write to database once independent of how many clinets are connected
    private static boolean writeToDbFlag = true; 

    private String msgReply = "250";
    private String msgFromClient = "";
    private static DataBaseConnection db = new DataBaseConnection();

    public GameHandler gameHandler = new GameHandler();

    public GameServer() {
        //if this is the first time the gameserver runs, start the serverloop
        if (number == 0) {

            serverLoop();

        }
        
    }

    public GameServer(Socket so, int plNum) throws IOException {
        //set the gameserver-instance socket
        this.connection = so;
        this.playerNumber = plNum;
    }

    //the loop-method which will run until the maximum number of clients is reached
    public void serverLoop() {

        try {

            System.out.println("Starting server...");

            //start the server socket on port 3004
            ServerSocket server = new ServerSocket(3004);

            //continue until the maximum number of clients is reached
            //x is the index of current client
            while (x < maxNumberOfClients) {

                //create a new socket, connection
                connection = new Socket();

                //accept the new socket by the server
                connection = server.accept();

                //increase number
                number++;

                //print the client address
                System.out.println(connection.getInetAddress());

                //a new instance of the quiz class
                v[x] = new GameServer(connection, number);

                //new thread and sending the new gameserver-object as a argument
                t = new Thread(v[x]);

                //start the thread
                t.start();

                //increase x
                x++;

            }

        } //handling exception
        catch (IOException e) {
            System.out.println("Exception som kastades: " + e);
        }

    }

    //when the thread starts and runs, this will happen
    @Override
    public void run() {

        //a loop that always runs
        while (true) {
            try {

                //if the client has been connected   
                if (flag == false) {

                    //a new input stream for the client
                    InputStream stream = connection.getInputStream();
                    
                    //scanning the inputstream
                    sc = new Scanner(stream);

                    //waiting for the client to write something as a username
                    while (name.equals("")) {
                        String s = sc.next();
                        //setting this clients username
                        this.name = s;

                    }

                    System.out.println(name + " är nu uppkopplad! " + "spelarnummer = " + playerNumber);

                    //sending playernumber to client(player)
                    String msgPlayerNumberToPlayer = Integer.toString(playerNumber);
                    PrintWriter printWriter = new PrintWriter(v[playerNumber - 1].connection.getOutputStream());
                    printWriter.println(msgPlayerNumberToPlayer);
                    printWriter.flush();

                    //set flag to true 
                    flag = true;

                }

                //if the player has been given a name
                if (!name.equals("")) {
                    try (Scanner sc = new Scanner(connection.getInputStream())) {
                        while (sc.hasNextLine()) {
                            //read message from client
                            msgFromClient = sc.nextLine();
                            System.out.println("msgFromClient: " + msgFromClient);

                            //substring the message
                            int pNumber = Integer.parseInt(msgFromClient.substring(0, 1));
                            String messageValue = msgFromClient.substring(1);
                            String writedbMessage = "";
                                    
                            if(msgFromClient.length()>8){
                                writedbMessage = msgFromClient.substring(1,8);
                            }
                            
                            
                            
                            //check if client wants see the highscore
                            if (messageValue.equals("highscore")) {

                                int lengthOfTable = db.getHighScoreLength();
                                ArrayList<HighScore> arraylist = new ArrayList<HighScore>();
                                arraylist = db.readDB();
                                String highScoreString = "";
                                for (int i = 0; i < lengthOfTable; i++) {
                                    highScoreString = highScoreString + (i + 1) + "   " + arraylist.get(i).getUser() + "   " + arraylist.get(i).getMovesWon() + "   " + arraylist.get(i).getTime() + ",";
                                }
                                System.out.println(highScoreString);
                                PrintWriter printWriter = new PrintWriter(v[pNumber - 1].connection.getOutputStream());
                                printWriter.println(pNumber + "highscore" + highScoreString);
                                printWriter.flush();

                            }
                            //check if the end of the game is reached and it is time to write highscore to database
                            else if(writedbMessage.equals("writedb")){
                                
                                if(writeToDbFlag == true){
                                    
                                
                                System.out.println("writeDB");
                                
                                //put message in a chararray to check it letter by letter
                                char[] charArray = msgFromClient.toCharArray();
                                int moves=0;
                                String strMoves = "";
                                String time = "";
                                String winner = "";
                                int j = 0;
                                
                                //get number of moves-won from the message from client
                                for(int d = 0; d<charArray.length ; d++){
                                    if(charArray[d] == ','){
                                        j = d;
                                        if(d != charArray.length -2 ){
                                            j++;
                                        }
                                        
                                        while(charArray[j] != ',' ){
                                            
                                            strMoves += charArray[j];
                                            j++;
                                        };
                                        break;
                                    }
                                    
                                }
                                
                                //get winner-name from the message from client
                                int k = 0;
                                for(int d = 0; d<charArray.length ; d++){
                                    if(charArray[d] == ','){
                                        
                                        if(d != charArray.length -2 ){
                                            j++;
                                        }
                                        
                                        while(charArray[j] != ',' ){
                                            
                                            winner += charArray[j];
                                            j++;
                                        };
                                        break;
                                    }
                                    
                                }
                                
                                k = 0;
                                //get winner-time from the message from client
                                for(int d = 0; d<charArray.length ; d++){
                                    if(charArray[d] == ','){
                                        k++;
                                        if(k>=3){
                                            do{
                                                time += charArray[++d]; 
                                            }while(charArray[d+1] != '-');
                                        }
                                        
                                        
                                    }
                                    
                                }
                                
                                moves = Integer.parseInt(strMoves);
                                
                                System.out.println("Moves = " + moves);
                                System.out.println("winner = " + winner);
                                System.out.println("time = " + time);
                                
                                //call the write highcore-method and write to database
                                db.writeHighScore(winner, moves, time);
                                writeToDbFlag = false;
                            }
                                
                            }  
                            //if the message is a game-message, that is a box or button on the playfield
                            //has been clicked
                            else {

                                //get the index of the button or box
                                int pIndex = Integer.parseInt(msgFromClient.substring(1));
                                
                                //call the checkwin-method to see if 5 in row has been reached
                                String result = gameHandler.checkWin(pIndex, pNumber);

                                for (int y = 0; y < number; y++) {

                                    PrintWriter printWriter = new PrintWriter(v[y].connection.getOutputStream());
                                    printWriter.println(msgFromClient);
                                    printWriter.flush();

                                }

                                //if the player has won, send the message to all clients
                                for (int y = 0; y < number; y++) {

                                    if (result.equals("5 in row")) {
                                        String pName = v[pNumber-1].name;
                                        msgFromClient = pNumber + result + pName;
                                    }
                                    PrintWriter printWriter = new PrintWriter(v[y].connection.getOutputStream());
                                    printWriter.println(msgFromClient);
                                    printWriter.flush();

                                }
                            }
                                
                            
                        }
                        //0.1 seconds delay
                        Thread.sleep(100);
                    } catch (IOException e) {
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
