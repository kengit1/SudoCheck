import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SudoWorkerManager {
    public static void runValidation(int mode , SudoSplitter splitter , List<Mistake> mistakes)
    {
        long startTime = System.nanoTime(); // to calculate the performance (Optional one)
        // getting the data
        int [][] rows = splitter.getRows();
        int[][] cols= splitter.getCols();
        int[][] blocks = splitter.getBlocks();
        // getting our executor-manager ready
        ExecutorService executor = null ;
        // choosing the strategy
        if(mode == 0)
        {
            // sequential approach , like we call "run" from thread0
            for(int i =0 ; i<9 ; i++) new ValidTask(rows[i],"ROW",i+1,mistakes ).run();
            for(int i=0; i<9 ; i++) new ValidTask(cols[i],"COL",i+1,mistakes).run();
            for(int i=0; i<9 ; i++) new ValidTask(blocks[i],"BOX",i+1,mistakes).run();
        }
        else
        {
            if(mode==3)
                executor= Executors.newFixedThreadPool(3) ;//only 3 workers
            else
                executor= Executors.newFixedThreadPool(27) ;//only 27 workers
            // either one of the modes , the executor will handle the system
            // now , we can create threads as we want , the synchronised block is there to protect the race condition , the executor will handle the interThreads communications :)
            for(int i =0 ; i<9 ; i++) executor.execute(new ValidTask(rows[i],"ROW",i+1,mistakes )) ;
            for(int i=0; i<9 ; i++) executor.execute(new ValidTask(cols[i],"COL",i+1,mistakes));
            for(int i=0; i<9 ; i++) executor.execute(new ValidTask(blocks[i],"BOX",i+1,mistakes));
            // shutting down the resources
            executor.shutdown();
            // waiting for the results
            try {
                // here , we made the executor asks his workers the duartion of allowed time
                // if the workers done the exceeded time limit , we will prompt this err
                // which indicates loop or deadlock
                if(!executor.awaitTermination(1, TimeUnit.MINUTES))
                    System.err.println("-*Time limit exceeded!");
                // why err ? it is an alert that the programmer notice either we have heavy load or we entered an infinite loop or worse, DeadLock!
                // although the program will continue , but by this indication we can now why our results is wrong
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                // allow us to track the tree of error in the console
                // indicates where the error happened!
            }
        }
        long endTime = System.nanoTime(); // notice it is calculated in nano
        System.out.println("->Validation Mode ["+mode+"] took "+(endTime-startTime)/1_000_000+"ms");
    }
}
