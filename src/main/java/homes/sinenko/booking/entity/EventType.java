package homes.sinenko.booking.entity;

public enum EventType {
    CREATED,
    UPDATED,
    DELETED,
    BOOKED,
    CANCELLED,
    PAID;

    public static EventType set(String value) {
        return EventType.valueOf(value.toUpperCase());
    }

    public String getName() {
        return name();
    }
}
