package net.intelie.challenges;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventStoreTest {
    private final String EVENT_TYPE = "Interview";
    private final long EVENT_TIMESTAMP = 1558098000L;
    private Event _event;

    @Before
    public void init(){
        _event = new Event(EVENT_TYPE, EVENT_TIMESTAMP);
    }

    /**
     * Adds a new Event to the Store
     * and checks if it was correctly added.
     */
    @Test
    public void Insert_Valid(){
        // Creating  new Store
        EventStore store = new MemoryEventStore();
        // Adding Event to Store
        store.insert(_event);

        // Getting Iterator for the Event's type
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);
        // Moving to first entry
        iterator.moveNext();
        // Getting current Event
        Event e = iterator.current();

        // Verifying if both Events are the same
        Assert.assertSame(_event, e);
    }

    /**
     * Adds Events to the Store
     * and then removes a specific Event type
     * to check if the Events were correctly removed.
     */
    @Test
    public void RemoveAll_Valid(){
        // Creating new Store
        EventStore store = new MemoryEventStore();

        // Adding 8 Events
        store.insert(new Event("Interview", 9876543210L));
        store.insert(new Event("Interview", 745456845L));
        store.insert(new Event("Party", 4541654546L));
        store.insert(new Event("Doctor", 455645645L));
        store.insert(new Event("Doctor", 545123155L));
        store.insert(new Event("Study", 1584845454L));
        store.insert(new Event("Study", 5481518548L));
        store.insert(new Event("Party", 5485541454L));

        // Removing  all Events of type "Party"
        store.removeAll("Party");

        // Getting an Iterator of removed type
        EventIterator iterator = store.query("Party", Long.MIN_VALUE, Long.MAX_VALUE);

        // moveNext() must return false as there is no Event of type "Party"
        Assert.assertFalse(iterator.moveNext());
    }

    /**
     * Adds Events to the Store
     * and makes a call to the query method
     * to check if the query is returning a value.
     */
    @Test
    public void Query_ReturnIterator_Valid(){
        // Creating new Store
        EventStore store = new MemoryEventStore();

        // Adding 8 Events
        store.insert(new Event("Interview", 9876543210L));
        store.insert(new Event("Interview", 745456845L));
        store.insert(new Event("Party", 4541654546L));
        store.insert(new Event("Doctor", 455645645L));
        store.insert(new Event("Doctor", 545123155L));
        store.insert(new Event("Study", 1584845454L));
        store.insert(new Event("Study", 5481518548L));
        store.insert(new Event("Party", 5485541454L));

        // Getting the Iterator
        EventIterator iterator = store.query("Party", Long.MIN_VALUE, Long.MAX_VALUE);

        // iterator must be an instantiated EventIterator
        Assert.assertNotNull(iterator);
    }

    /**
     * Adds an Event to the Store
     * and queries to another type
     */
    @Test
    public void Query_WrongTypeQuery_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query("Doctor", Long.MIN_VALUE, Long.MAX_VALUE);

        Assert.assertFalse(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries to another type
     */
    @Test
    public void Query_WrongTypeEvent_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(new Event("Doctor", 65456456456L));

        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        Assert.assertFalse(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with startTime greater
     * than Event's startTime
     */
    @Test
    public void Query_StartimeGreater_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, EVENT_TIMESTAMP + 1, Long.MAX_VALUE);

        Assert.assertFalse(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with startTime equals
     * to Event's startTime
     */
    @Test
    public void Query_StarttimeEquals_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, EVENT_TIMESTAMP, Long.MAX_VALUE);

        Assert.assertTrue(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with startTime smaller
     * than Event's startTime
     */
    @Test
    public void Query_StarttimeSmaller_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, EVENT_TIMESTAMP - 1, Long.MAX_VALUE);

        Assert.assertTrue(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with endTime greater
     * than Event's endTime
     */
    @Test
    public void Query_EndtimeGreater_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, EVENT_TIMESTAMP + 1);

        Assert.assertTrue(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with endTime equals
     * to Event's endTime
     */
    @Test
    public void Query_EndtimeEquals_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, EVENT_TIMESTAMP);

        Assert.assertFalse(iterator.moveNext());
    }

    /**
     * Adds an Event to the Store
     * and queries with endTime smaller
     * than Event's endTime
     */
    @Test
    public void Query_EndtimeSmaller_Valid(){
        EventStore store = new MemoryEventStore();
        store.insert(_event);

        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, EVENT_TIMESTAMP - 1);

        Assert.assertFalse(iterator.moveNext());
    }
}
