package model;

public class Block {
    private final char id;
    private final boolean[][] shape;

    public Block(char id, String[] lines) throws IllegalArgumentException {
        this.id = id;

        int maxWidth = 0;
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, line.length());
        }

        this.shape = new boolean[lines.length][maxWidth];

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                shape[i][j] = (line.charAt(j) != ' ');
            }
        }
        findAnchorPoint();
        validateConnectivity();
    }

    public Block(char id, boolean[][] shape) {
        this.id = id;
        this.shape = shape;
        findAnchorPoint();
        validateConnectivity();
    }

    private void findAnchorPoint() {
        for (int j = 0; j < shape[0].length; j++) {
            for (int i = shape.length - 1; i >= 0; i--) {
                if (shape[i][j]) {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("No valid anchor point found");
    }

    private void validateConnectivity() {
        // Find first true cell as starting point
        int startI = -1, startJ = -1;
        outerLoop:
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    startI = i;
                    startJ = j;
                    break outerLoop;
                }
            }
        }

        if (startI == -1) {
            throw new IllegalArgumentException("Block " + id + " is empty");
        }

        boolean[][] visited = new boolean[shape.length][shape[0].length];

        floodFill(startI, startJ, visited);

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] && !visited[i][j]) {
                    throw new IllegalArgumentException("Block " + id + " has disconnected pieces");
                }
            }
        }
    }

    private void floodFill(int i, int j, boolean[][] visited) {
        if (i < 0 || i >= shape.length || j < 0 || j >= shape[0].length) {
            return;
        }

        if (!shape[i][j] || visited[i][j]) {
            return;
        }

        visited[i][j] = true;

        floodFill(i-1, j, visited);  // up
        floodFill(i, j+1, visited);  // right
        floodFill(i+1, j, visited);  // down
        floodFill(i, j-1, visited);  // left
    }

    public char getId() {
        return id;
    }

    public boolean[][] getShape() {
        return shape;
    }

    public Block rotate() {
        int height = shape.length;
        int width = shape[0].length;
        boolean[][] rotated = new boolean[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotated[j][height-1-i] = shape[i][j];
            }
        }
        return new Block(id, rotated);
    }

    public Block mirror() {
        int height = shape.length;
        int width = shape[0].length;
        boolean[][] mirrored = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mirrored[i][width-1-j] = shape[i][j];
            }
        }
        return new Block(id, mirrored);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                sb.append(shape[i][j] ? id : '.');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}