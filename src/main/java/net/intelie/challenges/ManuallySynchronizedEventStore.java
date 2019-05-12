package net.intelie.challenges;

import java.util.ArrayList;

public class ManuallySynchronizedEventStore implements EventStore {

    private ArrayList<Event> _events;

    public ManuallySynchronizedEventStore()
    {
        _events = new ArrayList<>(2000);
    }

    @Override
    public synchronized void insert(Event event) {
        _events.add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        _events.removeIf(e -> e.type().equals(type));
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        return new EventStoreIterator(_events, type, startTime, endTime);
    }
}
