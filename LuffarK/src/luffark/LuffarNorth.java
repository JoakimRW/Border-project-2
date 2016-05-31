/*
 * Luffarnorth creates the grid that holds the buttons of the playfield  
 * it also handles much of the logic for sending messages to the server
 * it also defines the menubuttons and the time and number of moves
 * 
 */
package luffark;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import javafx.scene.control.TextInputDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Reza
 */
public class LuffarNorth extends JPanel implements ActionListener {
    // variables
    JButton btnNewGame, btnInfo, btnHighScore;
    TextInputDialog getName;
    PrintServer pServer;
    ReadServer rServer;
    String playerName;
    VarHolder varHolder;
    int playerNumber;
    boolean canClick;
    JButton arr[];
    JLabel dragLabel;
    JLabel timeLabel;
    JLabel turnLabel;
    double time = 0;
    ActionListener taskPerformer;
    Timer timer = new Timer(1000, taskPerformer);
    Socket socket;

    public LuffarNorth(JButton[] arr, boolean canClick, int playerNumber, PrintServer pServer, ReadServer rServer, VarHolder varHolder, JLabel dragLabel, JLabel timeLabel,JLabel turnLabel) {

        this.arr = arr;
        this.canClick = canClick;
        this.playerNumber = playerNumber;
        this.pServer = pServer;
        this.rServer = rServer;
        this.varHolder = varHolder;
        this.dragLabel = dragLabel;
        this.timeLabel = timeLabel;
        this.turnLabel = turnLabel;

        btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(this);

        btnInfo = new JButton("Info");

        btnInfo.addActionListener(this);

        btnHighScore = new JButton("HighScore");
        btnHighScore.addActionListener(this);

        btnHighScore.setEnabled(false);
        btnNewGame.setPreferredSize(new Dimension(850, 50));
        btnInfo.setPreferredSize(new Dimension(850, 50));
        btnHighScore.setPreferredSize(new Dimension(850, 50));

        GridBagLayout gr = new GridBagLayout();
        setLayout(gr);

        GridBagConstraints con;

        con = new GridBagConstraints();
        con.gridy = 1;
        con.gridx = 0;
        add(btnNewGame, con);

        con = new GridBagConstraints();
        con.gridy = 2;
        con.gridx = 0;
        add(btnInfo, con);

        con = new GridBagConstraints();
        con.gridy = 3;
        con.gridx = 0;
        add(btnHighScore, con);

        con = new GridBagConstraints();
        con.gridy = 4;
        con.gridx = 0;
        con.insets = new Insets(10, 10, 10, 10);
        add(dragLabel, con);

        con = new GridBagConstraints();
        con.gridy = 5;
        con.gridx = 0;
        con.insets = new Insets(10, 10, 10, 10);
        add(timeLabel, con);
        
        con = new GridBagConstraints();
        con.gridy = 6;
        con.gridx = 0;
        con.insets = new Insets(10, 10, 10, 10);
        add(turnLabel, con);

        dragLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        dragLabel.setText("Move: 1");
        timeLabel.setText("00:00");
        turnLabel.setText("turn");

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (time <= 0) {
                    time = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long clockTime = (long) (now - time);

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                timeLabel.setText(df.format(clockTime));
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnInfo) {
            JOptionPane.showMessageDialog(null,
                    "The goal with Tic Tac Toe is to get 5 marks in a row of the same type \n"
                    + "each player gets one type of mark, the first gets Circles O and \n"
                    + "the second gets crosses X, you do this in a field of play consisting of 20*20 squares,\n"
                    + "you cannot mark a square you or your opponent has already marked and you take turns marking \n");
        }

        if (ae.getSource() == btnNewGame) {


            System.out.println("Klienten startar");
            try {
//			Skapar en anslutning mot en server, denna kan kasta ett par exceptions (vilka alla är eller ligger under IOException)
                if(socket != null){
                    socket.close();
                }
                
                //få ip från användaren blir localhost om ingenting skrivs
                String address = JOptionPane.showInputDialog("Please enter the ip or nothing for localhost");
                if(address == null) address = "localhost";
                socket = new Socket(address, 3004);
                timer.start();
//			Kör igång en tråd för att kunna skriva till servern, klassen ser samma ut som serverns skrivar klass.			
                pServer.setSocket(socket);
                rServer.setSocketVB(socket, varHolder, arr, this, dragLabel,pServer);
                Thread t1 = new Thread(pServer);
                t1.start();
                Thread t2 = new Thread(rServer);
                t2.start();

                playerName = JOptionPane.showInputDialog("Please fill in your name: ");

                varHolder.setPlayerName(playerName);
                pServer.sendMessage(playerName);

                while (playerNumber == 0) {
                    System.out.println("Väntar på Spelarnummer");
                    if (varHolder.getMessage() != null) {
                        playerNumber = Integer.parseInt(varHolder.getMessage());
                        varHolder.setPlayerNumber(playerNumber);
                        System.out.println("Har fått spelarnummer = " + playerNumber);

                        setCanClicked(true);

                        varHolder.setMessage(null);
                        System.out.println("Message is reset = " + varHolder.getMessage());
                    }

                }
                btnHighScore.setEnabled(true);
                btnNewGame.setEnabled(false);
            } //Denna catch-sats fångar exception från nästan alla rader i try-satsen, enkelt att göra men kanske inte så bra då det blir så generellt.
            catch (IOException e) {
                System.out.println("Exception som kastades: " + e);
            }

            

        }

        if (ae.getSource() == btnHighScore) {
            pServer.sendMessage(playerNumber + "highscore");
        }

    }

    public boolean getCanClicked() {
        return canClick;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setCanClicked(boolean canClick) {
        this.canClick = canClick;
    }

    public Timer setTimer() {
        return timer;
    }

    public JButton getNewGameButton(){
        return btnNewGame;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public JButton getBtnHighScore(){
        return btnHighScore;
    }
    
    public JLabel getDragLabel(){
        return dragLabel;
    }
    public JLabel getTimeLabel(){
        return timeLabel;
    }
    
    public JLabel getTurnLabel(){
        return turnLabel;
    }
    
}
