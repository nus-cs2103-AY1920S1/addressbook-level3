package seedu.address.model.person.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    String eventName;
    ArrayList<Timeslot> timeslots;
    Event event;

    LocalDateTime startTime1 = LocalDateTime.parse("2007-12-03T10:15:30");
    LocalDateTime endTime1 = LocalDateTime.parse("2007-12-03T10:16:30");
    Venue venue1 = new Venue("venue1");
    Timeslot timeslot1 = new Timeslot(startTime1, endTime1, venue1);

    LocalDateTime startTime2 = LocalDateTime.parse("2007-12-03T10:17:30");
    LocalDateTime endTime2 = LocalDateTime.parse("2007-12-03T10:18:30");
    Venue venue2 = new Venue("venue2");
    Timeslot timeslot2 = new Timeslot(startTime2, endTime2, venue2);

    @BeforeEach
    void init(){
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