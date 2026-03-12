package infra.templates;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.contract.IEvent;
import domain.contract.Itemplete;
import io.github.cdimascio.dotenv.Dotenv;

public class Injection implements Itemplete {
    /**
     * xss
     * file inclusion
     * sqli
     * ....
     */

    static Map<String, String> basicPayloads = Map.ofEntries(
            // Basic Sqli Payloads
            Map.entry("' or '1'='1' --", "SQL INJECTION"),
            Map.entry("' or '1'='1' #", "SQL INJECTION"),
            // Basic Command Injection payloads
            Map.entry("=id", "COMMAND INJECTION"),
            Map.entry("=pwd", "COMMAND INJECTION"),
            Map.entry("=uname", "COMMAND INJECTION"),
            Map.entry("=nc", "COMMAND INJECTION"),
            Map.entry("=ls", "COMMAND INJECTION"),
            Map.entry("=bash", "COMMAND INJECTION"),
            Map.entry("=sh", "COMMAND INJECTION"),
            // Basic Path LFI Payloads
            Map.entry("/etc/passwd", "FILE INCLUSION"),
            Map.entry("/etc/hosts", "FILE INCLUSION"),
            // Basic Control Injection
            Map.entry("\r", "CONTROL INCLUSION"),
            Map.entry("\t", "CONTROL INCLUSION"),
            Map.entry("%00", "CONTROL INCLUSION"),
            // Basic XSS
            Map.entry("<script>", "XSS"),
            Map.entry("<img", "XSS"),
            Map.entry("<svg", "XSS")

    // Map.entry("cmd=id","COMMAND INJECTION")
    );

    private static Boolean Contains(String line) {
        for (Map.Entry<String, String> entry : basicPayloads.entrySet()) {
            if (line.contains(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    private static String getMatchedAttack(IEvent e) {
        for (Map.Entry<String, String> payload : basicPayloads.entrySet()) {
            if (e.subject().contains(payload.getKey())) {
                return String.format("Potential Attack: %-15s | Type: %-20s | Payload: %-20s\n", e.srcIp(),
                        payload.getValue(), e.subject()); // entry.getValue();
            }
        }
        return "Clean";
    }

    public String scan(List<IEvent> events, Dotenv env) {
        var suspIps = events.stream().
                            // collect(Collectors.groupingBy(IEvent::srcIp, Collectors.counting())).
                            // entrySet().
                            // stream().
                            filter(e -> Injection.Contains(e.subject())).
                            map(e -> Injection.getMatchedAttack(e)).
                            collect(Collectors.toList());

        StringBuilder builder = new StringBuilder("\033[33m");
        builder.append("\n==========[   Injection Template   ]==========\n");
        builder.append("----------------------------------------------\n");
        if (suspIps.isEmpty()) {
            builder.append("No suspicious activity detected.\n");
        } else {
            for (var x : suspIps) {
                builder.append(x);
            }
        }
        builder.append("\033[0m");
        return builder.toString();
    }

}
=