package net.intelie.challenges;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {

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
    public void CreateEvent_Valid(){
        Event e = new Event("MyType", 123456L);

        Assert.assertNotNull(e);
    }

    /**
     * Creates an Event with the type as null
     */
    @Test(expected = IllegalArgumentException.class)
    public void CreateEvent_NullType_Invalid(){
        Event e = new Event(null, 123456);
    }
}