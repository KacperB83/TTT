package com.myProject.game.TTT;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class TTT extends Application {

    private boolean playable = true;
    private boolean playerTurnX = true;
    public String choice;

    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    public StackPane root;

    private Pane tileRoot;

    private Parent createContent() {

        root = new StackPane();
        tileRoot = new Pane();

        root.setPrefSize(600, 600);
        tileRoot.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i* 200);
                tile.text.setText("");
                tileRoot.getChildren().add(tile);

                board[j][i] = tile;
            }
        }
        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }
        //pion
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }
        //przekątne
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        root.getChildren().add(tileRoot);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");

        primaryStage.setScene(new Scene(createContent()));

        primaryStage.show();
    }

    public void checkState(String choice) {

        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                displayWhosTheWinner("  "+ choice +  " have won!  ");
                break;
            }
        }
        boolean isFull = true;
        for (Tile[] row : board){
            for (Tile t : row) {
                if (t.getValue().equals(""))
                    isFull = false;
            }
        }
        if (isFull) {
            playable = false;
            displayWhosTheWinner("This is draw");
        }
    }

    private void playWinAnimation (Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());

        tileRoot.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();

        timeline.setOnFinished(event -> tileRoot.getChildren().remove(line));
    }

    public class Tile extends StackPane {

        private Text text = new Text();
        private String choice;

        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, 76));
            text.setFill(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (!playable) return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (playerTurnX) {
                        drawX();
                        choice = getValue();
                        playerTurnX = false;
                        checkState(choice);
                    }
                    if (playable) {
                        computerMove();
                    }
                }
            });
        }

        private void drawX() {text.setText("X");
        }

        private void drawO() {text.setText("O");
        }

        public String getValue() {return text.getText();
        }

        public double getCenterX() {return getTranslateX() + 100;
        }

        public double getCenterY() {return getTranslateY() + 100;
        }
    }

    public void computerMove() {

        if (board[0][0].getValue().isEmpty() && checkLeftUp()) {

            board[0][0].drawO();
            choice = board[0][0].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[0][1].getValue().isEmpty() && checkCentreUp()) {

            board[0][1].drawO();
            choice = board[0][1].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[0][2].getValue().isEmpty() && checkRightUp()) {

            board[0][2].drawO();
            choice = board[0][2].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[1][0].getValue().isEmpty() && checkLeftCentre()) {

            board[1][0].drawO();
            choice = board[1][0].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[1][1].getValue().isEmpty() && checkCentreCentre()) {

            board[1][1].drawO();
            choice = board[1][1].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[1][2].getValue().isEmpty() && checkRightCentre()) {

            board[1][2].drawO();
            choice = board[1][2].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[2][0].getValue().isEmpty() && checkLeftBottom()) {

            board[2][0].drawO();
            choice = board[2][0].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[2][1].getValue().isEmpty() && checkCentreBottom()) {

            board[2][1].drawO();
            choice = board[2][1].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        if (board[2][2].getValue().isEmpty() && checkRightBottom()) {

            board[2][2].drawO();
            choice = board[2][2].getValue();
            checkState(choice);
        }
        else {
            randomMove();
        }
    }
    public boolean checkLeftUp(){
        if(board[1][0].getValue().equals("O") ||
                board[0][1].getValue().equals("O") || board[1][1].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkCentreUp(){
        if(board[0][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[0][2].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkRightUp(){
        if(board[0][1].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[1][2].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkLeftCentre(){
        if(board[0][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[0][2].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkCentreCentre(){
        if(board[0][0].getValue().equals("O") || board[0][1].getValue().equals("O") || board[0][2].getValue().equals("O") ||
                board[1][0].getValue().equals("O") || board[1][2].getValue().equals("O") ||
                board[2][0].getValue().equals("O") || board[2][1].getValue().equals("O") || board[2][2].getValue().equals("O")
        ) {
            return true;
        }
        return false;
    }
    public boolean checkRightCentre(){
        if(board[0][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[0][2].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkLeftBottom(){
        if(board[1][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[2][1].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkCentreBottom(){
        if(board[2][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[2][2].getValue().equals("O")) {
            return true;
        }
        return false;
    }
    public boolean checkRightBottom(){
        if(board[1][0].getValue().equals("O") ||
                board[1][1].getValue().equals("O") || board[2][1].getValue().equals("O")) {
            return true;
        }
        return false;
    }

    public void randomMove() {
        Random random = new Random();

        int x = random.nextInt(3);
        int y = random.nextInt(3);

        if (board[x][y].getValue().isEmpty()) {
            board[x][y].drawO();
            choice = board[x][y].getValue();
            checkState(choice);
            playerTurnX = true;
        }
        else {
            randomMove();
        }
    }

    public class Combo {
        private Tile[] tiles;
        public Combo (Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            return  !tiles[0].getValue().isEmpty() &&
                    tiles[0].getValue().equals(tiles[1].getValue()) &&
                    tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    public void reset(){
        playerTurnX = true;
        playable = true;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                board[i][j].text.setText("");
            }
        }
    }

    public void displayWhosTheWinner(String message) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font(45));

        Button restart = new Button();
        restart.setText("Restart Game");

        restart.setOnAction(event -> {
            reset();
            window.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, restart);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

