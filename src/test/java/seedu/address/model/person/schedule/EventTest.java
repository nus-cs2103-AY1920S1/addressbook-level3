package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

    private String eventName;
    private ArrayList<Timeslot> timeslots;
    private Event event;

    private LocalDateTime startTime1 = LocalDateTime.parse("2007-12-03T10:15:30");
    private LocalDateTime endTime1 = LocalDateTime.parse("2007-12-03T10:16:30");
    private Venue venue1 = new Venue("venue1");
    private Timeslot timeslot1 = new Timeslot(startTime1, endTime1, venue1);

    private LocalDateTime startTime2 = LocalDateTime.parse("2007-12-03T10:17:30");
    private LocalDateTime endTime2 = LocalDateTime.parse("2007-12-03T10:18:30");
    private Venue venue2 = new Venue("venue2");
    private Timeslot timeslot2 = new Timeslot(startTime2, endTime2, venue2);

    @BeforeEach
    void init() {
        eventName = "event name";
        timeslots = new ArrayList<Timeslot>();
        event = new Event(eventName, timeslots);
    }

    @Test
    void addTimeslot() {
        assertTrue(event.addTimeslot(timeslot1));
    }

    @Test
    void testAddTimeslot() {
        ArrayList<Timeslot> arr = new ArrayList<Timeslot>();
        arr.add(timeslot1);
        arr.add(timeslot2);
        assertTrue(event.addTimeslot(arr));
    }

    @Test
    void getTimeslots() {
        event.addTimeslot(timeslot1);
        ArrayList<Timeslot> arr = event.getTimeslots();
        assertTrue(arr.size() == 1);
        assertTrue(arr.get(0).equals(timeslot1));
    }

    @Test
    void testEquals() {
        event.addTimeslot(timeslot1);
        event.addTimeslot(timeslot2);

        Event otherEvent = new Event("event name");
        otherEvent.addTimeslot(timeslot1);
        otherEvent.addTimeslot(timeslot2);

        assertTrue(event.equals(otherEvent));
    }

    @Test
    void getEventName() {
        assertTrue(event.getEventName().equals("event name"));
    }
}
