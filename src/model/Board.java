package model;

public class Board {
    private final int rows;
    private final int cols;
    private final char[][] grid;
    private static final String[] COLORS = {
            "\u001B[31m",    // RED
            "\u001B[32m",    // GREEN
            "\u001B[33m",    // YELLOW
            "\u001B[34m",    // BLUE
            "\u001B[35m",    // PURPLE
            "\u001B[36m",    // CYAN
            "\u001B[91m",    // BRIGHT RED
            "\u001B[92m",    // BRIGHT GREEN
            "\u001B[93m",    // BRIGHT YELLOW
            "\u001B[94m",    // BRIGHT BLUE
            "\u001B[95m",    // BRIGHT PURPLE
            "\u001B[96m",    // BRIGHT CYAN
            "\u001B[31;1m",  // BOLD RED
            "\u001B[32;1m",  // BOLD GREEN
            "\u001B[33;1m",  // BOLD YELLOW
            "\u001B[34;1m",  // BOLD BLUE
            "\u001B[35;1m",  // BOLD PURPLE
            "\u001B[36;1m",  // BOLD CYAN
            "\u001B[31;4m",  // UNDERLINE RED
            "\u001B[32;4m",  // UNDERLINE GREEN
            "\u001B[33;4m",  // UNDERLINE YELLOW
            "\u001B[34;4m",  // UNDERLINE BLUE
            "\u001B[35;4m",  // UNDERLINE PURPLE
            "\u001B[36;4m",  // UNDERLINE CYAN
            "\u001B[37;1m",  // BOLD WHITE
            "\u001B[37;4m"   // UNDERLINE WHITE
    };
    private static final String RESET = "\u001B[0m";

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];

        // Initialize empty board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }

    public int getRow() {
        return this.rows;
    }

    public int getColumn() {
        return this.cols;
    }

    public boolean canPlaceBlock(Block block, int row, int col) {
        boolean[][] shape = block.getShape();

        // out of bounds check
        if (row + shape.length > rows || col + shape[0].length > cols) {
            return false;
        }

        // check if the block can be placed
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] && grid[row + i][col + j] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeBlock(Block block, int row, int col) {
        boolean[][] shape = block.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    grid[row + i][col + j] = block.getId();
                }
            }
        }
    }

    public void removeBlock(Block block, int row, int col) {
        boolean[][] shape = block.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    grid[row + i][col + j] = '.';
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        java.util.Map<Character, String> colorMap = new java.util.HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            colorMap.put(c, COLORS[c - 'A']);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = grid[i][j];
                if (c == '.') {
                    sb.append('.');
                } else {
                    sb.append(colorMap.get(c)).append(c).append(RESET);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }
}