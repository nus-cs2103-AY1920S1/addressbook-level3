package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventTest {
    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("2000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest
            , descTest, timeTest, tagTest);

    @Test
    void getTitle() {
        assertEquals(eventTest.getTitle().toString(), "Awesome Title");
    }

    @Test
    void getDesc() {
        assertEquals(eventTest.getDesc().toString(), "My awesome description");
    }

    @Test
    void getDate() {
        assertEquals(eventTest.getDate().toString(), "28/10/2019");
    }

    @Test
    void getLocation() {
        assertEquals(eventTest.getLocation().toString(), "Singapore");
    }

    @Test
    void getTime() {
        assertEquals(eventTest.getTime().toString(), "8:00 p.m.");
    }

    @Test
    void getTag() {
        assertEquals(eventTest.getTag().toString(), "Priority: High");
    }

    @Test
    void getIsDone() {
        assertFalse(eventTest.getIsDone());
        eventTest.markIsDone();
        assertTrue(eventTest.getIsDone());
    }
}
