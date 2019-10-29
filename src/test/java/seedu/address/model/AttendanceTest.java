package seedu.address.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.training.Training;
import seedu.address.testutil.TypicalTraining;

class AttendanceTest {

    @Test
    void constructor() {
        Attendance attendanceWithoutList = new Attendance();
        assertEquals(Collections.emptyList(), attendanceWithoutList.getTrainings());
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());
            List<Training> trainings = new ArrayList<Training>();
            trainings.add(training);
            Attendance attendanceWithList = new Attendance(trainings);
            assertEquals(trainings, attendanceWithList.getTrainings());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testHasTraining_validDate() {
        try {
            AthletickDate date = new AthletickDate("20012019");
            TypicalTraining trainingGroup = new TypicalTraining();
            Training training = new Training(date, trainingGroup.getTraining());
            List<Training> trainings = new ArrayList<Training>();
            trainings.add(training);
            Attendance attendanceWithList = new Attendance(trainings);
            assertEquals(trainings, attendanceWithList.getTrainings());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTraining() {
    }

    @Test
    void getTrainings() {
    }

    @Test
    void getPersonAttendance() {
    }

    @Test
    void selectTraining() {
    }

    @Test
    void getAttended() {
    }
}