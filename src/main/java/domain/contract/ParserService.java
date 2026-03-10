package domain.contract;

import java.util.List;

public interface ParserService {
    public List<IEvent> Parse(String path);
}

