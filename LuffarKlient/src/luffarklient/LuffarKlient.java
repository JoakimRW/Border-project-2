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

    Button btnNewGame,btnInfo,btnHighScore,btnSetName;
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
    int portNumber = 3004;
    PrintServer pServer;
    Boolean player1,player2;
    

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
        ArrayList<Button> arr = new ArrayList<>();

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
                    Paint lightblue = Color.LIGHTBLUE;
                    Paint black = Color.BLACK;
                    Circle circle = new Circle(5, lightblue);
                    circle.setStroke(black);
                    playButton.setGraphic(circle);
                    playButton.setDisable(true);
                    System.out.println("button index +1= "+(arr.indexOf(playButton)+1));
                    pServer.sendMessage(""+(arr.indexOf(playButton)+1));
                }
            });

        }
        btnNewGame.setOnAction(buttonConnectEventHandler);

        scoreArea.getChildren().addAll(lblTurnNr, lblTime, lblPlayer1, lblPlayer2, txtChat,  txtSetName);
        buttonArea.getChildren().addAll(btnNewGame,btnSetName, btnInfo, btnHighScore);

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
                ReadServer rClient = new ReadServer(socket);
                Thread t1 = new Thread(pServer);
                t1.start();
                Thread t2 = new Thread(rClient);
                t2.start();

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


