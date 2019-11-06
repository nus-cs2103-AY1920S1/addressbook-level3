package seedu.address.model.person.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.END_TIME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.END_TIME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.END_TIME3;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.START_TIME1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.START_TIME2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.VENUE3;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.scheduleutil.TypicalTimeslots;

class TimeslotTest {

    @Test
    void testEquals() {
        assertTrue(TIME_SLOT1.equals(TIME_SLOT1));
        assertFalse(TIME_SLOT1.equals(TIME_SLOT2));
    }

    @Test
    void equals_differentStartTime() {
        assertFalse(
                new Timeslot(START_TIME1, END_TIME3, VENUE1).equals(
                        new Timeslot(START_TIME2, END_TIME3, VENUE1)
                )
        );
    }

    @Test
    void equals_differentEndTime() {
        assertFalse(
                new Timeslot(START_TIME1, END_TIME2, VENUE1).equals(
                        new Timeslot(START_TIME1, END_TIME3, VENUE1)
                )
        );
    }

    @Test
    void equals_differentVenue() {
        assertFalse(
                new Timeslot(START_TIME1, END_TIME2, VENUE1).equals(
                        new Timeslot(START_TIME1, END_TIME2, VENUE3)
                )
        );
    }

    @Test
    void getStartTime() {
        assertTrue(TIME_SLOT1.getStartTime().compareTo(START_TIME1) == 0);
        assertFalse(TIME_SLOT1.getStartTime().compareTo(START_TIME2) == 0);
    }

    @Test
    void getEndTime() {
        assertTrue(TIME_SLOT1.getEndTime().compareTo(END_TIME1) == 0);
        assertFalse(TIME_SLOT1.getEndTime().compareTo(END_TIME2) == 0);
    }

    @Test
    void getVenue() {
        assertTrue(VENUE1.equals(TypicalTimeslots.generateTimeslot1().getVenue()));
    }
}
