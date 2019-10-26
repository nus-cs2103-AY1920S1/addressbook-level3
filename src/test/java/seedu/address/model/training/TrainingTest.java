package seedu.address.model.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalTraining;

class TrainingTest {

    @Test
    public void test_validDate_returnTrue() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            Training training = new Training(date, new TypicalTraining().getTraining());
            assertTrue(date.equals(training.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_validTrainingAttendance_returnTrue() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());
            assertTrue(trainingGroup.getTraining().equals(training.getTrainingAttendance()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_personExists() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            assertTrue(training.hasPerson(ALICE));
            assertTrue(training.hasPerson(BENSON));
            assertTrue(training.hasPerson(CARL));
            assertTrue(training.hasPerson(DANIEL));
            assertTrue(training.hasPerson(ELLE));
            assertTrue(training.hasPerson(FIONA));
            assertTrue(training.hasPerson(GEORGE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_personDoesNotExists() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            assertFalse(training.hasPerson(HOON));
            assertFalse(training.hasPerson(IDA));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_personAttended() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            assertTrue(training.hasPersonAttended(ALICE));
            assertTrue(training.hasPersonAttended(BENSON));
            assertTrue(training.hasPersonAttended(ELLE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_personAbsent() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            assertFalse(training.hasPersonAttended(CARL));
            assertFalse(training.hasPersonAttended(GEORGE));
            assertFalse(training.hasPersonAttended(FIONA));
            assertFalse(training.hasPersonAttended(DANIEL));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_getAttended() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            List<Person> attended = training.getAttended();
            List<Person> selfCreatedAttended = new ArrayList<>();
            selfCreatedAttended.add(ALICE);
            selfCreatedAttended.add(BENSON);
            selfCreatedAttended.add(ELLE);

            assertTrue(attended.containsAll(selfCreatedAttended));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_getAbsent() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());

            List<Person> absentees = training.getAbsentees();
            List<Person> selfCreatedAbsentees = new ArrayList<>();
            selfCreatedAbsentees.add(CARL);
            selfCreatedAbsentees.add(GEORGE);
            selfCreatedAbsentees.add(FIONA);
            selfCreatedAbsentees.add(DANIEL);

            assertTrue(absentees.containsAll(selfCreatedAbsentees));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
