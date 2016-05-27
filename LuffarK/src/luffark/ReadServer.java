/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffark;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javax.swing.JButton;

/**
 *
 * @author JRW
 */
public class ReadServer implements Runnable {

    private Socket connection;
    private String msgFromServer;
    private VarHolder varHolder;
    private JButton[] arr;
    int pNumber, pIndex;
    boolean first = true, second = true;
    LuffarNorth ln;

    public ReadServer() {
        //this.ln = ln;

    }

    public void setSocketVB(Socket connection, VarHolder varHolder, JButton[] arr, LuffarNorth ln) {
        this.connection = connection;
        this.varHolder = varHolder;
        this.arr = arr;
        this.ln = ln;
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
            if (first == true) {
                while (reader.hasNextLine()) {
                    varHolder.setMessage(reader.nextLine());
                    first = false;
                    break;
                }
            }

            while (true) {
                System.out.println("while true satsen snurrar");

                if (varHolder.getMessage() != null && varHolder.getMessage().substring(1).equals("5 in row")) {
                    System.out.println("Spelare " + varHolder.getMessage().charAt(0) + " Vann");
                    break;
                }

                if (varHolder.getMessage() != null && varHolder.getMessage().length() > 1) {
                    System.out.println("getmessage = " + varHolder.getMessage());
                    pNumber = Integer.parseInt(varHolder.getMessage().substring(0, 1));
                    System.out.println("pNumber = " + pNumber);

                    pIndex = Integer.parseInt(varHolder.getMessage().substring(1)) - 1;
                    System.out.println("pIndex = " + pIndex);
                }
                if (varHolder.getMessage() != null && varHolder.getMessage().length() > 1 && arr[pIndex].getText() != "O" && arr[pIndex].getText() != "X") {

                    if (pNumber == 1) {
                        arr[pIndex].setText("O");
                        System.out.println("Satt O på " + pIndex);
                        second = true;
                        if (ln.getPlayerNumber() == 1) {
                            System.out.println("canclicked i if sats , före set =" + ln.getCanClicked());

                            //ln.setCanClicked(false);
                            System.out.println("canclicked i if sats , efter set =" + ln.getCanClicked());
                        } else {
                            //ln.setCanClicked(true);
                        }
                        if (ln.getCanClicked() == true) {
                            ln.setCanClicked(false);
                        } else {
                            ln.setCanClicked(true);
                        }

                    } else if (pNumber == 2) {

                        arr[pIndex].setText("X");
                        System.out.println("Satt X på " + pIndex);
                        second = true;
                        if (ln.getPlayerNumber() == 2) {
                            //ln.setCanClicked(true);
                        } else {
                            //ln.setCanClicked(true);
                        }
                        if (ln.getCanClicked() == true) {
                            ln.setCanClicked(false);
                        } else {
                            ln.setCanClicked(true);
                        }
                    }
                }else{
                    second = true;
                }
                if (second == true) {
                    while (reader.hasNextLine()) {
//				nextLine kastar exception men borde inte hända då vi kollar om det finns en ny rad varje gång, innan de läses.
                        System.out.println("readServer before = " + varHolder.getMessage());
                        varHolder.setMessage(reader.nextLine());
                        System.out.println("readserver = " + varHolder.getMessage());
                        second = false;
                        break;

                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
