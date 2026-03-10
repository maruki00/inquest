package domain.entity;

import java.time.Instant;
import java.util.Map;
import app.Enums.EventType;
import app.Enums.Severity;
import domain.contract.IEvent;

public class Event implements IEvent {
    Integer id;
    Instant timestamp;
    String dataset;
    EventType type;
    Severity severity;
    String actor;
    String subject;
    String srcIp;
    String dstIp;
    Integer status;
    String message;
    Map<String, String> attrs;

    public Event(Integer id, Instant timestamp, String dataset, EventType type,
            Severity severity, String actor, String subject, String srcIp, String dstIp,
            Integer status, String message, Map<String, String> attrs) {
        this.id = id;
        this.timestamp = timestamp;
        this.dataset = dataset;
        this.type = type;
        this.severity = severity;
        this.actor = actor;
        this.subject = subject;
        this.srcIp = srcIp;
        this.dstIp = dstIp;
        this.status = status;
        this.message = message;
        this.attrs = attrs;
    }

    public void id(Integer id){
        this.id = id;
    }
    
    @Override
    public Integer id() {
        return id;
    }

    @Override
    public Instant timestamp() {
        return timestamp;
    }

    @Override
    public String dataset() {
        return dataset;
    }

    @Override
    public EventType type() {
        return type;
    }

    @Override
    public Severity severity() {
        return severity;
    }

    @Override
    public String actor() {
        return actor;
    }

    @Override
    public String subject() {
        return subject;
    }

    @Override
    public String srcIp() {
        return srcIp;
    }

    @Override
    public String dstIp() {
        return dstIp;
    }

    @Override
    public Integer status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Map<String, String> attrs() {
        return attrs;
    }
}