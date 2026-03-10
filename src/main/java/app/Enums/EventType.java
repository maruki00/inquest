package app.Enums;

public enum EventType {
    LOGIN("AUTH_LOGIN"),
    REQUEST("HTTP_REQUEST"),
    SYSTEM("SYS_EVENT");

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
