package org.seng218.slidepuzzling;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Puzzle {
    private int currentLevel, numberOfMoves;
    private boolean isWin;
    private final MainController controller;

    public Puzzle(MainController controller) {
        this.controller = controller;
        // starts the current level with 1
        currentLevel = 1;
    }

    public void generatePuzzle(Button[][] arr) {
        isWin = false;
        numberOfMoves = 0;
        fillPuzzleWithNumbers(arr);
        shuffleNumbers(arr);
    }

    private void fillPuzzleWithNumbers(Button[][] arr) {
        int number = 1;
        for (Button[] elems : arr) {
            for (Button elem : elems) {
                elem.setText(Integer.toString(number));
                number++;
            }
        }
    }
    private void shuffleNumbers(Button[][] arr) {
        List<int[]> positions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(i == 2 && j == 2)) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        // Shuffle the positions
        Collections.shuffle(positions);

        int emptyRow = 2;
        int emptyCol = 2;

        // Perform the shuffling based on the shuffled positions
        for (int[] newPosition : positions) {
            int newRow = newPosition[0];
            int newCol = newPosition[1];

            arr[emptyRow][emptyCol].setText(arr[newRow][newCol].getText());
            arr[newRow][newCol].setText("");

            emptyRow = newRow;
            emptyCol = newCol;
        }
    }

    public void slide(Button[][] arr, int x, int y) {
        if (!isWin) {
            if (canMoveUp(x)) {
                moveTile(arr, x, y, x - 1, y);
            }
            if (canMoveDown(x)) {
                moveTile(arr, x, y, x + 1, y);
            }
            if (canMoveLeft(y)) {
                moveTile(arr, x, y, x, y - 1);
            }
            if (canMoveRight(y)) {
                moveTile(arr, x, y, x, y + 1);
            }
        }
    }

    private boolean canMoveUp(int x) {
        return x > 0;
    }
    private boolean canMoveDown(int x) {
        return x < 2;
    }
    private boolean canMoveLeft(int y) {
        return y > 0;
    }
    private boolean canMoveRight(int y) {
        return y < 2;
    }

    private void moveTile(Button[][] arr, int x1, int y1, int x2, int y2) {
        if (arr[x2][y2].getText().isEmpty()) {
            swapTiles(arr, x1, y1, x2, y2);
            check(arr);
        }
    }

    private void swapTiles(Button[][] arr, int x1, int y1, int x2, int y2) {
        String tempText = arr[x1][y1].getText();
        arr[x1][y1].setText(arr[x2][y2].getText());
        arr[x2][y2].setText(tempText);
    }

    public void check(Button[][] arr) {
        numberOfMoves++;
        boolean isComplete = true;
        int expectedNumber = 1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonText = arr[i][j].getText();
                if (!(buttonText.equals("") && expectedNumber == 9) &&
                        !buttonText.equals(Integer.toString(expectedNumber))) {
                    isComplete = false;
                    break;
                }
                expectedNumber++;
            }
            if (!isComplete)
                break;
        }

        if (isComplete)
            handleWinScene(arr);
    }


    public void handleWinScene(Button[][] arr) {
        Stage finishStage = new Stage();
        VBox finishVBox = new VBox();
        finishVBox.setId("root");
        Label finish = new Label("Successful");
        finish.setId("label");
        Button nextLevel = new Button("Next Level");
        Button quit = new Button("Quit");

        nextLevel.setOnAction(event -> {
            finishStage.close();
            currentLevel++;
            controller.setLevels();
            generatePuzzle(arr);
            controller.setMoves();
        });

        quit.setOnAction(e -> System.exit(0));

        finishStage.setOnCloseRequest(e -> handleWinScene(arr));

        finishVBox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(finish, new Insets(10, 0, 0, 0));
        VBox.setMargin(nextLevel, new Insets(10, 0, 0, 0));
        VBox.setMargin(quit, new Insets(5, 0, 0, 0));

        finishVBox.getChildren().addAll(finish, nextLevel, quit);
        Scene finishScene = new Scene(finishVBox, 200, 110);
        finishScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        finishStage.setTitle("Level Completed");
        finishStage.setResizable(false);
        finishStage.setAlwaysOnTop(true);
        finishStage.setScene(finishScene);
        finishStage.show();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public int getNumberOfMoves() {
        return numberOfMoves;
    }
    public boolean isWin() {
        return isWin;
    }
}
