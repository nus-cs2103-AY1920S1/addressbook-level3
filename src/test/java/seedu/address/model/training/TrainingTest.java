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

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalTraining;

class TrainingTest {

    @Test
    public void test_validDate_returnTrue() {
        String date = new Date().toString();
        Training training = new Training(date, new TypicalTraining().getTraining());
        assertTrue(date.equals(training.getDate()));
    }

    @Test
    public void test_validTrainingAttendance_returnTrue() {
        String date = new Date().toString();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());
        assertTrue(trainingGroup.getTraining().equals(training.getTrainingAttendance()));
    }

    @Test
    public void test_personHasAttendance_returnTrue() {
        String date = new Date().toString();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());

        assertTrue(training.getPersonAttendance(ALICE));
        assertTrue(training.getPersonAttendance(BENSON));
        assertTrue(training.getPersonAttendance(ELLE));
    }

    @Test
    public void test_personHasNoAttendance_returnFalse() {
        String date = new Date().toString();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());

        assertFalse(training.getPersonAttendance(CARL));
        assertFalse(training.getPersonAttendance(GEORGE));
        assertFalse(training.getPersonAttendance(FIONA));
        assertFalse(training.getPersonAttendance(DANIEL));
    }
}
