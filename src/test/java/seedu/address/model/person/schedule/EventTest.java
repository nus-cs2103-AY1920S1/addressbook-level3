package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENTNAME1;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENTNAME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT2;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.scheduleutil.TypicalEvents;

class EventTest {

    @Test
    void addTimeslot() {
        Event event = TypicalEvents.generateEmptyEvent();
        assertTrue(event.addTimeslot(TIMESLOT1));
    }

    @Test
    void testAddTimeslot() {
        Event event = TypicalEvents.generateEmptyEvent();
        ArrayList<Timeslot> arr = new ArrayList<Timeslot>();
        arr.add(TIMESLOT1);
        arr.add(TIMESLOT2);
        assertTrue(event.addTimeslot(arr));
    }

    @Test
    void getTimeslots() {
        Event event = TypicalEvents.generateTypicalEvent1();
        ArrayList<Timeslot> arr = event.getTimeslots();
        assertTrue(arr.size() == 1);
        assertTrue(arr.get(0).equals(TIMESLOT1));
        assertFalse(arr.get(0).equals(TIMESLOT2));

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
        assertTrue(event1.getEventName().equals(EVENTNAME1));
        assertFalse(event1.getEventName().equals(EVENTNAME2));
    }
}
