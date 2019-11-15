package dukecooks.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventTest {

    private static Event event;

    @Test
    void test_equals() {
        event = Event.getInstance();
        Event anotherEvent = Event.getInstance();

        // same object
        assertTrue(event.equals(event));

        // different object, same fields
        assertTrue(event.equals(anotherEvent));

        // different objects and fields
        assertFalse(event.equals(null));
    }

    @Test
    void test_hashCode() {
        event = Event.getInstance();
        Event anotherEvent = Event.getInstance();
        assertEquals(event.hashCode(), anotherEvent.hashCode());
    }
}
