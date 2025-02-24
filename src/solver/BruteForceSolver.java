package solver;

import model.Board;
import model.Block;
import java.util.List;

public class BruteForceSolver {
    private final Board board;
    private final List<Block> blocks;
    private long iterationCount;
    private final boolean[] used;

    public BruteForceSolver(Board board, List<Block> blocks) {
        this.board = board;
        this.blocks = blocks;
        this.iterationCount = 0;
        this.used = new boolean[blocks.size()];

        validateInput();

        int totalBlockCells = 0;
        for (Block block : blocks) {
            boolean[][] shape = block.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j]) {
                        totalBlockCells++;
                    }
                }
            }
        }

        int boardSize = board.getRow() * board.getColumn();
        if (totalBlockCells > boardSize) {
            System.out.println("Warning: Total block cells (" + totalBlockCells +
                    ") exceed board size (" + boardSize + "). No solution possible.");
        }
    }

    private void validateInput() {
        if (board.getRow() <= 0 || board.getColumn() <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive.");
        }

        if (blocks == null || blocks.size() < 1) {
            throw new IllegalArgumentException("There must be at least one block.");
        }

        for (Block block : blocks) {
            if (block == null || block.getShape() == null) {
                throw new IllegalArgumentException("Block and its shape must not be null.");
            }

            boolean[][] shape = block.getShape();
            boolean hasTrue = false;
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j]) {
                        hasTrue = true;
                        break;
                    }
                }
                if (hasTrue) break;
            }

            if (!hasTrue) {
                throw new IllegalArgumentException("Block must have at least one cell.");
            }
        }
    }
    private boolean isBoardFilled() {
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumn(); j++) {
                if (board.getCell(i, j) == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean solve() {
        System.out.println("Starting solve with " + blocks.size() + " blocks");
        return solveRecursive(0);
    }

    private boolean solveRecursive(int depth) {
        if (isAllBlocksUsed()) {
            return isBoardFilled();
        }

        int emptyCells = calculateEmptyCells();
        int remainingNeededCells = calculateRemainingNeededCells();

        if (emptyCells < remainingNeededCells) {
            return false;
        }

        for (int blockIndex = 0; blockIndex < blocks.size(); blockIndex++) {
            if (used[blockIndex]) continue;

            Block originalBlock = blocks.get(blockIndex);
            used[blockIndex] = true;

            // rotation
            Block currentBlock = originalBlock;
            for (int rotation = 0; rotation < 4; rotation++) {
                // normal and mirrored
                Block normalBlock = currentBlock;
                Block mirroredBlock = currentBlock.mirror();

                for (int flip = 0; flip < 2; flip++) {
                    Block blockToTry = (flip == 0) ? normalBlock : mirroredBlock;

                    // position
                    for (int row = 0; row < board.getRow(); row++) {
                        for (int col = 0; col < board.getColumn(); col++) {
                            iterationCount++;

                            if (board.canPlaceBlock(blockToTry, row, col)) {
                                board.placeBlock(blockToTry, row, col);

                                if (solveRecursive(depth + 1)) {
                                    return true;
                                }

                                board.removeBlock(blockToTry, row, col);
                            }
                        }
                    }
                }
                currentBlock = currentBlock.rotate();
            }
            used[blockIndex] = false;
        }
        return false;
    }

    private int calculateEmptyCells() {
        int empty = 0;
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumn(); j++) {
                if (board.getCell(i, j) == '.') {
                    empty++;
                }
            }
        }
        return empty;
    }

    private int calculateRemainingNeededCells() {
        int needed = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (!used[i]) {
                boolean[][] shape = blocks.get(i).getShape();
                for (int row = 0; row < shape.length; row++) {
                    for (int col = 0; col < shape[row].length; col++) {
                        if (shape[row][col]) {
                            needed++;
                        }
                    }
                }
            }
        }
        return needed;
    }

    private boolean isAllBlocksUsed() {
        for (boolean isUsed : used) {
            if (!isUsed) return false;
        }
        return true;
    }

    public long getIterationCount() {
        return iterationCount;
    }
}