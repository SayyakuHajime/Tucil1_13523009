import parser.FileParser;
import model.Board;
import solver.BruteForceSolver;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
// Import for GUI, comment this if using CLI only
import javafx.application.Application;
import gui.GUI;


public class Main {
    public static void main(String[] args) {
        System.out.print("Choose mode (gui/cli): ");
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine().trim().toLowerCase();

        if (mode.equals("gui")) {
            // Comment this if using CLI only
            Application.launch(GUI.class, args);
        } else {
            try {
                scanner = new Scanner(System.in);
                String testFile;

                if (args.length > 0) {
                    testFile = args[0];
                    System.out.println("Initial testFile value: " + testFile);
                } else {
                    System.out.print("Enter the input file path: ");
                    testFile = scanner.nextLine().trim();
                }

                FileParser.PuzzleConfig config = FileParser.parseFile(testFile);

                Board board = new Board(config.rows, config.cols);
                BruteForceSolver solver = new BruteForceSolver(board, config.blocks);

                long startTime = System.currentTimeMillis();
                boolean solved = solver.solve();
                long endTime = System.currentTimeMillis();

                if (solved) {
                    System.out.println("\nSolution found!");
                    System.out.println(board);
                } else {
                    System.out.println("\nNo solution exists.");
                }

                System.out.println("Time taken: " + (endTime - startTime) + " ms");
                System.out.println("Total iterations: " + solver.getIterationCount());

                // save solution
                if (solved) {
                    System.out.print("\nDo you want to save the solution? (y/n): ");
                    String response = scanner.nextLine().trim().toLowerCase();

                    if (response.startsWith("y")) {
                        System.out.print("Enter output file name: ");
                        String outputFile = scanner.nextLine().trim() + ".txt";

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                            writer.write(board.getRow() + " " + board.getColumn() + " " + config.numBlocks + "\n");

                            for (int i = 0; i < board.getRow(); i++) {
                                for (int j = 0; j < board.getColumn(); j++) {
                                    writer.write(board.getCell(i, j));
                                }
                                writer.newLine();
                            }
                            System.out.println("Solution saved to: " + outputFile);
                        } catch (IOException e) {
                            System.err.println("Error saving solution: " + e.getMessage());
                        }
                    }
                    scanner.close();
                }

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}