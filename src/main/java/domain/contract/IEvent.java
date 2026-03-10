package domain.contract;

import java.time.Instant;
import java.util.Map;

import app.Enums.EventType;
import app.Enums.Severity;

public interface IEvent {
    void id(Integer id);
    Integer id();
    Instant timestamp();
    String dataset();
    EventType type();
    Severity severity();
    String actor();
    String subject();
    String srcIp();
    String dstIp();
    Integer status();
    String message();
    Map<String, String> attrs();

    default boolean isFailure() {
        return status() != null && status() >= 400;
    }

    default boolean isInternal() {
        return srcIp() != null && (srcIp().startsWith("10.") || srcIp().startsWith("192.168.")|| srcIp().startsWith("127.0.0.1"));
    }
}
