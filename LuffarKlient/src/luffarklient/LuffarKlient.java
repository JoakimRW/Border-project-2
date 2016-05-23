/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarklient;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    @Override
    public void start(Stage primaryStage) {
        Button btnNewGame = new Button("New Game");
        Button btnInfo = new Button("Help");
        Button btnHighScore = new Button("High Score");
        GridPane playArea = new GridPane();
        int rows = 1;
        int rowcheck = 20;
        int cols = 1;
        Label lblTurnNr = new Label("Turn Number : " + 0);
        Label lblTime = new Label("Time : " + 0);
        Label lblPlayer1 = new Label("Player 1 : ");
        Label lblPlayer2 = new Label("Player 2 : ");; 
        TextArea txtChat = new TextArea();
        ArrayList<Button> arr = new ArrayList<>();

        VBox buttonArea = new VBox();
        GridPane root = new GridPane();
        VBox scoreArea = new VBox();
        
        btnNewGame.setMaxWidth(Double.MAX_VALUE);
        btnInfo.setMaxWidth(Double.MAX_VALUE);
        btnHighScore.setMaxWidth(Double.MAX_VALUE);
        
        playArea.setMinSize(300, 300);
        
        
        scoreArea.getChildren().addAll(lblTurnNr,lblTime,lblPlayer1,lblPlayer2,txtChat);
        for (int t = 0; t < 400; t++) {
            Button playButton = new Button("" + (t + 1));
            playButton.setMaxWidth(Double.MAX_VALUE);
            playButton.setStyle(""
                    + "-fx-background-color: lightblue; "
                    + "-fx-text-fill: black;"
                    + "-fx-border-color: black;"
                    + "-fx-arc-height: 0;"
                    + "-fx-arc-width: 0");
            playArea.add(playButton, cols, rows);
            cols++;
            arr.add(playButton);
            if (t + 1 == rowcheck) {
                System.out.println("t = " +t);
                cols= 1;
                rows++;
                rowcheck+=20;
                System.out.println("rowcheck = " +rowcheck);
            }

        }
        buttonArea.getChildren().addAll(btnNewGame, btnInfo,btnHighScore);
        
        GridPane.setMargin(buttonArea, new Insets(0,0,20,0));
        GridPane.setMargin(playArea, new Insets(0,10,0,0));
        root.add(buttonArea, 1, 1);
        root.add(playArea, 1, 2);
        root.add(scoreArea, 2, 2);
        Scene scene = new Scene(root, 1300, 650);

        primaryStage.setTitle("TickTackToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
