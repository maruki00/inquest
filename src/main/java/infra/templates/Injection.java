package infra.templates;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import domain.contract.IEvent;
import domain.contract.Itemplete;

public class Injection implements Itemplete{
    /**
     * xss
     * file inclusion
     * sqli
     * 
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
    public String scan(List<IEvent> events) {
        var suspIps = events.stream().
                                 // collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                                 // entrySet().
                                //  stream().
                                  filter(e -> Injection.Contains(e.subject())).
                                  collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        builder.append("\n   ========[ INJECTION Template ]==========\n");
        builder.append("----------------------------------------------\n");
        if (suspIps.isEmpty()) {
            builder.append("injection not detected");
        }else{
            for (var x : suspIps){
                builder.append(String.format("Potential Injecation : %-15s ", x.srcIp()));
            }
        }
        return builder.toString();
    }
   
}
