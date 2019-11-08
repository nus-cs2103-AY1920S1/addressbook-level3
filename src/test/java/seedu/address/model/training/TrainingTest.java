package seedu.address.model.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAthletickDates.FIRST_DATE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalTrainings.FIRST_TRAINING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;

class TrainingTest {

    @Test
    public void constructor() {
        Training training = new Training(FIRST_DATE, new HashMap<Person, Boolean>());
        assertNotNull(training);
    }

    @Test
    public void getDate() {
        assertEquals(FIRST_TRAINING.getDate(), FIRST_DATE);
    }

    @Test
    public void getTrainingAttendance() {
        assertNotNull(FIRST_TRAINING.getTrainingAttendance());
    }

    @Test
    public void test_validDate_returnTrue() {
        AthletickDate date = FIRST_DATE;
        Training training = FIRST_TRAINING;
        assertNotNull(training.getDate());
        assertTrue(date.equals(training.getDate()));
    }

    @Test
    public void test_validTrainingAttendance_returnTrue() {
        Training training = FIRST_TRAINING;
        assertNotNull(training);
    }

    @Test
    public void test_personExists() {
        Training training = FIRST_TRAINING;

        assertTrue(training.hasPerson(ALICE));
        assertTrue(training.hasPerson(BENSON));
        assertTrue(training.hasPerson(CARL));
        assertTrue(training.hasPerson(DANIEL));
        assertTrue(training.hasPerson(ELLE));
        assertTrue(training.hasPerson(FIONA));
        assertTrue(training.hasPerson(GEORGE));
    }

    @Test
    public void test_personDoesNotExists() {
        Training training = FIRST_TRAINING;

        assertFalse(training.hasPerson(HOON));
        assertFalse(training.hasPerson(IDA));
    }

    @Test
    public void test_personAttended() {
        Training training = FIRST_TRAINING;

        assertTrue(training.hasPersonAttended(ALICE));
        assertTrue(training.hasPersonAttended(BENSON));
        assertTrue(training.hasPersonAttended(ELLE));
    }

    @Test
    public void test_personAbsent() {
        Training training = FIRST_TRAINING;

        assertFalse(training.hasPersonAttended(CARL));
        assertFalse(training.hasPersonAttended(GEORGE));
        assertFalse(training.hasPersonAttended(FIONA));
        assertFalse(training.hasPersonAttended(DANIEL));
    }

    @Test
    public void test_getTrainingAttendanceList() {
        Training training = FIRST_TRAINING;
        List<AttendanceEntry> attendanceEntries = new ArrayList<>();
        attendanceEntries.add(new AttendanceEntry(ALICE, true));
        attendanceEntries.add(new AttendanceEntry(BENSON, true));
        attendanceEntries.add(new AttendanceEntry(CARL, false));
        attendanceEntries.add(new AttendanceEntry(DANIEL, false));
        attendanceEntries.add(new AttendanceEntry(ELLE, true));
        attendanceEntries.add(new AttendanceEntry(FIONA, false));
        attendanceEntries.add(new AttendanceEntry(GEORGE, false));
        assertTrue(training.getTrainingAttendanceList().containsAll(attendanceEntries));
    }


}
