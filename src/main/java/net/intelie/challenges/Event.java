package net.intelie.challenges;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event {
    private final String type;
    private final long timestamp;

    public Event(String type, long timestamp) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("Argument 'type' cannot be null.");
        if (type.equals("")) throw new IllegalArgumentException("Argument 'type' cannot be empty.");

        this.type = type;
        this.timestamp = timestamp;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }
}
