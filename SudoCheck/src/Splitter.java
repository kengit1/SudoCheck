import java.util.Arrays;

public class Splitter {
    private final int[][] grid;
    private final int[][] rows = new int[9][9];
    private final int[][] cols = new int[9][9];
    private final int[][] blocks = new int[9][9];

    public Splitter(int[][] grid) {
        if (grid.length != 9 || grid[0].length != 9 ) {
            throw new IllegalArgumentException("Grid must be 9x9");
        }
        this.grid = grid;
        split();
    }
    private void split() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                rows[r][c] = grid[r][c];
                cols[c][r] = grid[r][c];
            }
        }
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                int blockIndex = blockRow * 3 + blockCol;
                int idx = 0;
                for (int r = blockRow * 3; r < blockRow * 3 + 3; r++) {
                    for (int c = blockCol * 3; c < blockCol * 3 + 3; c++) {
                        blocks[blockIndex][idx++] = grid[r][c];
                    }
                }
            }
        }
    }
    public int[][] getRows() {
        return rows;
    }

    public int[][] getCols() {
        return cols;
    }

    public int[][] getBlocks() {
        return blocks;
    }
}

