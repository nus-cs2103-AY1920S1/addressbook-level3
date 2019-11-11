package seedu.address.itinerary.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;


class EventListTest {

    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("2000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest,
            descTest, timeTest, tagTest);

    private Title titleTest2 = new Title("Same Time Awesome Title");
    private Date dateTest2 = new Date("28102019");
    private Location locationTest2 = new Location("USA");
    private Description descTest2 = new Description("My cool description");
    private Time timeTest2 = new Time("2000");
    private Tag tagTest2 = new Tag("Priority: Medium");
    private Event eventTest2 = new Event(titleTest2, dateTest2, locationTest2,
            descTest2, timeTest2, tagTest2);

    private Title titleTest3 = new Title("Another Cool Title");
    private Date dateTest3 = new Date("28102019");
    private Location locationTest3 = new Location("USA");
    private Description descTest3 = new Description("My cool description");
    private Time timeTest3 = new Time("0000");
    private Tag tagTest3 = new Tag("Priority: Medium");
    private Event eventTest3 = new Event(titleTest3, dateTest3, locationTest3,
            descTest3, timeTest3, tagTest3);

    private EventList eventListTest = new EventList();

    @Test
    void getSize() {
        // Starting off with the empty list
        assertEquals(0, eventListTest.getSize());

        // Testing adding of event and checking the size
        eventListTest.addEvent(eventTest);
        assertEquals(1, eventListTest.getSize());

        // Testing adding of event and checking the size
        eventListTest.addEvent(eventTest2);
        assertEquals(2, eventListTest.getSize());

        // Removing the event and checking the size
        eventListTest.deleteEvent(eventTest);
        assertEquals(1, eventListTest.getSize());

        // Removing the event and checking the size
        eventListTest.deleteEvent(eventTest2);
        assertEquals(0, eventListTest.getSize());

        // Testing adding of event and checking the size
        eventListTest.addEvent(eventTest3);
        assertEquals(1, eventListTest.getSize());
    }

    @Test
    void contains() {
        // Checking the empty list no event available.
        assertFalse(eventListTest.contains(eventTest));

        // Adding the event and checking whether it contains it.
        eventListTest.addEvent(eventTest);
        assertTrue(eventListTest.contains(eventTest));

        // Checking with another event. However, the event has the same date and time -> considered as equal.
        assertTrue(eventListTest.contains(eventTest2));

        // Adding event test 2 and delete event 1 should still give true when checking for event 1
        eventListTest.addEvent(eventTest2);
        eventListTest.deleteEvent(eventTest);
        assertTrue(eventListTest.contains(eventTest));

        // Checking with another totally different event.
        assertFalse(eventListTest.contains(eventTest3));

        // Adding the event and checking whether it contains it.
        eventListTest.addEvent(eventTest3);
        assertTrue(eventListTest.contains(eventTest3));

        // Deleting the event and checking whether it contains it.
        eventListTest.deleteEvent(eventTest3);
        assertFalse(eventListTest.contains(eventTest3));
    }
}
