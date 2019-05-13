package net.intelie.challenges;

import java.util.ArrayList;
import java.util.List;

public class EventStoreIterator implements EventIterator {

    private final Object _filteredEventsSync = new Object();
    private final Object _eventsSync = new Object();

    private List<Event> _events;
    private List<Event> _filteredEvents;
    private int _currentIndex = -1;
    private boolean _lastMoveNext = false;

    EventStoreIterator(List<Event> events, String type, long startTime, long endTime) throws IllegalArgumentException {
        if (events == null) throw new IllegalArgumentException("Argument 'events' cannot be null.");
        if (type == null) throw new IllegalArgumentException("Argument 'type' cannot be null.");

        _events = events;
        _filteredEvents = filterEvents(events, type, startTime, endTime);
    }

    private List<Event> filterEvents(List<Event> events, String type, long startTime, long endTime) {
        List<Event> filteredEvents = new ArrayList<>(events.size());

        synchronized (_eventsSync) {
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
        }

        return filteredEvents;
    }

    @Override
    public boolean moveNext() {
        synchronized (_filteredEventsSync) {
            _lastMoveNext = _currentIndex < _filteredEvents.size() - 1;
            _currentIndex++;
        }

        return _lastMoveNext;
    }

    @Override
    public Event current() throws IllegalStateException {
        synchronized (_filteredEventsSync) {
            if (_currentIndex == -1)
                throw new IllegalStateException("moveNext() was never called.");
            if (!_lastMoveNext)
                throw new IllegalStateException("moveNext() returned false on last call.");

            return _filteredEvents.get(_currentIndex);
        }
    }

    @Override
    public void remove() throws IllegalStateException {
        synchronized (_filteredEventsSync) {
            if (_currentIndex == -1)
                throw new IllegalStateException("moveNext() was never called.");
            if (!_lastMoveNext)
                throw new IllegalStateException("moveNext() returned false on last call.");

            synchronized (_eventsSync) {
                _events.remove(_currentIndex);
            }
            _filteredEvents.remove(_currentIndex);
        }
    }

    @Override
    public void close() {
        _filteredEvents = null;
    }
}
