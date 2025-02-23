package model;

public class Block {
    private final char id;
    private final boolean[][] shape;

    public Block(char id, String[] lines) {
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
    }

    public Block(char id, boolean[][] shape) {
        this.id = id;
        this.shape = shape;
        findAnchorPoint();
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