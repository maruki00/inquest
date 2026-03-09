package domain.contract;

import java.util.List;

public interface IParser {
    public static List<IEvent> Parse(String path, String del); 
}
