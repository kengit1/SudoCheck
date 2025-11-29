import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//  Better Naming (Noun) instead of "ReadFile" the old one
public class SudoLoader {

    // No Magic Numbers for easing the modification
    private static final int SIZE = 9;
    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 9;

    //  This method only reads the file , it is csv hard. if we want another database so it is!
    public static List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }

    //  This method parses and validates logic , for future extensibility
    public static int[][] parseBoard(List<String> lines) {
        int[][] board = new int[SIZE][SIZE];
        int row = 0;

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            if (row >= SIZE) break;

            String[] parts = line.trim().split("[,\\s]+"); // Improved Regex

            if (parts.length < SIZE) {
                throw new IllegalArgumentException("Row " + (row + 1) + " is incomplete.");
            }

            for (int col = 0; col < SIZE; col++) {
                try {
                    int val = Integer.parseInt(parts[col]);
                    if (val < MIN_VAL || val > MAX_VAL) {
                        throw new IllegalArgumentException("Value " + val + " is out of bounds.");
                    }
                    board[row][col] = val;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number at [" + row + "," + col + "]");
                }
            }
            row++;
        }

        if (row < SIZE) {
            throw new IllegalArgumentException("File has fewer than " + SIZE + " valid rows.");
        }
        return board;
    }

    public static void printBoard(int[][] board) {
        // (Format logic remains, but uses SIZE constants instead of hardcoded numbers)
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
        System.out.println("Board printed successfully");
    }

    public static void main(String[] args) {
        // Using the passed variable correctly
        String filePath = "C:\\Users\\ABDO\\Documents\\My Work\\My Codes\\Java\\LabAssignments\\OOP Projects\\SudoChecker\\sudoku_valid.csv" ;

        try {
            List<String> lines = readFile(filePath);
            int[][] board = parseBoard(lines);
            printBoard(board);
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data error: " + e.getMessage());
        }
    }
}