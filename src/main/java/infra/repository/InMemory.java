package infra.repository;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import domain.contract.IEvent;
import domain.contract.IRepository;
public class InMemory implements IRepository {
    private final List<IEvent> events = new CopyOnWriteArrayList<>();

    public void add(IEvent event) {
        event.id(events.size()+1);
        this.events.add(event);
    }

    public boolean remove(int id) {
        return this.events.removeIf(e -> e.id().equals(id));
    }

    public  List<IEvent> datasource(){
        return this.events;
    }

    public void clear() {
        this.events.clear();
    }

    public Optional<IEvent> searchById(int id) {
        return this.events.stream()
                .filter(e -> e.id().equals(id))
                .findFirst();
    }

    public List<IEvent> searchByIP(String query) {
        if (query == null) return List.of();

        return events.stream()
                .filter(e -> 
                    query.equalsIgnoreCase(e.srcIp()) || 
                    query.equalsIgnoreCase(e.dstIp())
                )
                .toList(); 
    }

    public List<IEvent> searchByStr(String query) {
        if (query == null) return List.of();
        return events.stream()
                .filter(e -> 
                    query.equalsIgnoreCase(e.srcIp()) || 
                    query.equalsIgnoreCase(e.dstIp()) ||
                    query.equalsIgnoreCase(e.subject()) ||
                    query.equalsIgnoreCase(e.message())
                )
                .toList(); 
    }
}