package infra.templates;

import java.util.Comparator;
import java.util.List;

import domain.contract.IEvent;

public class basic {
    // TODO: implementation
    public String scan(List<IEvent> events) {
        List<IEvent> sortedEvents = events.stream().sorted(Comparator.comparing(IEvent::timestamp)).toList();
        if (!sortedEvents.isEmpty()) {
            IEvent first = sortedEvents.getFirst();
            IEvent last = sortedEvents.getLast();
            System.out.println(first.timestamp());
            System.out.println(last.timestamp());
        }
        return "";
    }
}
