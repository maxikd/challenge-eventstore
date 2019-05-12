package net.intelie.challenges;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class EventTest {

    @Rule
    public ExpectedException nullType = ExpectedException.none();
    @Rule
    public ExpectedException emptyType = ExpectedException.none();

    @Test
    public void thisIsAWarning() throws Exception {
        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.timestamp());
        assertEquals("some_type", event.type());
    }

    /**
     * Creates an Event with correct information
     */
    @Test
    public void CreateEvent_Valid() {
        Event e = new Event("MyType", 123456L);

        Assert.assertNotNull(e);
    }

    /**
     * Creates an Event with the type as null
     */
    @Test
    public void CreateEvent_NullType_Invalid() {
        nullType.expect(IllegalArgumentException.class);
        nullType.expectMessage("Argument 'type' cannot be null.");

        Event e = new Event(null, 123456);
    }

    /**
     * Creates an Event with the type as empty
     */
    @Test
    public void CreateEvent_EmptyType_Invalid(){
        emptyType.expect(IllegalArgumentException.class);
        emptyType.expectMessage("Argument 'type' cannot be empty.");

        Event e = new Event("", 123456L);
    }

    /**
     * Creates an Event with the timestamp as 0
     */
    @Test
    public void CreateEvent_ZeroTimestamp_Invalid(){
        emptyType.expect(IllegalArgumentException.class);
        emptyType.expectMessage("Argument 'timestamp' cannot be smaller than 1.");

        Event e = new Event("Doctor", 0L);
    }

    /**
     * Creates an Event with the timestamp as less than 0
     */
    @Test
    public void CreateEvent_LessThanZeroTimestamp_Invalid(){
        emptyType.expect(IllegalArgumentException.class);
        emptyType.expectMessage("Argument 'timestamp' cannot be smaller than 1.");

        Event e = new Event("Doctor", -1L);
    }
}