package infra.templates;
import java.util.List;
import java.util.stream.Collectors;
import domain.contract.IEvent;
import domain.contract.Itemplete;

public class DDoS implements Itemplete {
    private static final Integer MAX_ALLOWED_REQUESTS = 1000;

    public String scan(List<IEvent> events) {

        var suspIps = events.stream().
                                  collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                                  entrySet().
                                  stream().
                                  filter(e -> e.getValue() > MAX_ALLOWED_REQUESTS).
                                  collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("\033[31m");
        builder.append("   ========[ DDoS Template ]============\n");
        builder.append("------------------------------------------\n");
        if (suspIps.isEmpty()) {
            builder.append("No suspicious activity detected.");
        }else{
            for (var x : suspIps){
                builder.append(String.format("Potential DDoS : %-15s | Times : %-5d%n", x.getKey(), x.getValue()));
            }
        }
        builder.append("\033[0m");
        return builder.toString();
    }
}
