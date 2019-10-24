package seedu.address.model.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
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
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Training(null, null));
    }

    @Test
    public void test_validDate_returnTrue() {
        Date date = new Date();
        Training training = new Training(date, new TypicalTraining().getTraining());
        assertTrue(date.equals(training.getDate()));
    }

    @Test
    public void test_validTrainingAttendance_returnTrue() {
        Date date = new Date();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());
        assertTrue(trainingGroup.getTraining().equals(training.getTrainingAttendance()));
    }

    @Test
    public void test_personHasAttendance_returnTrue() {
        Date date = new Date();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());

        assertTrue(training.getPersonAttendance(ALICE));
        assertTrue(training.getPersonAttendance(BENSON));
        assertTrue(training.getPersonAttendance(ELLE));
    }

    @Test
    public void test_personHasNoAttendance_returnFalse() {
        Date date = new Date();
        TypicalTraining trainingGroup = new TypicalTraining();
        Training training = new Training(date, trainingGroup.getTraining());

        assertFalse(training.getPersonAttendance(CARL));
        assertFalse(training.getPersonAttendance(GEORGE));
        assertFalse(training.getPersonAttendance(FIONA));
        assertFalse(training.getPersonAttendance(DANIEL));
    }
}
