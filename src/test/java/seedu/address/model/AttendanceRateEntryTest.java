package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalTrainings.FIRST_TRAINING;
import static seedu.address.testutil.TypicalTrainings.FOURTH_TRAINING;
import static seedu.address.testutil.TypicalTrainings.SECOND_TRAINING;
import static seedu.address.testutil.TypicalTrainings.THIRD_TRAINING;

import org.junit.jupiter.api.Test;

class AttendanceRateEntryTest {

    private static Attendance attendance = new Attendance();

    static {
        attendance.addTraining(FIRST_TRAINING);
        attendance.addTraining(SECOND_TRAINING);
        attendance.addTraining(THIRD_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
    }

    @Test
    void constructorTest() {
        AttendanceRateEntry entry = new AttendanceRateEntry(ALICE, attendance.getPersonAttendanceRateString(ALICE));
        assertNotNull(entry);
    }

    @Test
    void getPersonTest() {
        AttendanceRateEntry entry = new AttendanceRateEntry(BENSON, attendance.getPersonAttendanceRateString(BENSON));
        assertEquals(entry.getPerson(), BENSON);
        assertNotEquals(entry.getPerson(), ALICE);
    }

    @Test
    void getAttendanceRateString() {
        AttendanceRateEntry entry = new AttendanceRateEntry(CARL, attendance.getPersonAttendanceRateString(CARL));
        assertEquals(entry.getAttendanceRateString(), attendance.getPersonAttendanceRateString(CARL));
    }

    @Test
    void testEquals() {
        AttendanceRateEntry firstEntry = new AttendanceRateEntry(DANIEL,
                attendance.getPersonAttendanceRateString(DANIEL));
        AttendanceRateEntry firstEntryCopy = new AttendanceRateEntry(DANIEL,
                attendance.getPersonAttendanceRateString(DANIEL));
        AttendanceRateEntry secondEntry = new AttendanceRateEntry(ELLE, attendance.getPersonAttendanceRateString(ELLE));
        AttendanceRateEntry secondEntryInvalid = new AttendanceRateEntry(ELLE,
                attendance.getPersonAttendanceRateString(GEORGE));
        assertEquals(firstEntry, firstEntry);
        assertEquals(firstEntry, firstEntryCopy);
        assertNotEquals(firstEntry, secondEntry);
        assertNotEquals(secondEntry, secondEntryInvalid);
    }
}
