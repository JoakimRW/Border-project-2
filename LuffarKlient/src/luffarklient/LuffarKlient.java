/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarklient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/*btnNewGame.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });*/
/**
 *
 * @author JRW
 */
public class LuffarKlient extends Application {

    Button btnNewGame, btnInfo, btnHighScore, btnSetName;
    TextArea txtChat;
    GridPane playArea;
    Label lblTurnNr;
    Label lblTime;
    Label lblPlayer1;
    Label lblPlayer2;
    VBox scoreArea;
    VBox buttonArea;
    GridPane root;
    TextField txtSetName;
    String adress = "localhost";
    int portNumber = 3004, playerNumber = 0;
    PrintServer pServer;
    ReadServer rServer;
    Boolean player1, player2, canClick;
    TextInputDialog getName;
    String playerName;
    ArrayList<Button> arr;
    private int[] pArr = new int[400];
    VarHolder varHolder = new VarHolder();

    @Override
    public void start(Stage primaryStage) {
        btnNewGame = new Button("New Game");
        btnInfo = new Button("Help");
        btnHighScore = new Button("High Score");
        btnSetName = new Button("Set Name");
        playArea = new GridPane();
        txtSetName = new TextField();
        int rows = 1;
        int rowcheck = 20;
        int cols = 1;
        lblTurnNr = new Label("Turn Number : " + 0);
        lblTime = new Label("Time : " + 0);
        lblPlayer1 = new Label("Player 1 : ");
        lblPlayer2 = new Label("Player 2 : ");

        txtChat = new TextArea();
        arr = new ArrayList<>();

        buttonArea = new VBox();
        GridPane root = new GridPane();
        scoreArea = new VBox();

        btnNewGame.setMaxWidth(Double.MAX_VALUE);
        btnSetName.setMaxWidth(Double.MAX_VALUE);
        btnInfo.setMaxWidth(Double.MAX_VALUE);
        btnHighScore.setMaxWidth(Double.MAX_VALUE);

        playArea.setMinSize(300, 300);

        for (int t = 0; t < 400; t++) {
            Button playButton = new Button();
            playButton.setPrefWidth(40);
            playButton.setMaxWidth(Double.MAX_VALUE);
            playButton.setStyle(""
                    + "-fx-background-color: lightblue; "
                    + "-fx-text-fill: black;"
                    + "-fx-border-color: black;"
                    + "-fx-arc-height: 0;"
                    + "-fx-arc-width: 0;");
            playArea.add(playButton, cols, rows);
            cols++;
            arr.add(playButton);
            if (t + 1 == rowcheck) {
                cols = 1;
                rows++;
                rowcheck += 20;
            }
            playButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World!");
                    if (canClick != false) {

                        System.out.println("button index +1= " + (arr.indexOf(playButton) + 1));
                        pServer.sendMessage("" + playerNumber + (arr.indexOf(playButton) + 1));
                        canClick = false;
                        recMessage();
                        System.out.println("" + rServer.GetMessageFromServer());
                    }
                }
            });

        }
        btnNewGame.setOnAction(buttonConnectEventHandler);

        scoreArea.getChildren().addAll(lblTurnNr, lblTime, lblPlayer1, lblPlayer2, txtChat);
        buttonArea.getChildren().addAll(btnNewGame, btnInfo, btnHighScore);

        GridPane.setMargin(buttonArea, new Insets(0, 0, 20, 0));
        GridPane.setMargin(playArea, new Insets(0, 10, 0, 0));
        root.add(buttonArea, 1, 1);
        root.add(playArea, 1, 2);
        root.add(scoreArea, 2, 2);
        Scene scene = new Scene(root, 1300, 700);

        primaryStage.setTitle("TickTackToe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void recMessage() {
        System.out.println("MFS = " + varHolder.getMessage());
        while (varHolder.getMessage() == null) {
            System.out.println("Väntar på meddelande");
        }
        System.out.println("MFS After = " + varHolder.getMessage());
        
            int pNumber, pIndex;

            pNumber = Integer.parseInt(varHolder.getMessage().substring(0, 1));
            System.out.println("pNumber = " + pNumber);
            pIndex = Integer.parseInt(varHolder.getMessage().substring(1)) - 1;
            System.out.println("pIndex = " + pIndex);
            pArr[pIndex] = pNumber;
            rServer.resetMessageFromServer();
            if (pNumber == 1) {
                Paint lightblue = Color.LIGHTBLUE;
                Paint black = Color.BLACK;
                Circle circle = new Circle(5, lightblue);
                circle.setStroke(black);
                arr.get(pIndex).setGraphic(circle);
                if (playerNumber == 1) {
                    canClick = false;
                } else {
                    canClick = true;
                }
            } else if (pNumber == 2) {
                arr.get(pIndex).setText("x");
                if (playerNumber == 2) {
                    canClick = false;
                } else {
                    canClick = true;
                }
            }
         
    }

    EventHandler<ActionEvent> buttonConnectEventHandler
            = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("Klienten startar");
            try {
//			Skapar en anslutning mot en server, denna kan kasta ett par exceptions (vilka alla är eller ligger under IOException)
                Socket socket = new Socket(adress, portNumber);

//			Kör igång en tråd för att kunna skriva till servern, klassen ser samma ut som serverns skrivar klass.			
                pServer = new PrintServer(socket);
                rServer = new ReadServer(socket,varHolder);
                Thread t1 = new Thread(pServer);
                t1.start();
                Thread t2 = new Thread(rServer);
                t2.start();
                getName = new TextInputDialog();
                getName.setTitle("Name");
                getName.setContentText("Please fill in your name");
                Optional<String> result = getName.showAndWait();
                playerName = result.get();
                pServer.sendMessage(playerName);

                while (playerNumber == 0) {
                    System.out.println("Väntar på Spelarnummer");
                    if (varHolder.getMessage() != null) {
                        playerNumber = Integer.parseInt(varHolder.getMessage());
                        System.out.println("Har fått spelarnummer = " + playerNumber);
                        if (playerNumber == 1) {
                            canClick = true;
                        }
                        if (playerNumber == 2) {
                            canClick = false;
                        }
                        varHolder.setMessage(null);
                        System.out.println("Message is reset = " + rServer.GetMessageFromServer());
                    }

                }
                btnNewGame.setDisable(true);
            } //Denna catch-sats fångar exception från nästan alla rader i try-satsen, enkelt att göra men kanske inte så bra då det blir så generellt.
            catch (IOException e) {
                System.out.println("Exception som kastades: " + e);
            }

            btnNewGame.setDisable(true);
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
