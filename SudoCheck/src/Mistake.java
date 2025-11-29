import java.util.List;
import java.util.Map;

public class Mistake {
    private final String type;
    private final int number;
    private final  List<Map<String, Object>> info;

    public Mistake(String type,int number, List<Map<String, Object>> info){
        this.type = type;
        this.number = number;
        this.info = info;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public  List<Map<String, Object>> getinfo() {
        return info;
    }

}
