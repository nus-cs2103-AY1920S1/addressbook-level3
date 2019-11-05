package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventTest {

    // Both event 1 and 2 are the same to check that title are still the same
    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("0000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest,
            descTest, timeTest, tagTest);

    private Title titleTest2 = new Title("Awesome Title");
    private Date dateTest2 = new Date("28102019");
    private Location locationTest2 = new Location("Singapore");
    private Description descTest2 = new Description("My awesome description");
    private Time timeTest2 = new Time("0000");
    private Tag tagTest2 = new Tag("Priority: High");
    private Event eventTest2 = new Event(titleTest2, dateTest2, locationTest2,
            descTest2, timeTest2, tagTest2);

    private Title titleTest3 = new Title("This is another Awesome Title");
    private Date dateTest3 = new Date("29022000");
    private Location locationTest3 = new Location("Malaysia");
    private Description descTest3 = new Description("");
    private Time timeTest3 = new Time("1559");
    private Tag tagTest3 = new Tag("Priority: Low");
    private Event eventTest3 = new Event(titleTest3, dateTest3, locationTest3,
            descTest3, timeTest3, tagTest3);

    @Test
    void getTitle() {
        // getting the title attribute from each event
        assertEquals("Awesome Title", eventTest.getTitle().toString());
        assertEquals("This is another Awesome Title", eventTest3.getTitle().toString());

        // check whether event1 and event2 have the same title
        assertEquals(eventTest2.getTitle().toString(), eventTest.getTitle().toString());
    }

    @Test
    void getDesc() {
        // getting the description attribute from each event
        assertEquals("My awesome description", eventTest.getDesc().toString());
        assertEquals("", eventTest3.getDesc().toString());

        // check whether event1 and event2 have the same description
        assertEquals(eventTest.getDesc().toString(), eventTest2.getDesc().toString());
    }

    @Test
    void getDate() {
        // Checking with unformatted timing
        assertNotEquals("28102019", eventTest.getDate().toString());

        // getting the description attribute from each event
        assertEquals("28/10/2019", eventTest.getDate().toString());
        assertEquals("29/02/2000", eventTest3.getDate().toString());

        // check whether event1 and event2 have the same description
        assertEquals(eventTest.getDate().toString(), eventTest2.getDate().toString());
    }

    @Test
    void getLocation() {
        // getting the description attribute from each event
        assertEquals("Singapore", eventTest.getLocation().toString());
        assertEquals("Malaysia", eventTest3.getLocation().toString());

        // check whether event1 and event2 have the same description
        assertEquals(eventTest.getDate().toString(), eventTest2.getDate().toString());
    }

    @Test
    void getTime() {
        // Checking with unformatted timing
        assertNotEquals("2000", eventTest.getTime().toString());

        // getting the description attribute from each event
        assertEquals("12:00 a.m.", eventTest.getTime().toString());
        assertEquals("3:59 p.m.", eventTest3.getTime().toString());

        // check whether event1 and event2 have the same description
        assertEquals(eventTest.getTime().toString(), eventTest2.getTime().toString());
    }

    @Test
    void getTag() {
        // getting the description attribute from each event
        assertEquals("Priority: High", eventTest.getTag().toString());
        assertEquals("Priority: Low", eventTest3.getTag().toString());

        // check whether event1 and event2 have the same description
        assertEquals(eventTest.getTag().toString(), eventTest2.getTag().toString());
    }

    @Test
    void getIsDone() {
        // Check that the event is not done first
        assertFalse(eventTest.getIsDone());

        // Change the status of the event to done
        eventTest.markIsDone();
        assertTrue(eventTest.getIsDone());
    }
}
