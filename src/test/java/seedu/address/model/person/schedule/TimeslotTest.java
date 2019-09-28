package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.ENDTIME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.ENDTIME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.STARTTIME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.STARTTIME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE2;

import org.junit.jupiter.api.Test;

class TimeslotTest {

    @Test
    void testEquals() {
        assertTrue(TIMESLOT1.equals(TIMESLOT1));
        assertFalse(TIMESLOT1.equals(TIMESLOT2));
    }

    @Test
    void getStartTime() {
        assertTrue(TIMESLOT1.getStartTime().compareTo(STARTTIME1) == 0);
        assertFalse(TIMESLOT1.getStartTime().compareTo(STARTTIME2) == 0);
    }

    @Test
    void getEndTime() {
        assertTrue(TIMESLOT1.getEndTime().compareTo(ENDTIME1) == 0);
        assertFalse(TIMESLOT1.getEndTime().compareTo(ENDTIME2) == 0);
    }

    @Test
    void getVenue() {
        assertTrue(VENUE1.equals(TIMESLOT1.getVenue()));
        assertFalse(VENUE2.equals(TIMESLOT1.getVenue()));
    }
}
