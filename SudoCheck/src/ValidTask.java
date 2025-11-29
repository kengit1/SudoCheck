import java.util.List;
import java.util.Map;

public class ValidTask implements Runnable{
    private final int[] data ; //our dataStructure we will work on
    private final String type ; // row , col...
    private final int id ;
    private final List<Mistake> sharedList ;
    public ValidTask(int[] data, String type, int id, List<Mistake> sharedList) {
        this.data = data;
        this.type = type;
        this.id = id;
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        // the checking logic
        List<Map<String, Object>> dupes = SudoDupeChecker.checkForDupes(data) ;
        if(!dupes.isEmpty())
        {
            // create our Mistake object
            Mistake mistake = new Mistake(type , id , dupes);

            //the critical part , protecting the sharedList with our sync block
            synchronized (sharedList)
            {sharedList.add(mistake) ;}
        }
    }
}
