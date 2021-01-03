package com.myProject.game.TTT;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TTT extends Application {

    Button restart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");
        Label labelWelcome = new Label("Welcome to TicTacToe game.");
        labelWelcome.setAlignment(Pos.TOP_CENTER);
        restart = new Button();
        restart.setText("Restart game");
        restart.setOnAction(e -> TTT.launch());


        StackPane layout = new StackPane();
        layout.getChildren().addAll(labelWelcome, restart);

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}

