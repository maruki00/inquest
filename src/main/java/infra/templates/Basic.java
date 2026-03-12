package infra.templates;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import domain.contract.IEvent;
import domain.contract.Itemplete;
import io.github.cdimascio.dotenv.Dotenv;




public class Basic implements Itemplete{
    
    public String scan(List<IEvent> events, Dotenv env) {
        var mostFrequentIP = events.stream().
                                  collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                                  entrySet().
                                  stream().
                                  max(Map.Entry.comparingByValue());
        
        var mostFrequentHTTPMethod = events.stream().
                                  collect(Collectors.groupingBy(IEvent::message, Collectors.counting())).
                                  entrySet().
                                  stream().
                                  max(Map.Entry.comparingByValue());
       
        var mostFrequentURI = events.stream().
                                  collect(Collectors.groupingBy(IEvent::subject, Collectors.counting())).
                                  entrySet().
                                  stream().
                                  max(Map.Entry.comparingByValue());

        var mostFrequentRequestsInADay = events.stream().
                                  collect(Collectors.groupingBy(IEvent::timestamp, Collectors.counting())).
                                  entrySet().
                                  stream().
                                  max(Map.Entry.comparingByValue());

        StringBuilder builder = new StringBuilder("\033[0;34m");
        builder.append("==========[     Basic Template     ]==========\n");
        builder.append("----------------------------------------------\n");
        if(mostFrequentIP.isPresent()){
            builder.append(String.format("Top Attacker IP  : %-15s (%5d hits)\n", 
                                            mostFrequentIP.get().getKey(), 
                                            mostFrequentIP.get().getValue() ) 
            );
        }

        if(mostFrequentHTTPMethod.isPresent()){
            builder.append(String.format("Dominant Method  : %-15s (%5d hits)\n", 
                                            mostFrequentHTTPMethod.get().getKey(), 
                                            mostFrequentHTTPMethod.get().getValue())
            );
        }

        if(mostFrequentURI.isPresent()){
            builder.append(String.format("Hot Resource     : %-15s (%5d hits)\n", 
                                            mostFrequentURI.get().getKey(), 
                                            mostFrequentURI.get().getValue())
            );
        }

        if(mostFrequentRequestsInADay.isPresent()){
            builder.append(String.format("Peak Traffic Day : %-15s (%5d hits)\n", 
                                            mostFrequentRequestsInADay.get().getKey(), 
                                            mostFrequentRequestsInADay.get().getValue())
            );
        }

        builder.append("\033[0m");
        return builder.toString();
    }
}
