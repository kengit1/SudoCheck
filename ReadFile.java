import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFile {
    private static int[][] readBoard(String csvPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("C:\\Users\\Omarh\\Desktop\\SudoCheck\\sudoku_valid.csv"));
        int[][] board = new int[9][9];
        int r = 0;
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s*,\\s*|\\s+");
            if (parts.length < 9) {
                throw new IllegalArgumentException("Row " + (r+1) + " does not have 9 values: '" + line + "'");
            }
            for (int c = 0; c < 9; c++) {
                try {
                    board[r][c] = Integer.parseInt(parts[c]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Bad number at row " + (r+1) + " col " + (c+1) + ": '" + parts[c] + "'");
                }
                if (board[r][c] < 1 || board[r][c] > 9) {
                    throw new IllegalArgumentException("Value out of range (1-9) at row " + (r+1) + " col " + (c+1) + ". Found: " + board[r][c]);
                }
            }
            r++;
            if (r >= 9) break;
        }
        if (r<9) throw new IllegalArgumentException("Board has fewer than 9 non-empty rows (" + r + ")");
        return board;
    }
    public static void printBoard(int[][] board) {
        System.out.println("--- Sudoku Board ---");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                if ((j + 1) % 3 == 0 && j < 8) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i < 8) {
                System.out.println("--------------------");
            }
        }
        System.out.println("--------------------");
    }
    public static void main(String[] args) {
        String filePath = "sudoku.csv";
        try {
            int[][] board = readBoard(filePath);
            printBoard(board);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Make sure the file '" + filePath + "' exists and is accessible.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error in file format: " + e.getMessage());
        }
    }
}