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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.myProject.game.TTT.WhoHaveWon.displayWhosTheWinner;

public class TTT extends Application {

    private double[] possision;
    private boolean playable = true;
    private boolean playerTurnX = true;

    public Tile[][] getBoard() {
        return board;
    }

    private Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    Pane root = new Pane();

    Button restart = new Button();
    Label labelWelcome = new Label();

    private Parent createContent() {
        root.setPrefSize(700, 550);
        labelWelcome.setAlignment(Pos.TOP_CENTER);
        labelWelcome.setText("Welcome to TicTacToe game.");
        restart.setAlignment(Pos.TOP_RIGHT);
        restart.setText("Restart game");
        restart.setOnAction(e -> TTT.launch());

        root.getChildren().addAll(labelWelcome, restart);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 150);
                tile.setTranslateY(i* 150);
                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }
        for (int y = 0; y < board.length; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }
        //pion
        for (int x = 0; x < board.length; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }
        //przekątne
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    public void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                //String c = String.valueOf(combos.get(1));
                playable = false;
                playWinAnimation(combo);
                displayWhosTheWinner( " have won!");
                break;
            }
        }
    }
    private void playWinAnimation (Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[board.length].getCenterX());
        line.setEndY(combo.tiles[board.length].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                new KeyValue(line.endXProperty(), combo.tiles[board.length].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[board.length].getCenterY())));
        timeline.play();
    }

    public class Combo {
        private Tile[] tiles;
        public Combo (Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty() || tiles[2].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    &&tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    public class Tile extends StackPane {

        private Text text = new Text();


        public Tile() {
            Rectangle border = new Rectangle(150, 150);
            border.setFill(Color.BURLYWOOD);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, 76));
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(e -> {
                if (!playable) return;

                if (e.getButton() == MouseButton.PRIMARY) {
                    if (!playerTurnX) return;
                    drawX();
                    possision = new double[]{getTranslateX(), getTranslateY()};
                    playerTurnX = false;
                    checkState();

                } else {
                    if (playerTurnX) return;
                    computerTurnO();
                    playerTurnX = true;
                    checkState();
                }
            });
        }
        public double[] getPossision() {
            return possision;
        }

        private void drawX() {text.setText("X");
        }

        private void drawO() {text.setText("O");
        }

        public String getValue() {return text.getText();
        }

        public double getCenterX() {return getTranslateX() + 75;
        }

        public double getCenterY() {return getTranslateY() + 75;
        }
    }

    private void computerTurnO() {



    }

    @Override
    public String toString() {
        return "TTT{" +
                "combos=" + combos +
                '}';
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");

        primaryStage.setScene(new Scene(createContent()));

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

