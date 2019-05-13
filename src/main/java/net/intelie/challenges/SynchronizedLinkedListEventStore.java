package net.intelie.challenges;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
    Collections.synchronizedList(collection) creates a synchronized scenario
    for the wrapped collection. In my case, I used an LinkedList<> as the base collection
    because of how it can be fast on adding and removing objects.
 */
public class SynchronizedLinkedListEventStore implements EventStore {

    private List<Event> _events;

    public SynchronizedLinkedListEventStore() {
        _events = Collections.synchronizedList(new LinkedList<>());
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
