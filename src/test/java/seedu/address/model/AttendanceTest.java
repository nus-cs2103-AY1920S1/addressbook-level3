package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalAthletickDates.FIRST_DATE;
import static seedu.address.testutil.TypicalAthletickDates.FOURTH_DATE;
import static seedu.address.testutil.TypicalAthletickDates.SECOND_DATE;
import static seedu.address.testutil.TypicalAthletickDates.THIRD_DATE;
import static seedu.address.testutil.TypicalPersons.ALICE;
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

import seedu.address.model.training.Training;

class AttendanceTest {

    @Test
    void constructor() {
        Attendance attendanceWithoutList = new Attendance();
        assertNotNull(attendanceWithoutList);
        assertEquals(Collections.emptyList(), attendanceWithoutList.getTrainings());

        List<Training> trainings = new ArrayList<Training>();
        trainings.add(FIRST_TRAINING);
        trainings.add(SECOND_TRAINING);
        trainings.add(THIRD_TRAINING);
        trainings.add(FOURTH_TRAINING);

        Attendance attendanceWithList = new Attendance(trainings);
        assertNotNull(attendanceWithList);
        assertEquals(trainings, attendanceWithList.getTrainings());
    }

    @Test
    void hasTraining_validDate() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        trainings.add(SECOND_TRAINING);
        trainings.add(THIRD_TRAINING);
        Attendance attendance = new Attendance(trainings);

        assertTrue(attendance.hasTraining(FIRST_DATE));
        assertTrue(attendance.hasTraining(SECOND_DATE));
        assertTrue(attendance.hasTraining(THIRD_DATE));
    }

    @Test
    void getTrainings() {
        Attendance attendance = new Attendance();
        assertNotNull(attendance.getTrainings());

        attendance.addTraining(FIRST_TRAINING);
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        assertEquals(trainings, attendance.getTrainings());

    }

    @Test
    void addTraining_normalAddition() {
        Attendance attendance = new Attendance();
        List<Training> trainings = new ArrayList<>();
        trainings.add(FIRST_TRAINING);
        attendance.addTraining(FIRST_TRAINING);
        assertEquals(attendance.getTrainings(), trainings);
    }

    @Test
    void addTraining_trainingWithSortedList_returnTrue() {
        Attendance attendance = new Attendance();
        List<Training> trainingsOrdered = new ArrayList<>();
        trainingsOrdered.add(FIRST_TRAINING);
        trainingsOrdered.add(FOURTH_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
        attendance.addTraining(FIRST_TRAINING);
        assertEquals(attendance.getTrainings(), trainingsOrdered);
    }

    @Test
    void addTraining_trainingWithUnsortedList_returnFalse() {
        Attendance attendance = new Attendance();
        List<Training> trainingsUnordered = new ArrayList<>();
        trainingsUnordered.add(FOURTH_TRAINING);
        trainingsUnordered.add(FIRST_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
        attendance.addTraining(FIRST_TRAINING);
        assertNotEquals(attendance.getTrainings(), trainingsUnordered);
    }

    @Test
    void getPersonAttendedTrainings() {
        Attendance attendance = new Attendance();
        attendance.addTraining(FIRST_TRAINING);
        attendance.addTraining(SECOND_TRAINING);
        attendance.addTraining(THIRD_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
        assertEquals(4, attendance.getPersonAttendedTrainings(ALICE));
        assertEquals(2, attendance.getPersonAttendedTrainings(HOON));
    }

    @Test
    void getPersonAbsentTrainings() {
        Attendance attendance = new Attendance();
        attendance.addTraining(FIRST_TRAINING);
        attendance.addTraining(SECOND_TRAINING);
        attendance.addTraining(THIRD_TRAINING);
        assertEquals(1, attendance.getPersonAbsentTrainings(GEORGE));
        assertEquals(1, attendance.getPersonAbsentTrainings(HOON));
    }

    @Test
    void getPersonTotalTrainings() {
        Attendance attendance = new Attendance();
        attendance.addTraining(FIRST_TRAINING);
        attendance.addTraining(SECOND_TRAINING);
        attendance.addTraining(THIRD_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
        assertEquals(4, attendance.getPersonTotalTrainings(ALICE));
        assertEquals(4, attendance.getPersonTotalTrainings(GEORGE));
        assertEquals(3, attendance.getPersonTotalTrainings(HOON));
        assertEquals(2, attendance.getPersonTotalTrainings(IDA));
    }

    @Test
    void getTrainingOnDate() {
        Attendance attendance = new Attendance();
        attendance.addTraining(FIRST_TRAINING);
        attendance.addTraining(SECOND_TRAINING);
        attendance.addTraining(THIRD_TRAINING);
        attendance.addTraining(FOURTH_TRAINING);
        assertEquals(attendance.getTrainingOnDate(FIRST_DATE), FIRST_TRAINING);
        assertEquals(attendance.getTrainingOnDate(SECOND_DATE), SECOND_TRAINING);
        assertEquals(attendance.getTrainingOnDate(THIRD_DATE), THIRD_TRAINING);
        assertEquals(attendance.getTrainingOnDate(FOURTH_DATE), FOURTH_TRAINING);
    }
}
