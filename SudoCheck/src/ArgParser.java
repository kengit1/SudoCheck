public class ArgParser {
    // default modes
    private int mode = 0 ;
    private String filePath = "sudoku_invalid.csv"; //just for our testing
    private boolean helpRequested = false ;

    public void parse(String[] args)
    {
        // our way to interpret the argument is throught basic iterating
        for(int i =0 ; i< args.length;i++)
        {
            switch (args[i])
            {
                case "-m" :
                case"--mode" :
                    // checking if there is any value after this command
                    if(i+1 < args.length)
                    {
                        try {
                            this.mode = Integer.parseInt(args[i+1]);
                            i++ ; // for the next iterate
                        }catch (NumberFormatException e)
                        {
                            System.err.println("-*Error: Mode must be 0,3 or 27 only");
                        }
                    }
                    else
                        System.err.println("-*Error: missing value of --mode");
                    break;

                case "-f":
                case "--file":
                    if(i+1 < args.length) // to check we are not out of boundaries
                    {
                        this.filePath=args[i+1];
                        i++ ;
                    }
                    else
                        System.err.println("-*Error: Missing value of --file");
                    break;

                case "-h":
                case "--help":
                    this.helpRequested=true;
                    break;
            }
        } // the end of interpreting the argument
    }
    public int getMode()
    {
        return mode;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isHelpRequested() {
        return helpRequested;
    }
    // an optional but nice add
    // good for learning the basics of interpreting arguments
    // especially for RealTimeAI
    public void printHelpMenu()
    {
        System.out.println("Usage: java SudokuApp [options]");
        System.out.println("Options:");
        System.out.println("  -f, --file <path>    Path to the CSV file (default: sudoku.csv)");
        System.out.println("  -m, --mode <0|3|27>  Threading mode:");
        System.out.println("                         0  = Single Thread (Sequential)");
        System.out.println("                         3  = 3 Concurrent Threads");
        System.out.println("                         27 = Full Parallelism");
        System.out.println("  -h, --help           Show this help message");
    }
}
