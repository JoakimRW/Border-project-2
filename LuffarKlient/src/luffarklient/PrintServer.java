/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarklient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JRW
 */
class PrintServer implements Runnable {

    private Socket connection;
    private boolean sm= false;

    public PrintServer(Socket connection) {
        this.connection = connection;
    }
    public void sendMessage(){
        System.out.println("metod kallad");
        sm=true;
    }
    @Override
    public void run() {
//		Skapar ett scanner-objekt som lyssnar på vad användaren skriver i konsolen.
        Scanner readingConsole = new Scanner(System.in);

        try {
//			Öppnar upp strömmen mot socket med 
            PrintWriter pWriter = new PrintWriter(connection.getOutputStream());
            
            while (true) {
                try {
                    //System.out.println(sm);
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrintServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(sm==true){
                    System.out.println("if sats");
                  pWriter.println("test");
                  pWriter.flush();
                  sm=false;
                }
                //pWriter.println(readingConsole.nextLine());
                //pWriter.flush();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
