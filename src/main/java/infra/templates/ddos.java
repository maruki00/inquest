package infra.templates;
import java.util.Comparator;
import java.util.List;
import domain.contract.IEvent;
import domain.contract.Itemplete;
public class ddos implements Itemplete {
    private static final Integer MAX_REQUESTS_PER_MINUTE = 1000;
    // TODO: implementation
    public String scan(List<IEvent> events) {
        List<IEvent> sortedEvents = events.stream().sorted(Comparator.comparing(IEvent::timestamp)).toList();
        if (!sortedEvents.isEmpty()) {
            IEvent first = sortedEvents.getFirst(); 
            IEvent last = sortedEvents.getLast(); 
            System.out.println(first.timestamp());
            System.out.println(last.timestamp());
            System.out.println(MAX_REQUESTS_PER_MINUTE);
        }
        return "";
    } 
}
