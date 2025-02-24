package parser;

import model.Block;
import java.io.*;
import java.util.*;

public class FileParser {
    public static class PuzzleConfig {
        public final int rows;
        public final int cols;
        public final int numBlocks;
        public final String puzzleType;
        public final List<Block> blocks;

        public PuzzleConfig(int rows, int cols, int numBlocks, String puzzleType, List<Block> blocks) {
            this.rows = rows;
            this.cols = cols;
            this.numBlocks = numBlocks;
            this.puzzleType = puzzleType;
            this.blocks = blocks;

            /* //Debug print
            System.out.println("\nParsed Configuration:");
            System.out.println("Dimensions: " + rows + "x" + cols);
            System.out.println("Number of blocks: " + numBlocks);
            System.out.println("Type: " + puzzleType);
            System.out.println("\nParsed Blocks:");
            for (Block block : blocks) {
                System.out.println("\nBlock " + block.getId() + ":");
                System.out.println(block.toString());
            }

             */

        }
    }

    public static PuzzleConfig parseFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] dimensions = reader.readLine().trim().split("\\s+");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            int numBlocks = Integer.parseInt(dimensions[2]);

            String puzzleType = reader.readLine().trim();
            if (!puzzleType.equals("DEFAULT")) {
                if (!puzzleType.equals("CUSTOM") && !puzzleType.equals("PYRAMID")) {
                    throw new IOException("Invalid puzzle type: " + puzzleType);
                }
                else {
                    throw new IOException("Puzzle type not supported yet");
                }
            }

            List<Block> blocks = new ArrayList<>();
            List<String> currentBlockLines = new ArrayList<>();
            String line;
            char currentId = '\0';

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (!trimmedLine.isEmpty()) {
                    char firstChar = 0;
                    for (char c : line.toCharArray()) {
                        if (c != ' ') {
                            firstChar = c;
                            break;
                        }
                    }

                    if (currentBlockLines.isEmpty() || firstChar != currentId) {
                        if (!currentBlockLines.isEmpty()) {
                            // Validate block before adding
                            validateBlock(currentId, currentBlockLines);
                            blocks.add(new Block(currentId, currentBlockLines.toArray(new String[0])));
                            currentBlockLines.clear();
                        }
                        currentId = firstChar;
                        currentBlockLines.add(line);
                    } else {
                        currentBlockLines.add(line);
                    }
                }
            }

            if (!currentBlockLines.isEmpty()) {
                // Validate final block
                validateBlock(currentId, currentBlockLines);
                blocks.add(new Block(currentId, currentBlockLines.toArray(new String[0])));
            }

            if (blocks.size() != numBlocks) {
                throw new IOException("Number of parsed blocks (" + blocks.size() +
                        ") doesn't match specified count (" + numBlocks + ")");
            }

            return new PuzzleConfig(rows, cols, numBlocks, puzzleType, blocks);
        }
    }

    private static void validateBlock(char id, List<String> blockLines) throws IOException {
        // Check if block is empty
        if (blockLines.isEmpty()) {
            throw new IOException("Block " + id + " is empty");
        }

        // Check if block has only spaces
        boolean hasNonSpace = false;
        for (String line : blockLines) {
            if (!line.trim().isEmpty()) {
                hasNonSpace = true;
                break;
            }
        }
        if (!hasNonSpace) {
            throw new IOException("Block " + id + " contains only spaces");
        }

        // Check if block character is valid (A-Z)
        if (id < 'A' || id > 'Z') {
            throw new IOException("Invalid block identifier: " + id + ". Must be A-Z");
        }
    }
}