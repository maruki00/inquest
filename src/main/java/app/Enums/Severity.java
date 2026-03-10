package app.Enums;

public enum Severity {
    INFO("Information"),
    LOW("Low Priority"),
    MEDIUM("Medium Priority"),
    HIGH("High Priority"),
    CRITICAL("Critical Alert");

    private final String severity;

    Severity(String severity) {
        this.severity = severity;
    }

    public String getVal() { 
        return severity; 
    }

    @Override
    public String toString() {
        return severity;
    }
//var severity = switch (status == null ? 0 : status) {                
// case 500, 501, 502, 503, 504 -> Severity.HIGH;                
// case 400, 401, 403, 404, 429 -> Severity.MEDIUM;                
// default -> Severity.LOW;            
//};

    public Severity fromCode(Integer status) {
        return switch (status == null ? 0 : status) {                
            case 500, 501, 502, 503, 504 -> Severity.HIGH;                
            case 400, 401, 403, 404, 429 -> Severity.MEDIUM;                
            default -> Severity.LOW;            
        };

    } 
}
