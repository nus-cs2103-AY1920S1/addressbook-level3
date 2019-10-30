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
    private Event eventTest = new Event(titleTest, dateTest, locationTest
            , descTest, timeTest, tagTest);
    EventList eventListTest = new EventList();

    @Test
    void getSize() {
        assertEquals(eventListTest.getSize(), 0);
        eventListTest.addEvent(eventTest);
        assertEquals(eventListTest.getSize(), 1);
    }

    @Test
    void contains() {
        assertFalse(eventListTest.contains(eventTest));
        eventListTest.addEvent(eventTest);
        assertTrue(eventListTest.contains(eventTest));
    }
}
