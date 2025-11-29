import java.util.*;

public class SudoDupeChecker {

    public static List<Map<String, Object>> checkForDupes(int[] arr) {
        List<Map<String, Object>> duplicates = new ArrayList<>();
        boolean[] processed = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (processed[i]) continue;
            List<Integer> indices = new ArrayList<>();
            indices.add(i + 1);
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == arr[i]) {
                    indices.add(j + 1);
                    processed[j] = true;
                }
            }
            if (indices.size() > 1) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("value", arr[i]);
                entry.put("indices", indices);
                duplicates.add(entry);
            }
        }
        return duplicates;
    }
}
