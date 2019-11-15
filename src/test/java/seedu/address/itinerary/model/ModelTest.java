package seedu.address.itinerary.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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


class ModelTest {

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

    private Model model = new Model();

    @Test
    void hasEvent() {
        // Checking the empty list no event available.
        assertFalse(model.hasEvent(eventTest));

        // Adding the event and checking whether it contains it.
        model.addEvent(eventTest);
        assertTrue(model.hasEvent(eventTest));

        // Checking with another event. However, the event has the same date and time -> considered as equal.
        assertTrue(model.hasEvent(eventTest2));

        // Adding event test 2 and delete event 1 should still give true when checking for event 1
        model.addEvent(eventTest2);
        model.deleteEvent(eventTest);
        assertTrue(model.hasEvent(eventTest));

        // Checking with another totally different event.
        assertFalse(model.hasEvent(eventTest3));

        // Adding the event and checking whether it contains it.
        model.addEvent(eventTest3);
        assertTrue(model.hasEvent(eventTest3));

        // Deleting the event and checking whether it contains it.
        model.deleteEvent(eventTest3);
        assertFalse(model.hasEvent(eventTest3));
    }

    @Test
    void clearTest() {
        // Adding some events to the event list.
        model.addEvent(eventTest);
        model.addEvent(eventTest2);
        model.addEvent(eventTest3);

        // Before clearing the event list, the size should initially be 3.
        assertEquals(3, model.getSortedEventList().size());

        // Calling clear event command. Comparing with empty array created.
        model.clearEvent();
        Event[] expectedEvent = new Event[0];

        assertArrayEquals(expectedEvent, model.getSortedEventList().toArray());

        // Adding some events to the empty eventlist should increase the size
        model.addEvent(eventTest);
        model.addEvent(eventTest2);

        assertEquals(2, model.getSortedEventList().size());
    }
}
