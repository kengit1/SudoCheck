import java.io.IOException;
import java.util.*;

public class SudoMain {
    public static void main(String[] args) {
        // initialize our new parser
        ArgParser parser = new ArgParser();
        parser.parse(args);// the main point of parsing is through the main point of the program
        if(!parser.isTrueArg())
        {
            System.err.println("-*Error: Could not start the program for missing values");
            parser.printHelpMenu();
            return;
        }
        if(parser.isHelpRequested())
        {
            parser.printHelpMenu();
            return; // done the purpose of the program
        }

        // get the parameters

        int mode = parser.getMode();
        String filePath = parser.getFilePath();
        System.out.println("-> Starting Sudoku Checker...");
        System.out.println("-> File: " + filePath);
        System.out.println("-> Mode: " + mode);

        try {
            // preparing our controller with its components
            List<String> lines = SudoLoader.readFile(filePath) ;
            int [][] board =  SudoLoader.parseBoard(lines);

            SudoSplitter splitter = new SudoSplitter(board);
            List<Mistake> mistakes = Collections.synchronizedList(new ArrayList<>()) ;
            SudoWorkerManager.runValidation(mode,splitter,mistakes); // added our wrapper to add the lock for our collections when dealing with threads
            // checking logic
            if(mistakes.isEmpty())
                System.out.println("->The Sudoku Board is VALID!!");
            else
            {
                System.out.println("->Found "+mistakes.size()+" mistakes in your board");
                printMistakes(mistakes);
            }
        } catch (IOException e) {
            System.err.println("-*Error: could not find the file of '"+filePath+"'");
        }
    }
    public static void printMistakes(List<Mistake> mistakes)
    {
        System.out.println("=========================================================");
        for(Mistake m:mistakes){
            for(Map<String, Object> dup : m.getinfo()){
                String str = m.getType()+" "+m.getNumber()+", #"+dup.get("value")+", "+dup.get("indices");
                System.out.println(str);
            }
        }
    }
}
