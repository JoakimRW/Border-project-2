/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.graphtest;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author JRW
 */
public class FXMLDocumentController implements Initializable {

    int cols = 1, rows = 1, playerNumber = 0, portNumber = 3004;
    boolean playAreaConstructed = false, canClick;
    ArrayList<Button> arr = new ArrayList<>();
    PrintServer pServer;
    ReadServer rServer;
    String playerName, adress = "localhost";
    TextInputDialog getName;
    VarHolder varHolder = new VarHolder();

    @FXML
    private GridPane playArea;
    
    @FXML
    private Button btnNewGame;

    @FXML
    private HBox buttonArea;

    @FXML
    private VBox scoreArea;

    @FXML
    private GridPane gameArea;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        connectToServer();
        
        constructPlayArea();

    }
    
    public void connectToServer(){
        try {
            Socket socket = new Socket(adress, portNumber);
            pServer = new PrintServer(socket);
            rServer = new ReadServer(socket, varHolder, arr);
            Thread t1 = new Thread(pServer);
            t1.start();
            Thread t2 = new Thread(rServer);
            t2.start();
            getName = new TextInputDialog();
            getName.setTitle("Name");
            getName.setContentText("Please fill in your name");
            Optional<String> result = getName.showAndWait();
            playerName = result.get();
            varHolder.setPlayerName(playerName);
            pServer.sendMessage(playerName);
            while (playerNumber == 0) {
                System.out.println("Väntar på Spelarnummer");
                if (varHolder.getMessage() != null) {
                    playerNumber = Integer.parseInt(varHolder.getMessage());
                    varHolder.setPlayerNumber(playerNumber);
                    System.out.println("Har fått spelarnummer = " + playerNumber);

                    canClick = true;

                    varHolder.setMessage(null);
                    System.out.println("Message is reset = " + varHolder.getMessage());
                }

            }
            btnNewGame.setDisable(true);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void constructPlayArea() {
        if (playAreaConstructed == false) {
            playAreaConstructed = true;
            for (int t = 0; t < 400; t++) {
                Button playButton = new Button();
                arr.add(playButton);
                playButton.setPrefWidth(40);
                playButton.setMaxWidth(Double.MAX_VALUE);
                playButton.setStyle(""
                        + "-fx-background-color: lightblue; "
                        + "-fx-text-fill: black;"
                        + "-fx-border-color: black;"
                        + "-fx-arc-height: 0;"
                        + "-fx-arc-width: 0;");
                playButton.setOnAction(playButtonClicked);

                playArea.add(playButton, cols, rows);
                cols++;
                if (cols == 21) {
                    cols = 1;
                    rows++;
                }
            }
        } else {
            System.out.println("playArea already Constructed");
        }
    }

    EventHandler<ActionEvent> playButtonClicked
            = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("playButton clicked index = " + arr.indexOf(event.getSource()));
            
            if (canClick == true && playerNumber == 1) {

                        System.out.println("button index +1= " + (arr.indexOf(event.getSource()) + 1));
                        pServer.sendMessage("" + playerNumber + (arr.indexOf(event.getSource()) + 1));
                        System.out.println("" + rServer.GetMessageFromServer());
                    } else if (canClick == true && playerNumber == 2) {
                        System.out.println("player 1's tur men Cirklar");
                        canClick = false;
                    } else if (canClick == false && playerNumber == 2) {
                        System.out.println("button index +1= " + (arr.indexOf(event.getSource()) + 1));
                        pServer.sendMessage("" + playerNumber + (arr.indexOf(event.getSource()) + 1));
                    } else if (canClick == false && playerNumber == 1) {
                        System.out.println("player 2's tur men kryssar");
                        canClick = true;
                    }
        }

    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gameArea.setConstraints(buttonArea, 1, 1);
        gameArea.setConstraints(scoreArea, 2, 2);
        gameArea.setConstraints(playArea, 1, 2);
        GridPane.setMargin(buttonArea, new Insets(0, 0, 20, 0));
        GridPane.setMargin(playArea, new Insets(0, 10, 0, 0));

    }

}
