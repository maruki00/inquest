package infra.templates;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import domain.contract.IEvent;
import domain.contract.Itemplete;
/**
 done:* Line 1: From which IP do requests occur most frequently? (Identify which other property occurs most frequently and least frequently in your specific log format).
 done:* Line 2: Which HTTP method is called most often? (Which command or set of commands appears most frequently in the logs?)
 done:* Line 3: Which URI is requested most frequently?
 done* Line 4: On which day were there the most requests?
 done* Line 5: What time period do the logs cover?
 * Line 6: Suspicious requests (multiple identical requests in a row, multiple requests that received an error status code, etc.—feel free to use your imagination here).
 * Line 7: Track any number of events of the same type/code from a single key within a short time window.
*/
public class Basic implements Itemplete{
    
    public String scan(List<IEvent> events) {
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
        builder.append("\n   ========[ Basic Template ]==========\n");
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
