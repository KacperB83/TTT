package com.myProject.game.TTT;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WhoHaveWon {

    public static void displayWhosTheWinner (String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font(45));

        Button restart = new Button();
        restart.setText("Restart Game");
        //restart.setOnAction(event -> TTT.);
        restart.setOnAction(event -> window.close());


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, restart);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
