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

    private static TrainingManager trainingManager = new TrainingManager();

    static {
        trainingManager.addTraining(FIRST_TRAINING);
        trainingManager.addTraining(SECOND_TRAINING);
        trainingManager.addTraining(THIRD_TRAINING);
        trainingManager.addTraining(FOURTH_TRAINING);
    }

    @Test
    void constructorTest() {
        AttendanceRateEntry entry = new AttendanceRateEntry(ALICE,
                trainingManager.getPersonAttendanceRateString(ALICE));
        assertNotNull(entry);
    }

    @Test
    void getPersonTest() {
        AttendanceRateEntry entry = new AttendanceRateEntry(BENSON,
                trainingManager.getPersonAttendanceRateString(BENSON));
        assertEquals(entry.getPerson(), BENSON);
        assertNotEquals(entry.getPerson(), ALICE);
    }

    @Test
    void getAttendanceRateString() {
        AttendanceRateEntry entry = new AttendanceRateEntry(CARL, trainingManager.getPersonAttendanceRateString(CARL));
        assertEquals(entry.getAttendanceRateString(), trainingManager.getPersonAttendanceRateString(CARL));
    }

    @Test
    void testEquals() {
        AttendanceRateEntry firstEntry = new AttendanceRateEntry(DANIEL,
                trainingManager.getPersonAttendanceRateString(DANIEL));
        AttendanceRateEntry firstEntryCopy = new AttendanceRateEntry(DANIEL,
                trainingManager.getPersonAttendanceRateString(DANIEL));
        AttendanceRateEntry secondEntry = new AttendanceRateEntry(ELLE,
                trainingManager.getPersonAttendanceRateString(ELLE));
        AttendanceRateEntry secondEntryInvalid = new AttendanceRateEntry(ELLE,
                trainingManager.getPersonAttendanceRateString(GEORGE));
        assertEquals(firstEntry, firstEntry);
        assertEquals(firstEntry, firstEntryCopy);
        assertNotEquals(firstEntry, secondEntry);
        assertNotEquals(secondEntry, secondEntryInvalid);
    }
}
