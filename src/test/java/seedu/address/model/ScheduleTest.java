package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;
import seedu.address.testutil.SampleInterviewers;
import seedu.address.testutil.SampleSchedules;

public class ScheduleTest {

    @Test
    public void getInterviewsSlot_existingInterviewee_success() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();

        List<Slot> johnSlots = new LinkedList<>();
        johnSlots.add(new Slot("10/9/2019", "18:00", "18:30"));
        johnSlots.add(new Slot("10/9/2019", "18:30", "19:00"));

        List<Slot> selinaSlots = new LinkedList<>();
        selinaSlots.add(new Slot("10/9/2019", "19:30", "20:00"));
        selinaSlots.add(new Slot("10/9/2019", "20:00", "20:30"));

        assertEquals(johnSlots, schedule.getInterviewSlots("John"));
        assertEquals(selinaSlots, schedule.getInterviewSlots("Selina"));
    }

    @Test
    public void getInterviewsSlots_nonExistingInterviewee_emptyList() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();
        assertEquals(new LinkedList<>(), schedule.getInterviewSlots("ABC"));
    }

    @Test
    public void hasInterviewer_existingInterviewer_true() {
        Schedule schedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer hazel = SampleInterviewers.getHazel();
        assertTrue(schedule.hasInterviewer(hazel));
    }

    @Test
    public void hasInterviewer_nonExistingInterviewer_false() {
        Schedule schedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer bernard = SampleInterviewers.getBernard();
        assertFalse(schedule.hasInterviewer(bernard));
    }

    @Test
    public void addInterviewer_oneValidAvailability_true() {
        Schedule scheduleTest = SampleSchedules.getSampleAvailabilityTable();
        Interviewer interviewer = SampleInterviewers.getInterviewerOneValidAvailability();

        // Generate the expected Schedule
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();
        String[] newColumn = new String[]{"Technical - Alice Pauline", "1", "0", "0", "0", "0", "0"};
        addColumn(expectedSchedule, newColumn);

        assertTrue(scheduleTest.addInterviewer(interviewer));
        assertEquals(expectedSchedule, scheduleTest);
    }

    @Test
    public void addInterviewer_multipleValidAvailabilities_true() {
        Schedule scheduleTest = SampleSchedules.getSampleAvailabilityTable();
        Interviewer interviewer = SampleInterviewers.getInterviewerMultipleValidAvailabilities();

        // Generate the expected Schedule
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();
        String[] newColumn = new String[]{"Technical - Alice Pauline", "0", "1", "1", "0", "1", "0"};
        addColumn(expectedSchedule, newColumn);

        assertTrue(scheduleTest.addInterviewer(interviewer));
        assertEquals(expectedSchedule, scheduleTest);
    }

    @Test
    public void addInterviewer_multipleInvalidAvailabilities_false() {
        Schedule scheduleTest = SampleSchedules.getSampleAvailabilityTable();
        Interviewer interviewer = SampleInterviewers.getInterviewerMultipleInvalidAvailabilities();
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();

        assertFalse(scheduleTest.addInterviewer(interviewer));
        assertEquals(expectedSchedule, scheduleTest);
    }

    @Test
    public void addInterviewer_multipleAvailabilitiesSomeInvalid_true() {
        Schedule scheduleTest = SampleSchedules.getSampleAvailabilityTable();
        Interviewer interviewer = SampleInterviewers.getInterviewerMultipleAvailabilitiesSomeInvalid();

        // Generate the expected Schedule
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();
        String[] newColumn = new String[]{"Technical - Alice Pauline", "0", "0", "1", "0", "1", "0"};
        addColumn(expectedSchedule, newColumn);

        assertTrue(scheduleTest.addInterviewer(interviewer));
        assertEquals(expectedSchedule, scheduleTest);
    }

    /**
     * Add a column into the schedule.
     */
    private void addColumn(Schedule schedule, String[] newColumn) {
        ObservableList<ObservableList<String>> table = schedule.getObservableList();
        int numColumns = table.size();
        IntStream.range(0, numColumns).forEach(i -> {
            ObservableList<String> row = table.get(i);
            row.add(newColumn[i]);
        });
    }

    @Test
    public void equals_differentSchedules_notEqual() {
        Schedule filledSchedule = SampleSchedules.getSampleFilledSchedule();
        Schedule availabilityTable = SampleSchedules.getSampleAvailabilityTable();
        assertNotEquals(filledSchedule, availabilityTable);
    }

    @Test
    public void cloneSchedule_equalTest_equal() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();
        Schedule cloneSchedule = Schedule.cloneSchedule(schedule);
        assertEquals(schedule, cloneSchedule);
    }

    @Test
    public void cloneSchedule_independentTest_notEqual() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();
        Schedule cloneSchedule = Schedule.cloneSchedule(schedule);

        String[] newColumn = new String[]{"Technical - Alice Pauline", "0", "0", "1", "0", "1", "0"};
        addColumn(schedule, newColumn);

        assertNotEquals(schedule, cloneSchedule);
    }

    @Test
    public void toTwoDimensionalObservableList_twoDimensionalLinkedList_success() {
        LinkedList<LinkedList<String>> list = new LinkedList<>();
        IntStream.range(0, 3).forEach(i -> list.add(getRandomList(5)));
        ObservableList<ObservableList<String>> clone = Schedule.toTwoDimensionalObservableList(list);
        assertEquals(list, clone);
    }

    private LinkedList<String> getRandomList(int n) {
        LinkedList<String> list = new LinkedList<>();
        Random rand = new Random();
        IntStream.range(0, n).forEach(i -> list.add(String.valueOf(rand.nextInt(1000))));
        return list;
    }
}
