package net.intelie.challenges;

import java.util.ArrayList;
import java.util.List;

public class EventStoreIterator implements EventIterator {

    private List<Event> _events;
    private List<Event> _filteredEvents;
    private int currentIndex = -1;
    private boolean lastMoveNext = false;

    EventStoreIterator(List<Event> events, String type, long startTime, long endTime) {
        _events = events;
        _filteredEvents = filterEvents(events, type, startTime, endTime);
    }

    private List<Event> filterEvents(List<Event> events, String type, long startTime, long endTime)
    {
        List<Event> filteredEvents = new ArrayList<>(events.size());
        events.forEach(
                e ->
                {
                    if (
                            e.type().equals(type)
                                    && e.timestamp() >= startTime
                                    && e.timestamp() < endTime
                    )
                        filteredEvents.add(e);
                }
        );

        return filteredEvents;
    }

    @Override
    public synchronized boolean moveNext() {
        if (_filteredEvents.size() == currentIndex + 1) {
            return lastMoveNext = false;
        } else {
            currentIndex++;
            return lastMoveNext = true;
        }
    }

    @Override
    public Event current() throws IllegalStateException {
        if (currentIndex == -1)
            throw new IllegalStateException("moveNext() was never called.");
        if (!lastMoveNext)
            throw new IllegalStateException("moveNext() returned false on last call.");

        return _filteredEvents.get(currentIndex);
    }

    @Override
    public synchronized void remove() throws IllegalStateException {
        if (currentIndex == -1)
            throw new IllegalStateException("moveNext() was never called.");
        if (!lastMoveNext)
            throw new IllegalStateException("moveNext() returned false on last call.");

        _events.remove(currentIndex);
        _filteredEvents.remove(currentIndex);
    }

    @Override
    public void close() {
        _filteredEvents = null;
    }
}
