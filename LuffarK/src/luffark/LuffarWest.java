/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffark;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Reza
 */
public class LuffarWest extends JPanel implements ActionListener {

    JButton btnNewGame, btnInfo, btnHighScore;
    PrintServer pServer;
    ReadServer rServer;
    String playerName;
    VarHolder varHolder;
    int playerNumber;
    boolean canClick;
    JButton[] buttonArray;
    LuffarNorth ln;

    public LuffarWest(JButton[] arr, boolean canClick, int playerNumber, PrintServer pServer, ReadServer rServer, VarHolder varHolder, LuffarNorth ln) {

        this.buttonArray = arr;
        this.canClick = canClick;
        this.playerNumber = playerNumber;
        this.pServer = pServer;
        this.rServer = rServer;
        this.varHolder = varHolder;
        this.ln = ln;

        GridBagLayout gr = new GridBagLayout();

        setLayout(gr);
        GridBagConstraints con;

        for (int x = 0; x < 400; x++) {
            buttonArray[x].setPreferredSize(new Dimension(60, 40));
            con = new GridBagConstraints();
            con.gridx = x % 20;
            con.gridy = (x / 20) + 1;
            //con.insets = new Insets(0, 10, 0, 0);
            add(buttonArray[x], con);
            buttonArray[x].setFont(new Font("SansSerif", Font.BOLD, 12));
            buttonArray[x].setText(" ");
            buttonArray[x].addActionListener(this);
            
        }
        

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        int k = 500;
        for (int n = 0; n < 400; n++) {
            if (ae.getSource() == buttonArray[n]) {
                k = n;
            }
        }

        System.out.println(k);
        buttonArray[k].removeActionListener(this);
        System.out.println("canclick= " + ln.getCanClicked());
        System.out.println("playernumber = " + ln.getPlayerNumber());
        canClick = ln.getCanClicked();
        playerNumber = ln.getPlayerNumber();

        if (canClick == true && playerNumber == 1) {

            System.out.println("button index +1= " + (k + 1));
            pServer.sendMessage("" + playerNumber + (k + 1));
            //ln.setCanClicked(false);
            System.out.println("" + rServer.GetMessageFromServer());

        } else if (canClick == true && playerNumber == 2) {

            System.out.println("Inte min tur men Cirklar");
            //ln.setCanClicked(false);

        } else if (canClick == false && playerNumber == 2) {
            System.out.println("button index +1= " + (k + 1));
            pServer.sendMessage("" + playerNumber + (k + 1));
            //ln.setCanClicked(true);

        } else if (canClick == false && playerNumber == 1) {
            System.out.println("player 2 s tur men jag kryssar");
            //ln.setCanClicked(true);
        }

    }

}
