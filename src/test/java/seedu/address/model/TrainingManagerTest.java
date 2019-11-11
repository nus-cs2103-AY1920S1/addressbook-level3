package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalAthletickDates.FIRST_DATE;
import static seedu.address.testutil.TypicalAthletickDates.SECOND_DATE;
import static seedu.address.testutil.TypicalAthletickDates.THIRD_DATE;
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
import static seedu.address.testutil.TypicalTrainings.FOURTH_TRAINING;
import static seedu.address.testutil.TypicalTrainings.SECOND_TRAINING;
import static seedu.address.testutil.TypicalTrainings.THIRD_TRAINING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.training.AttendanceEntry;
import seedu.address.model.training.Training;

class TrainingManagerTest {

    @Test
    void constructor() {
        TrainingManager trainingManagerWithoutList = new TrainingManager();
        assertNotNull(trainingManagerWithoutList);
        assertEquals(Collections.emptyList(), trainingManagerWithoutList.getTrainings());

        List<Training> trainings = new ArrayList<Training>();
        trainings.add(FIRST_TRAINING);
        trainings.add(SECOND_TRAINING);
        trainings.add(THIRD_TRAINING);
        trainings.add(FOURTH_TRAINING);

        TrainingManager trainingManagerWithList = new TrainingManager(trainings);
        assertNotNull(trainingManagerWithList);
        assertEquals(trainings, trainingManagerWithList.getTrainings());
    }

    @Test
    void hasTraining_validDate() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        trainings.add(SECOND_TRAINING);
        trainings.add(THIRD_TRAINING);
        TrainingManager trainingManager = new TrainingManager(trainings);

        assertTrue(trainingManager.hasTrainingOnDate(FIRST_DATE));
        assertTrue(trainingManager.hasTrainingOnDate(SECOND_DATE));
        assertTrue(trainingManager.hasTrainingOnDate(THIRD_DATE));
    }

    @Test
    void getTrainings() {
        TrainingManager trainingManager = new TrainingManager();
        assertNotNull(trainingManager.getTrainings());

        trainingManager.addTraining(FIRST_TRAINING);
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        assertEquals(trainings, trainingManager.getTrainings());

    }

    @Test
    void addTraining_normalAddition() {
        TrainingManager trainingManager = new TrainingManager();
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        trainingManager.addTraining(FIRST_TRAINING);
        assertEquals(trainingManager.getTrainings(), trainings);
    }

    @Test
    void addTraining_trainingWithSortedList_returnTrue() {
        TrainingManager trainingManager = new TrainingManager();
        List<Training> trainingsOrdered = new ArrayList<>();
        trainingsOrdered.add(FIRST_TRAINING);
        trainingsOrdered.add(FOURTH_TRAINING);
        trainingManager.addTraining(FOURTH_TRAINING);
        trainingManager.addTraining(FIRST_TRAINING);
        assertEquals(trainingManager.getTrainings(), trainingsOrdered);
    }

    @Test
    void addTraining_trainingWithUnsortedList_returnFalse() {
        TrainingManager trainingManager = new TrainingManager();
        List<Training> trainingsUnordered = new ArrayList<>();
        trainingsUnordered.add(FOURTH_TRAINING);
        trainingsUnordered.add(FIRST_TRAINING);
        trainingManager.addTraining(FOURTH_TRAINING);
        trainingManager.addTraining(FIRST_TRAINING);
        assertNotEquals(trainingManager.getTrainings(), trainingsUnordered);
    }

    @Test
    void getPersonAttendedTrainings() {
        TrainingManager trainingManager = new TrainingManager();
        trainingManager.addTraining(FIRST_TRAINING);
        trainingManager.addTraining(SECOND_TRAINING);
        trainingManager.addTraining(THIRD_TRAINING);
        trainingManager.addTraining(FOURTH_TRAINING);
        assertEquals(4, trainingManager.getPersonAttendedTrainings(ALICE));
        assertEquals(2, trainingManager.getPersonAttendedTrainings(HOON));
    }

    @Test
    void getPersonAbsentTrainings() {
        TrainingManager trainingManager = new TrainingManager();
        trainingManager.addTraining(FIRST_TRAINING);
        trainingManager.addTraining(SECOND_TRAINING);
        trainingManager.addTraining(THIRD_TRAINING);
        assertEquals(1, trainingManager.getPersonAbsentTrainings(GEORGE));
        assertEquals(1, trainingManager.getPersonAbsentTrainings(HOON));
    }

    @Test
    void getPersonTotalTrainings() {
        TrainingManager trainingManager = new TrainingManager();
        trainingManager.addTraining(FIRST_TRAINING);
        trainingManager.addTraining(SECOND_TRAINING);
        trainingManager.addTraining(THIRD_TRAINING);
        trainingManager.addTraining(FOURTH_TRAINING);
        assertEquals(4, trainingManager.getPersonTotalTrainings(ALICE));
        assertEquals(4, trainingManager.getPersonTotalTrainings(GEORGE));
        assertEquals(3, trainingManager.getPersonTotalTrainings(HOON));
        assertEquals(2, trainingManager.getPersonTotalTrainings(IDA));
    }

    @Test
    void getTrainingAttendanceListOnDate() {
        TrainingManager trainingManager = new TrainingManager();
        trainingManager.addTraining(FIRST_TRAINING);
        List<AttendanceEntry> firstAttendanceEntries = trainingManager.getTrainingAttendanceListOnDate(FIRST_DATE);
        List<AttendanceEntry> attendanceEntries = new ArrayList<>();
        attendanceEntries.add(new AttendanceEntry(ALICE, true));
        attendanceEntries.add(new AttendanceEntry(BENSON, true));
        attendanceEntries.add(new AttendanceEntry(CARL, false));
        attendanceEntries.add(new AttendanceEntry(DANIEL, false));
        attendanceEntries.add(new AttendanceEntry(ELLE, true));
        attendanceEntries.add(new AttendanceEntry(FIONA, false));
        attendanceEntries.add(new AttendanceEntry(GEORGE, false));
        assertTrue(firstAttendanceEntries.containsAll(attendanceEntries));
    }
}
