package net.intelie.challenges;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EventIteratorTest {
    private final String EVENT_TYPE = "Party";
    private final long EVENT_TIMESTAMP = 1558227600L;
    private Event _event;

    @Rule
    public ExpectedException moveNextWasNotCalled = ExpectedException.none();
    @Rule
    public ExpectedException moveNextReturnedFalseOnLastCall = ExpectedException.none();

    @Before
    public void init(){
        _event = new Event(EVENT_TYPE, EVENT_TIMESTAMP);
    }

    /**
     * Gets an EventIterator and tries
     * to call current() without calling moveNext()
     */
    @Test
    public void Current_MoveNextNotCalled_Invalid(){
        // Configuring exception rule
        moveNextWasNotCalled.expect(IllegalStateException.class);
        moveNextWasNotCalled.expectMessage("moveNext() was never called.");

        // Creating new Store
        EventStore store = new MemoryEventStore();
        // Adding an Event to the Store
        store.insert(_event);

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Throwing IllegalStateException
        // because I did not call moveNext() before current()
        iterator.current();
    }

    /**
     * Gets an EventIterator and tries
     * to call current() after moveNext() returned false
     */
    @Test
    public void Current_MoveNextReturnedFalse_Invalid(){
        // Configuring exception rule
        moveNextReturnedFalseOnLastCall.expect(IllegalStateException.class);
        moveNextReturnedFalseOnLastCall.expectMessage("moveNext() returned false on last call.");

        // Creating new Store
        EventStore store = new MemoryEventStore();

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Moves Iterator to the first record
        iterator.moveNext();

        // Throwing IllegalStateException
        // because moveNext() returned false
        iterator.current();
    }

    /**
     * Gets an EventIterator and tries
     * to call remove() without calling moveNext()
     */
    @Test
    public void Remove_MoveNextNotCalled_Invalid(){
        // Configuring exception rule
        moveNextWasNotCalled.expect(IllegalStateException.class);
        moveNextWasNotCalled.expectMessage("moveNext() was never called.");

        // Creating new Store
        EventStore store = new MemoryEventStore();
        // Adding an Event to the Store
        store.insert(_event);

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Throwing IllegalStateException
        // because I did not call moveNext() before remove()
        iterator.remove();
    }

    /**
     * Gets an EventIterator and tries
     * to call remove() after moveNext() returned false
     */
    @Test
    public void Remove_MoveNextReturnedFalse_Invalid(){
        // Configuring exception rule
        moveNextReturnedFalseOnLastCall.expect(IllegalStateException.class);
        moveNextReturnedFalseOnLastCall.expectMessage("moveNext() returned false on last call.");

        // Creating new Store
        EventStore store = new MemoryEventStore();

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Moving Iterator to the first record
        iterator.moveNext();

        // Throwing IllegalStateException
        // because moveNext() returned false
        iterator.remove();
    }

    /**
     * Adds an Event to the Store,
     * queries the Store and checks if current()
     * if the return Event is the current Event
     */
    @Test
    public void Current_Valid(){
        // Creating a new Store
        EventStore store = new MemoryEventStore();
        // Adding an Event to the Store
        store.insert(_event);

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Moving Iterator to the first record
        iterator.moveNext();

        // Getting first record
        Event e = iterator.current();

        // Verifying if the current is the only Event in Store
        Assert.assertSame(_event, e);
    }

    /**
     * Adds an Event to the Store, queries the Store
     * and remove the current Event from the Store
     */
    @Test
    public void Remove_Valid(){
        // Creating a new Store
        EventStore store = new MemoryEventStore();
        // Adding an Event to the Store
        store.insert(_event);

        // Querying the Store
        EventIterator iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Moving Iterator to the first record
        iterator.moveNext();

        // Removing the current Event from the Store
        iterator.remove();

        // Querying the Store again to check if Event was removed
        iterator = store.query(EVENT_TYPE, Long.MIN_VALUE, Long.MAX_VALUE);

        // Verifying if the Store has Events
        Assert.assertFalse(iterator.moveNext());
    }
}
