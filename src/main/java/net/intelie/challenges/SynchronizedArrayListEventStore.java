package net.intelie.challenges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Collections.synchronizedList(collection) creates a synchronized scenario
    for the wrapped collection. In my case, I used an ArrayList<> as the base collection
    because of how it expands its size when needed.
 */
public class SynchronizedArrayListEventStore implements EventStore {

    private List<Event> _events;

    public SynchronizedArrayListEventStore()
    {
        _events = Collections.synchronizedList(new ArrayList<>(2000));
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
