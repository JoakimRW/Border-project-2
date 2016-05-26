/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarklient;

import javafx.scene.control.Button;

/**
 *
 * @author JRW
 */
public class ButtonView {

    public ButtonView() {
        
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
        }
    }
}
