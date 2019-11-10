package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1230;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1800;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_1200_TO_1800;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class EventDayTimeTest {

    @Test
    void eventDayTimeNumMins() {
        assertEquals(TIME_0800_TO_1800.numMinutes(), 600);
        assertEquals(TIME_0800_TO_1230.numMinutes(), 270);
        assertEquals(TIME_1200_TO_1800.numMinutes(), 360);
    }

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
        assertThrows(NullPointerException.class, () -> EventDayTime.isValidEventDayTime(null));

        //invalid time format
        assertFalse(EventDayTime.isValidEventDayTime("1020")); //single value
        assertFalse(EventDayTime.isValidEventDayTime("10:20-12:30")); //no colons
        assertFalse(EventDayTime.isValidEventDayTime("1020, 2030")); //not seperated by commas
        assertFalse(EventDayTime.isValidEventDayTime("1020-2530")); //invalid tim
        assertFalse(EventDayTime.isValidEventDayTime("1000-1000")); //start and end cannot be same time
        assertFalse(EventDayTime.isValidEventDayTime("1000-0900")); //start cannot be after end

        //valid time format
        assertTrue(EventDayTime.isValidEventDayTime("1020-2030"));
        assertTrue(EventDayTime.isValidEventDayTime("0020-0530"));
    }

    @Test
    void eventDayTimeEquals() {
        assertEquals(new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 30)),
                new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 30)));

        assertNotEquals(new EventDayTime(LocalTime.of(10, 30), LocalTime.of(10, 40)),
                new EventDayTime(LocalTime.of(10, 30), LocalTime.of(10, 45)));
    }

}
