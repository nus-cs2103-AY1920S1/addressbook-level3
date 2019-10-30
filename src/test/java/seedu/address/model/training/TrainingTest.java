package seedu.address.model.training;

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
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;

class TrainingTest {

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
    public void test_getAttended() {
        Training training = FIRST_TRAINING;

        List<Person> attended = training.getAttended();
        List<Person> selfCreatedAttended = new ArrayList<>();
        selfCreatedAttended.add(ALICE);
        selfCreatedAttended.add(BENSON);
        selfCreatedAttended.add(ELLE);

        assertTrue(attended.containsAll(selfCreatedAttended));
    }

    @Test
    public void test_getAbsent() {
        Training training = FIRST_TRAINING;

        List<Person> absentees = training.getAbsentees();
        List<Person> selfCreatedAbsentees = new ArrayList<>();
        selfCreatedAbsentees.add(CARL);
        selfCreatedAbsentees.add(GEORGE);
        selfCreatedAbsentees.add(FIONA);
        selfCreatedAbsentees.add(DANIEL);

        assertTrue(absentees.containsAll(selfCreatedAbsentees));
    }
}
