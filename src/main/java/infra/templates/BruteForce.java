package infra.templates;

import java.util.List;
import java.util.stream.Collectors;
import app.Enums.HttpStatus;
import domain.contract.IEvent;
import domain.contract.Itemplete;
import io.github.cdimascio.dotenv.Dotenv;

public class BruteForce implements Itemplete{

    private static boolean IN(Integer status){
        return  status == HttpStatus.BAD_REQUEST.getCode() || 
                status == HttpStatus.NOT_FOUND.getCode() ||
                status == HttpStatus.FORBIDDEN.getCode() ||
                status == HttpStatus.UNAUTHORIZED.getCode() ||
                status == HttpStatus.NOT_IMPLEMENTED.getCode() ||
                status == HttpStatus.INTERNAL_SERVER_ERROR.getCode() ;
    }
    public String scan(List<IEvent> events, Dotenv env) {
        var suspIps = events.
                        stream().
                        filter(e -> BruteForce.IN(e.status())).
                        collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                        entrySet().
                        stream().
                        filter(e -> e.getValue() >=  Integer.parseInt(env.get("MAX_BRUTEFORCE_ATTEMPTS"))).
                        collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("\033[36m");
        builder.append("\n   ========[ Brute Force Template ]==========\n");
        builder.append("----------------------------------------------\n");
        if (suspIps.isEmpty()) {
            builder.append("No suspicious activity detected.");
        }else{
            for (var x : suspIps){
                builder.append(String.format("Potential Brute Force : %-15s (%5d hits)\n", x.getKey(), x.getValue()));
            }
        }
        builder.append("\033[0m");
        return builder.toString();
    }
}
