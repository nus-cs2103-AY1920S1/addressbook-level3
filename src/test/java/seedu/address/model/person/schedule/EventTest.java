package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT3;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.scheduleutil.TypicalEvents;

class EventTest {

    private Event event;

    @BeforeEach
    void init() {
        event = TypicalEvents.generateTypicalEvent1();
    }

    @Test
    void addTimeslot_valid() {
        Event event = TypicalEvents.generateEmptyEvent();
        assertTrue(event.addTimeslot(TIME_SLOT1));
    }

    @Test
    void addTimeslot_null() {
        Event event = TypicalEvents.generateEmptyEvent();
        assertFalse(event.addTimeslot((Timeslot) null));
    }

    @Test
    void addTimeslot_validArray() {
        Event event = TypicalEvents.generateEmptyEvent();
        ArrayList<Timeslot> arr = new ArrayList<>();
        arr.add(TIME_SLOT1);
        arr.add(TIME_SLOT2);
        assertTrue(event.addTimeslot(arr));
    }

    @Test
    void addTimeslot_nullArray() {
        Event event = TypicalEvents.generateEmptyEvent();
        assertFalse(event.addTimeslot((ArrayList<Timeslot>) null));
    }

    @Test
    void getTimeslots() {
        Event event = TypicalEvents.generateTypicalEvent1();
        ArrayList<Timeslot> arr = event.getTimeslots();
        assertTrue(arr.size() == 1);
        assertTrue(arr.get(0).equals(TIME_SLOT1));
        assertFalse(arr.get(0).equals(TIME_SLOT2));

    }

    @Test
    void testEquals_null() {
        assertFalse(event.equals(null));
    }

    @Test
    void testEquals_notSameSize() {
        Event event2 = TypicalEvents.generateTypicalEvent1();
        event2.addTimeslot(TIME_SLOT2);
        assertFalse(event.equals(event2));
    }

    @Test
    void testEquals_differentTimeslots() {
        event.addTimeslot(TIME_SLOT2);
        Event event2 = TypicalEvents.generateTypicalEvent1();
        event2.addTimeslot(TIME_SLOT3);
        assertFalse(event.equals(event2));
    }

    @Test
    void testEquals() {
        Event event = TypicalEvents.generateTypicalEvent2();
        Event sameEvent = TypicalEvents.generateTypicalEvent2();
        Event otherEvent = TypicalEvents.generateTypicalEvent1();

        assertTrue(event.equals(sameEvent));
        assertFalse(event.equals(otherEvent));
    }

    @Test
    void getEventName() {
        Event event1 = TypicalEvents.generateTypicalEvent1();
        assertTrue(event1.getEventName().equals(EVENT_NAME1));
        assertFalse(event1.getEventName().equals(EVENT_NAME2));
    }

    @Test
    void toStringTest() {
        assertEquals(event.toString(), event.toString());
    }
}
