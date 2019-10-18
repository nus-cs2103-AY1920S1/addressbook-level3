package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class EventDayTimeTest {

    @Test
    void eventDayTimeToString() {
        EventDayTime edtTest = new EventDayTime(LocalTime.of(10, 20), LocalTime.of(20, 30));
        assertEquals(edtTest.toString(), "1020-2030");

        EventDayTime edtTest2 = new EventDayTime(LocalTime.of(00, 03), LocalTime.of(02, 50));
        assertEquals(edtTest2.toString(), "0003-0250");
    }

    @Test
    void isValidEventDayTime() {
        //null event time
        assertThrows(NullPointerException.class, () -> EventDayTime.isValidTime(null));

        //invalid time format
        assertFalse(EventDayTime.isValidTime("1020")); //single value
        assertFalse(EventDayTime.isValidTime("10:20-12:30")); //no colons
        assertFalse(EventDayTime.isValidTime("1020, 2030")); //not seperated by commas
        assertFalse(EventDayTime.isValidTime("1020-2530")); //invalid time

        //valid time format
        assertTrue(EventDayTime.isValidTime("1020-2030"));
        assertTrue(EventDayTime.isValidTime("0020-0530"));

    }

    @Test
    void eventDayTimeEquals() {
        assertEquals(new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 20)),
                new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 20)));

        assertNotEquals(new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 20)),
                new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 10)));
    }

}
