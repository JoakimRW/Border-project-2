/*
 * PrintServer sends messages to the server that other classes define
 */
package luffark;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JRW
 */
public class PrintServer implements Runnable {

    //instance variables
    private Socket connection;
    private boolean sm= false;
    private String message;

    public PrintServer(){
        
    }
    
    //recieving and setting the socket
    public void setSocket(Socket connection) {
        this.connection = connection;
    }
    
    public void sendMessage(String message){
        System.out.println("metod kallad");
        sm=true;
        this.message=message;
    }
    
    @Override
    public void run() {
        

        try {
            //opening stream
            PrintWriter pWriter = new PrintWriter(connection.getOutputStream());
            
            while (true) {
                try {
                    
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrintServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                //send the message to server
                if(sm==true){
                    System.out.println("if sats");
                    pWriter.println(message);
                    pWriter.flush();
                  sm=false;
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
