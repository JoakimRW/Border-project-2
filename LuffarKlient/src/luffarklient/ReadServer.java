/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarklient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JRW
 */
public class ReadServer implements Runnable {

    private Socket connection;
    private String msgFromServer;
    private VarHolder varHolder;

    public ReadServer(Socket connection,VarHolder varHolder) {
        this.connection = connection;
        this.varHolder = varHolder;
    }

    public void resetMessageFromServer() {
        msgFromServer = null;
    }

    public String GetMessageFromServer() {
        return msgFromServer;
    }

    @Override
    public void run() {

        InputStream stream;
        try {
            stream = connection.getInputStream();
            //  Sedan öppnas en ström för att läsa det man får till datorn. 
            Scanner reader = new Scanner(stream);
//			Så länge som det finns en ny rad att hämta kommer denna skrivas ut, vilket endast kommer hända en gång i detta exempel.

            while (reader.hasNextLine()) {
//				nextLine kastar exception men borde inte hända då vi kollar om det finns en ny rad varje gång, innan de läses.
                System.out.println("readServer before = " + varHolder.getMessage());
                varHolder.setMessage(reader.nextLine());
                System.out.println("readserver = " + varHolder.getMessage());

            }
        } catch (IOException ex) {
            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
