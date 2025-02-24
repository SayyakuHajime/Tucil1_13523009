package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.geometry.Insets;
import model.Board;
import parser.FileParser;
import solver.BruteForceSolver;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GUI extends Application {
    private BorderPane mainLayout;
    private GridPane boardView;
    private VBox controlPanel;
    private TextArea outputArea;
    private FileParser.PuzzleConfig currentConfig;
    private Board board;

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();

        setupControlPanel();
        setupBoardView();
        setupOutputArea();

        Scene scene = new Scene(mainLayout, 1000, 800);
        primaryStage.setTitle("IQ Puzzler Pro Solver");

        // Set custom icon

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupBoardView() {
        boardView = new GridPane();
        boardView.setGridLinesVisible(true);
        boardView.setPadding(new Insets(10));
        boardView.setHgap(1);
        boardView.setVgap(1);
        mainLayout.setCenter(boardView);
    }

    private void setupControlPanel() {
        controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));

        Button loadButton = new Button("Load Puzzle");
        loadButton.setMaxWidth(Double.MAX_VALUE);

        Button solveButton = new Button("Solve");
        solveButton.setMaxWidth(Double.MAX_VALUE);

        loadButton.setOnAction(e -> handleLoadFile());
        solveButton.setOnAction(e -> handleSolve());

        controlPanel.getChildren().addAll(
                new Label("Controls:"),
                loadButton,
                solveButton
        );

        mainLayout.setRight(controlPanel);
    }

    private void setupOutputArea() {
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(5);
        outputArea.setWrapText(true);
        VBox.setMargin(outputArea, new Insets(10));
        mainLayout.setBottom(outputArea);
    }

    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Puzzle File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                currentConfig = FileParser.parseFile(file.getPath());
                board = new Board(currentConfig.rows, currentConfig.cols);
                updateBoardView();
                outputArea.setText("Loaded puzzle:\n" +
                        "Size: " + currentConfig.rows + "x" + currentConfig.cols + "\n" +
                        "Number of blocks: " + currentConfig.blocks.size());
            } catch (Exception e) {
                outputArea.setText("Error loading file: " + e.getMessage());
            }
        }
    }

    private void handleSolve() {
        if (currentConfig == null || board == null) {
            outputArea.setText("Please load a puzzle first!");
            return;
        }

        BruteForceSolver solver = new BruteForceSolver(board, currentConfig.blocks);
        long startTime = System.currentTimeMillis();
        boolean solved = solver.solve();
        long endTime = System.currentTimeMillis();

        if (solved) {
            updateBoardView();
            outputArea.setText("Solution found!\n" +
                    "Time taken: " + (endTime - startTime) + " ms\n" +
                    "Iterations: " + solver.getIterationCount());

            // Ask user if they want to save the solution
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Solution");
            alert.setHeaderText("Would you like to save this solution?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeSave = new ButtonType("Save");
            ButtonType buttonTypeCancel = new ButtonType("Don't Save", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeCancel);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeSave) {
                    handleSaveSolution();
                }
            });
        } else {
            outputArea.setText("No solution exists.\n" +
                    "Time taken: " + (endTime - startTime) + " ms\n" +
                    "Iterations: " + solver.getIterationCount());
        }
    }

    private void handleSaveSolution() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Solution");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Save solution to file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    // Write board dimensions
                    writer.write(board.getRow() + " " + board.getColumn() + "\n");

                    // Write solution grid
                    for (int i = 0; i < board.getRow(); i++) {
                        for (int j = 0; j < board.getColumn(); j++) {
                            writer.write(board.getCell(i, j));
                        }
                        writer.newLine();
                    }

                    outputArea.appendText("\nSolution saved to: " + file.getPath());
                }
            } catch (IOException e) {
                outputArea.appendText("\nError saving solution: " + e.getMessage());
            }
        }
    }

    private void updateBoardView() {
        boardView.getChildren().clear();

        // Create cells with equal size
        double cellSize = 40;

        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumn(); j++) {
                Pane cell = new Pane();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");

                if (board.getCell(i, j) != '.') {
                    // Get color based on block ID
                    String color = getColorForBlock(board.getCell(i, j));
                    cell.setStyle("-fx-background-color: " + color + "; -fx-border-color: black;");

                    // Add label with block ID
                    Label label = new Label(String.valueOf(board.getCell(i, j)));
                    label.setStyle("-fx-text-fill: white;");
                    cell.getChildren().add(label);
                }

                boardView.add(cell, j, i);
            }
        }
    }

    private String getColorForBlock(char blockId) {
        // Map block IDs to colors
        String[] colors = {
                "#FF0000",   // Red
                "#00FF00",   // Green
                "#0000FF",   // Blue
                "#FFFF00",   // Yellow
                "#FF00FF",   // Magenta
                "#00FFFF",   // Cyan
                "#FFA500",   // Orange
                "#800080",   // Purple
                "#008000",   // Dark Green
                "#000080",   // Navy
                "#800000",   // Maroon
                "#808000",   // Olive
                "#FF69B4",   // Hot Pink
                "#4B0082",   // Indigo
                "#8B4513",   // Saddle Brown
                "#483D8B",   // Dark Slate Blue
                "#2F4F4F",   // Dark Slate Gray
                "#9400D3",   // Dark Violet
                "#8B008B",   // Dark Magenta
                "#556B2F",   // Dark Olive Green
                "#8B0000",   // Dark Red
                "#4682B4",   // Steel Blue
                "#D2691E",   // Chocolate
                "#9932CC",   // Dark Orchid
                "#8FBC8F",   // Dark Sea Green
                "#E9967A"    // Dark Salmon
        };

        // Map A to 0, B to 1, etc.
        int index = blockId - 'A';
        if (index >= 0 && index < colors.length) {
            return colors[index];
        }
        return "#808080"; // Default gray color for unknown blocks
    }
}