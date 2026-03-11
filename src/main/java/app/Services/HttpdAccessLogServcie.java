package app.Services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import app.Enums.Dataset;
import app.Enums.EventType;
import app.Enums.Severity;
import domain.contract.IEvent;
import domain.contract.IRepository;
import domain.contract.Itemplete;
import domain.contract.ParserService;
import domain.entity.Event;

public class HttpdAccessLogServcie implements ParserService {
    private static final String LOCAL_IP = "127.0.0.1";
    private static IRepository repository;

    public HttpdAccessLogServcie(IRepository re) {
        HttpdAccessLogServcie.repository = re;
    }

    // 10.223.157.186 - - [15/Jul/2009:15:50:36 -0700] "GET
    // /assets/img/dummy/secondary-news-3.jpg HTTP/1.1" 200 5604
    private static final Pattern APACHE_ACCESS_LOG_LINE_PATTERN = Pattern.compile(
            "^(\\S+) (\\S+) (\\S+) \\[(.*?)\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+|-)"
    // "^(\\d{1,3}(?:\\.\\d{1,3}){3})\\s-\\s-\\s\\[([^\\]]+)\\]\\s\"([A-Z]+)\\s([^\\s\"]+)\\sHTTP/\\d\\.\\d\"\\s(\\d{3})\\s(\\d+|-)$"
    );
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z",
            Locale.ENGLISH);

    
    public static IEvent parseLine(String line) {
        var matcher = APACHE_ACCESS_LOG_LINE_PATTERN.matcher(line);
        if (!matcher.find())
            return null;
        String rawActor = matcher.group(3);
        String actor = "-".equals(rawActor) ? "anonymous" : rawActor;
        Instant ts = OffsetDateTime.parse(matcher.group(4), DATE_FORMATTER).toInstant();
        return new Event(
                Integer.valueOf(0),
                ts,
                Dataset.ApacheAccess.toString(),// "apache-access",
                EventType.REQUEST,
                Severity.LOW,
                actor,
                matcher.group(6),
                matcher.group(1),
                LOCAL_IP,
                Integer.parseInt(matcher.group(8)),
                matcher.group(5) + " request",
                Map.of());
    }

    public static void processEvent(IEvent event) {
            HttpdAccessLogServcie.repository.add(event);
    }

    @Override
    public List<IEvent> Parse(String filePath) {
        Path path = Path.of(filePath);

        try (Stream<String> lines = Files.lines(path)) {

            lines.filter(line -> !line.isBlank())
                    .map(HttpdAccessLogServcie::parseLine)
                    .filter(event -> event != null)
                    .forEach(event -> {
                        processEvent(event);
                    });

        } catch (Exception e) {
            System.err.println("Fatal error reading log file: " + e.getMessage());
        }

        return null;
    }

    public void Analyse(Itemplete templete) {
        System.out.println(templete.scan(HttpdAccessLogServcie.repository.datasource()));
    }

}


