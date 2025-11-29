import java.util.*;

public class MainChecker {
    public static void main(String[] args) {

        int[][] solution = new int[][]{ //3shan test bas l7ad man7ot el code eli by7wl mn file l array.
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1}
        };
        boolean valid = true;
        Splitter s = new Splitter(solution);
        List<Mistake> mistakes = new ArrayList<>();
        int[][] rows = s.getRows();
        int[][] cols = s.getCols();
        int[][] blocks = s.getBlocks();

        for (int i = 0; i < 9; i++) {
            List<Map<String, Object>> dupes = DuplicateChecker.checkForDupes(rows[i]);
            if(!dupes.isEmpty()){
                valid = false;
                Mistake m = new Mistake("ROW",(i+1),dupes);
                mistakes.add(m);
            }
        }

        for (int i = 0; i < 9; i++) {
            List<Map<String, Object>> dupes = DuplicateChecker.checkForDupes(cols[i]);
            if(!dupes.isEmpty()){
                valid = false;
                Mistake m = new Mistake("COL",(i+1),dupes);
                mistakes.add(m);
            }
        }

        for (int i = 0; i < 9; i++) {
            List<Map<String, Object>> dupes = DuplicateChecker.checkForDupes(blocks[i]);
            if(!dupes.isEmpty()){
                valid = false;
                Mistake m = new Mistake("BOX",(i+1),dupes);
                mistakes.add(m);
            }
        }

        if(valid) System.out.println("The given solution is VALID");
        else{
            System.out.println("The given solution is INVALID");
            System.out.println("=========================================================");
            for(Mistake m:mistakes){
                for(Map<String, Object> dup : m.getinfo()){
                    String str = m.getType()+" "+m.getNumber()+", #"+dup.get("value")+", "+dup.get("indices");
                    System.out.println(str);
                }
            }
        }
    }
}
