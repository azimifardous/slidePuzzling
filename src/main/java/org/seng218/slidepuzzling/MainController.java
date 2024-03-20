package org.seng218.slidepuzzling;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label moves;
    @FXML
    private Label level;
    @FXML
    private GridPane gridPane;


    private final Button[][] buttonsArr;
    private final Puzzle puzzle;

    public MainController() {
        buttonsArr = new Button[3][3];
        puzzle = new Puzzle(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i, j;
        for(i = 0; i < 3; i++)
            for(j = 0; j < 3; j++) {
                buttonsArr[i][j] = new Button();
                gridPane.add(buttonsArr[i][j], j, i);
                buttonsArr[i][j].setPrefHeight(60);
                buttonsArr[i][j].setPrefWidth(60);
            }

        puzzle.generatePuzzle(buttonsArr);
        setMoves();
        setLevels();

        for(i = 0; i < 3; i++)
            for(j = 0; j < 3; j++) {
                int x = i, y = j;
                buttonsArr[i][j].setOnAction(e -> {
                    puzzle.slide(buttonsArr, x, y);
                    setMoves();
                });
            }
    }

    public void setLevels() {
        level.setText("Level: " + puzzle.getCurrentLevel());
    }
    public void setMoves() {
        moves.setText("Moves: " + puzzle.getNumberOfMoves());
    }

    public void handleNewGame(ActionEvent event) {
        if (!puzzle.isWin()) {
            puzzle.setCurrentLevel(1);
            puzzle.generatePuzzle(buttonsArr);
            setMoves();
            setLevels();
        }
    }
    public void handleRestartLevel(ActionEvent event) {
        if (!puzzle.isWin()) {
            puzzle.generatePuzzle(buttonsArr);
            setMoves();
        }
    }
    public void handleQuit(ActionEvent event) {
        System.exit(0);
    }



}
