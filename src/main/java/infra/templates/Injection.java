package infra.templates;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import domain.contract.IEvent;
import domain.contract.Itemplete;
import io.github.cdimascio.dotenv.Dotenv;

public class Injection implements Itemplete{
    /**
     * xss
     * file inclusion
     * sqli
     * ....
     */

    static Map<String,String> basicPayloads = Map.ofEntries(
        Map.entry("' or '1'='1' --","SQL INJECTION"),
        Map.entry("' or '1'='1' #","SQL INJECTION"),
        Map.entry("| id","COMMAND INJECTION"),
        Map.entry("&& id","COMMAND INJECTION"),
        Map.entry("& id","COMMAND INJECTION"),
        Map.entry("../../etc/passwd","FILE INCLUSION"),
        Map.entry("/etc/passwd", "FILE INCLUSION"),
        Map.entry("\r","INCLUSION"),
        Map.entry("\t","INCLUSION"),
        Map.entry("%00","INCLUSION"),
        Map.entry("<script>alert(1)</script>","XSS"),
        Map.entry("cmd=id","COMMAND INJECTION")
    );

    private static Boolean Contains(String line) {
        for (Map.Entry<String, String> entry : basicPayloads.entrySet()){
            if (line.contains(entry.getKey())){
                return true;
            }
        }
        return false;
    }
    private static String getMatchedAttack(IEvent e){
        for (Map.Entry<String, String> payload : basicPayloads.entrySet()) {
        if (e.subject().contains(payload.getKey())) {
            return  String.format("Potential Attack : %-15s | Type: %-10s | Payload: %-20s\n", e.srcIp(), payload.getValue(), payload.getKey());   //entry.getValue(); 
        }
    }
        return "Clean";
    }
    public String scan(List<IEvent> events, Dotenv env) {
        var suspIps = events.stream().
                                 // collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                                 // entrySet().
                                //  stream().
                                  filter(e -> Injection.Contains(e.subject())).
                                  map(e -> Injection.getMatchedAttack(e)).
                                  collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("\033[33m");
        builder.append("\n   ========[ Injection Template ]==========\n");
        builder.append("----------------------------------------------\n");
        if (suspIps.isEmpty()) {
            builder.append("No suspicious activity detected.\n");
        }else{
            for (var x : suspIps){
                builder.append(x);
            }
        }
        builder.append("\033[0m");
        return builder.toString();
    }
   
}
