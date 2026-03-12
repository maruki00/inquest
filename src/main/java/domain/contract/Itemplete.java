package domain.contract;

import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;

public interface Itemplete {
    public String scan(List<IEvent> events, Dotenv env);
}
