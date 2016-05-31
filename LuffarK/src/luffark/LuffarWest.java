/*
 * LuffarWest handles the buttons in the playfield and also contains their actionEvent handler
 */
package luffark;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reza
 */
public class LuffarWest extends JPanel implements ActionListener {
    
    //instance variables
    private JButton btnNewGame, btnInfo, btnHighScore;
    private PrintServer pServer;
    private ReadServer rServer;
    private String playerName;
    private VarHolder varHolder;
    private int playerNumber;
    private boolean canClick;
    private JButton[] buttonArray;
    private LuffarNorth ln;
    private JLabel dragLabel;
    private JLabel timeLabel;

    
    //constructor
    public LuffarWest(JButton[] arr, boolean canClick, int playerNumber, PrintServer pServer, ReadServer rServer, VarHolder varHolder, LuffarNorth ln, JLabel dragLabel,JLabel timeLabel) {

        this.buttonArray = arr;
        this.canClick = canClick;
        this.playerNumber = playerNumber;
        this.pServer = pServer;
        this.rServer = rServer;
        this.varHolder = varHolder;
        this.ln = ln;
        this.dragLabel = dragLabel;
        this.timeLabel = timeLabel;

        GridBagLayout gr = new GridBagLayout();

        setLayout(gr);
        GridBagConstraints con;
        
        //setting style of the buttons and their layout
        for (int x = 0; x < 400; x++) {
            buttonArray[x].setPreferredSize(new Dimension(60, 40));
            con = new GridBagConstraints();
            con.gridx = x % 20;
            con.gridy = (x / 20) + 1;
            add(buttonArray[x], con);
            buttonArray[x].setFont(new Font("SansSerif", Font.BOLD, 12));
            buttonArray[x].setText(" ");
            buttonArray[x].addActionListener(this);
            
        }
        

    }
    //the actionEvent that decides what happens when you click on the playfield
    @Override
    public void actionPerformed(ActionEvent ae) {

        int k = 500;
        for (int n = 0; n < 400; n++) {
            if (ae.getSource() == buttonArray[n]) {
                k = n;
            }
        }

        System.out.println(k);
        System.out.println("canclick= " + ln.getCanClicked());
        System.out.println("playernumber = " + ln.getPlayerNumber());
        canClick = ln.getCanClicked();
        playerNumber = ln.getPlayerNumber();

        //sends message to server containing the playernumber and the button or box that has been clicked
        if (canClick == true && playerNumber == 1) {

            System.out.println("button index +1= " + (k + 1));
            pServer.sendMessage("" + playerNumber + (k + 1));
            System.out.println("" + rServer.GetMessageFromServer());

        } else if (canClick == true && playerNumber == 2) {

            System.out.println("Inte min tur men Cirklar");

        } else if (canClick == false && playerNumber == 2) {
            System.out.println("button index +1= " + (k + 1));
            pServer.sendMessage("" + playerNumber + (k + 1));

        } else if (canClick == false && playerNumber == 1) {
            System.out.println("player 2 s tur men jag kryssar");
        }

    }

}
