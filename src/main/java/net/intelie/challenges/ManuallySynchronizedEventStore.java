package net.intelie.challenges;

import java.util.ArrayList;

/*
    This class is a manually implemented synchronized ArrayList.
    This is not the best implementation, as the locks are not optimized
    for best performance and the main method's test was not finished with this.
 */
public class ManuallySynchronizedEventStore implements EventStore {

    private final Object _eventsSync = new Object();
    private ArrayList<Event> _events;

    public ManuallySynchronizedEventStore() {
        _events = new ArrayList<>(2000);
    }

    @Override
    public void insert(Event event) {
        synchronized (_eventsSync) {
            _events.add(event);
        }
    }

    @Override
    public void removeAll(String type) {
        synchronized (_eventsSync) {
            _events.removeIf(e -> e.type().equals(type));
        }
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        return new EventStoreIterator(_events, type, startTime, endTime);
    }
}
