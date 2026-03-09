package infra.repository;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import domain.contract.IEvent;
import domain.contract.IRepository;

public class InMemory implements IRepository {
    private final List<IEvent> events = new CopyOnWriteArrayList<>();

    public void add(IEvent event) {
        this.events.add(event);
    }

    public boolean remove(int id) {
        return this.events.removeIf(e -> e.GetID() == id);
    }

    public void clear() {
        this.events.clear();
    }

    public Optional<IEvent> searchById(int id) {
        return this.events.stream()
                .filter(e -> e.GetID() == id)
                .findFirst();
    }

    public List<IEvent> searchByStr(String query) {
        if (query == null) return List.of();

        return events.stream()
                .filter(e -> query.equalsIgnoreCase(e.GetLabel()))
                .sorted(Comparator.comparing(IEvent::GetLabel))
                .toList(); 
    }
}
