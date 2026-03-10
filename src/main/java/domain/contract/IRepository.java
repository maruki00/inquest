package domain.contract;

import java.util.List;
import java.util.Optional;

public interface IRepository {
public void add(IEvent event) ;
    public boolean remove(int id) ;
    public void clear() ;
    public Optional<IEvent> searchById(int id) ;
    public List<IEvent> searchByStr(String query);
} 