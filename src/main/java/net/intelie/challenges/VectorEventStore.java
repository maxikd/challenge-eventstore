package net.intelie.challenges;

import java.util.Vector;

/*
    Vector<> is a collection that is already implemented as thread-safe.
    Although it isn't the best in performance, I chose it to implement my solution
    because it is naturally synchronized and I can rely on it.
 */
public class VectorEventStore implements EventStore {

    private Vector<Event> _events;

    public VectorEventStore()
    {
        _events = new Vector<>();
    }

    @Override
    public void insert(Event event) {
        _events.add(event);
    }

    @Override
    public void removeAll(String type) {
        _events.removeIf(e -> e.type().equals(type));
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        return new EventStoreIterator(_events, type, startTime, endTime);
    }
}
