/*
 * The class that reads information from the server and acts on it.
 */
package luffark;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author JRW
 */
public class ReadServer implements Runnable {
    
    //instance variables
    private Socket connection;
    private String msgFromServer;
    private VarHolder varHolder;
    private JButton[] arr;
    private int pNumber, pIndex;
    private boolean first = true, second = true;
    private LuffarNorth ln;
    private boolean hc = false;
    private JLabel dragLabel;
    private PrintServer pServer;

    public ReadServer() {

    }
    
    //setting variables
    public void setSocketVB(Socket connection, VarHolder varHolder, JButton[] arr, LuffarNorth ln, JLabel dragLabel, PrintServer pServer) {
        this.connection = connection;
        this.varHolder = varHolder;
        this.arr = arr;
        this.ln = ln;
        this.dragLabel = dragLabel;
        this.pServer = pServer;
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
            //input stream
            stream = connection.getInputStream();
            
            //scanning the stream 
            Scanner reader = new Scanner(stream);
            
            //read the message and set varholder-message
            if (first == true) {
                while (reader.hasNextLine()) {
                    varHolder.setMessage(reader.nextLine());
                    first = false;
                    break;
                }
            }

            while (true) {
                System.out.println("while true satsen snurrar");
                
                //checks if the message from the server is the highscore
                // and if it is displays the highscore
                if (varHolder.getMessage() != null && varHolder.getMessage().length() > 10 && varHolder.getMessage().substring(1,10).equals("highscore") && hc == false) {
                    System.out.println("highscore = " + varHolder.getMessage().substring(10));
                    
                    String hcm = varHolder.getMessage().substring(10);
                    String output = "";
                    output = "Id    Namn    Antal-drag  Tid" + "\n";
                    char[] charArray = hcm.toCharArray();
                    int charArrayLength = charArray.length;
                    
                    for(int a = 0; a<charArrayLength ; a++){
                        if(charArray[a] == ','){
                            output += "\n";
                        }
                        else{
                            output += charArray[a];
                        }
                    }
                    
                    JOptionPane.showMessageDialog(null,
                            output);
                    hc = true;
                    
                }
                else{
                    
                }
                // checks if the message from the server is reporting a win
                // and if so reports the vinner 
                if(varHolder.getMessage().length() > 9){
                    
                    if (varHolder.getMessage() != null && varHolder.getMessage().substring(1,9).equals("5 in row")) {
                        System.out.println("Spelare " + varHolder.getMessage().charAt(0) + " " + varHolder.getMessage().substring(9)+ " Vann");
                        ln.setTimer().stop();
                        if(Integer.parseInt(varHolder.getMessage().substring(0,1)) ==(varHolder.getPlayerNumber())){
                            pServer.sendMessage(varHolder.getMessage().charAt(0) + "writedb" + "," + LuffarK.move + "," + varHolder.getMessage().substring(9) + "," + ln.getTimeLabel().getText() + "-");
                        }
                        
                        JOptionPane.showMessageDialog(null,
                            "Spelare " + varHolder.getMessage().charAt(0) + " " + varHolder.getMessage().substring(9) + " Vann");
                        
                        
                        try {
                            Thread.sleep(1000);
                            pServer.sendMessage(ln.getPlayerNumber() + "highscore");
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ln.getBtnHighScore().setEnabled(false);
                        
                        while (reader.hasNextLine()) {
				
                            varHolder.setMessage(reader.nextLine());
                            break;
                        }
                        // displays the highscore after a winner has been decided
                        if (varHolder.getMessage() != null && varHolder.getMessage().length() > 8 && varHolder.getMessage().substring(1,10).equals("highscore")) {
                            System.out.println("highscore = " + varHolder.getMessage().substring(10));
                            String hcm = varHolder.getMessage().substring(10);
                            String output = "";
                            output = "Id    Namn    Antal-drag  Tid" + "\n";
                            char[] charArray = hcm.toCharArray();
                            int charArrayLength = charArray.length;
                    
                            for(int a = 0; a<charArrayLength ; a++){
                                if(charArray[a] == ','){
                                    output += "\n";
                                }
                                else{
                                    output += charArray[a];
                                }
                            }
                    
                            JOptionPane.showMessageDialog(null,
                                output);
                                hc = true;
                            }
                        
                            break;
                    }
                }
                
                //set the playernumber variable
                if (varHolder.getMessage() != null && varHolder.getMessage().length() > 1) {
                    System.out.println("getmessage = " + varHolder.getMessage());
                    pNumber = Integer.parseInt(varHolder.getMessage().substring(0, 1));
                    System.out.println("pNumber = " + pNumber);
                    
                    //if it is not a win-message set the button or box index
                    if(hc == false){
                        pIndex = Integer.parseInt(varHolder.getMessage().substring(1)) - 1;
                    }
                    
                    System.out.println("pIndex = " + pIndex);
                }
                
                // applies the players mark to a button 
                if (varHolder.getMessage() != null && varHolder.getMessage().length() > 1 && arr[pIndex].getText() != "O" && arr[pIndex].getText() != "X" && hc==false) {

                    if (pNumber == 1) {
                        arr[pIndex].setText("O");
                        System.out.println("Satt O på " + pIndex);
                        second = true;
                        
                        //switch player turn
                        if (ln.getCanClicked() == true) {
                            ln.setCanClicked(false);
                        } else {
                            ln.setCanClicked(true);
                        }
                        
                        //set the text of the turn-label
                        if (ln.getCanClicked() == true && ln.getPlayerNumber() == 1) {

                            ln.getTurnLabel().setText("Your turn!");

                        } else if (ln.getCanClicked() == true && ln.getPlayerNumber() == 2) {
                            ln.getTurnLabel().setText("Your opponent's turn!");
                            
                        } else if (ln.getCanClicked() == false && ln.getPlayerNumber() == 2) {
                            ln.getTurnLabel().setText("Your turn!");
                            
                        } else if (ln.getCanClicked() == false && ln.getPlayerNumber() == 1) {
                             ln.getTurnLabel().setText("Your opponent's turn!");   
                        }
                        

                    } else if (pNumber == 2) {

                        arr[pIndex].setText("X");
                        System.out.println("Satt X på " + pIndex);
                        second = true;
                        
                        //switch player turn
                        if (ln.getCanClicked() == true) {
                            ln.setCanClicked(false);
                            
                        } else {
                            ln.setCanClicked(true);
                        }
                        
                        //increase turn variable
                        LuffarK.move++;
                        dragLabel.setText("Move: " + LuffarK.move);
                        
                        //set the text of the turn-label
                        if (ln.getCanClicked() == true && ln.getPlayerNumber() == 1) {

                            ln.getTurnLabel().setText("Your turn!");

                        } else if (ln.getCanClicked() == true && ln.getPlayerNumber() == 2) {
                            ln.getTurnLabel().setText("Your opponent's turn!");
                            
                        } else if (ln.getCanClicked() == false && ln.getPlayerNumber() == 2) {
                            ln.getTurnLabel().setText("Your turn!");
                            
                        } else if (ln.getCanClicked() == false && ln.getPlayerNumber() == 1) {
                             ln.getTurnLabel().setText("Your opponent's turn!");   
                        }
                        
                    }
                } else {
                    second = true;
                }
                //recieves the next line
                if (second == true) {
                    while (reader.hasNextLine()) {
                        
                        System.out.println("readServer before = " + varHolder.getMessage());
                        varHolder.setMessage(reader.nextLine());
                        System.out.println("readserver = " + varHolder.getMessage());
                        second = false;
                        
                        break;

                    }
                }
                hc = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
