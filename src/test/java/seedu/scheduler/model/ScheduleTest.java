package seedu.scheduler.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.scheduler.commons.exceptions.ScheduleException;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.IntervieweeSlot;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.testutil.SampleInterviewee;
import seedu.scheduler.testutil.SampleInterviewer;
import seedu.scheduler.testutil.SampleSchedules;

public class ScheduleTest {
    @Test
    public void getInterviewsSlot_existingInterviewee_success() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();

        List<Slot> johnSlots = new LinkedList<>();
        johnSlots.add(new Slot("10/09/2019", "18:00", "18:30"));
        johnSlots.add(new Slot("10/09/2019", "18:30", "19:00"));

        List<Slot> selinaSlots = new LinkedList<>();
        selinaSlots.add(new Slot("10/09/2019", "19:30", "20:00"));
        selinaSlots.add(new Slot("10/09/2019", "20:00", "20:30"));

        assertEquals(johnSlots, schedule.getInterviewSlots("John"));
        assertEquals(selinaSlots, schedule.getInterviewSlots("Selina"));
    }

    @Test
    public void getInterviewsSlots_nonExistingInterviewee_emptyList() {
        Schedule schedule = SampleSchedules.getSampleFilledSchedule();
        assertEquals(new LinkedList<>(), schedule.getInterviewSlots("ABC"));
    }

    @Test
    public void addAllocatedInterviewees_validAllocatedSlots_success() {
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();
        expectedSchedule.getObservableList().get(1).set(2, "Bernard");
        expectedSchedule.getObservableList().get(2).set(2, "Jessie");

        Schedule subjectSchedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer johnathan = SampleInterviewer.getInterviewer("Johnathan", "Technical");
        Interviewee interviewee1 = SampleInterviewee.getSampleIntervieweesForGraph1().get(0);
        Interviewee interviewee2 = SampleInterviewee.getSampleIntervieweesForGraph1().get(1);

        // The slots and the interviewer falls in the schedule, and the slot is available for interview,
        // i.e. the slot is "1".
        johnathan.addAllocatedSlot(new IntervieweeSlot(interviewee1, Slot.fromString("10/09/2019 18:30-19:00")));
        johnathan.addAllocatedSlot(new IntervieweeSlot(interviewee2, Slot.fromString("10/09/2019 19:00-19:30")));

        assertDoesNotThrow(() -> subjectSchedule.addAllocatedInterviewees(johnathan, johnathan.getAllocatedSlots()));
        assertEquals(subjectSchedule, expectedSchedule);
    }

    @Test
    public void addAllocatedSlot_slotNotInSchedule_notAdded() {
        Schedule expectedSchedule = SampleSchedules.getSampleAvailabilityTable();
        Schedule subjectSchedule = SampleSchedules.getSampleAvailabilityTable();
        List<Interviewee> interviewees = SampleInterviewee.getSampleIntervieweesForGraph1();

        // Interviewer not in schedule
        Interviewer ben = SampleInterviewer.getInterviewer("Ben", "Presidential");
        ben.addAllocatedSlot(new IntervieweeSlot(interviewees.get(0), Slot.fromString("10/09/2019 18:30-19:00")));
        assertDoesNotThrow(() -> subjectSchedule.addAllocatedInterviewees(ben, ben.getAllocatedSlots()));
        assertEquals(subjectSchedule, expectedSchedule);

        // Slot's date is not the same as schedule
        Interviewer johnathan = SampleInterviewer.getInterviewer("Johnathan", "Technical");
        johnathan.addAllocatedSlot(new IntervieweeSlot(interviewees.get(0),
                Slot.fromString("25/10/2019 18:30-19:00")));
        assertDoesNotThrow(() -> subjectSchedule.addAllocatedInterviewees(johnathan, johnathan.getAllocatedSlots()));
        assertEquals(subjectSchedule, expectedSchedule);

        // Slot's timing is not in the schedule
        Interviewer james = SampleInterviewer.getInterviewer("James", "Logistics");
        james.addAllocatedSlot(new IntervieweeSlot(interviewees.get(0),
                Slot.fromString("10/09/2019 01:00-02:00")));
        assertDoesNotThrow(() -> subjectSchedule.addAllocatedInterviewees(james, james.getAllocatedSlots()));
        assertEquals(subjectSchedule, expectedSchedule);
    }

    @Test
    public void addAllocatedSlot_invalidAllocatedSlots_throwScheduleException() {
        Schedule subjectSchedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer johnathan = SampleInterviewer.getInterviewer("Johnathan", "Technical");
        Interviewee interviewee1 = SampleInterviewee.getSampleIntervieweesForGraph1().get(0);

        // The slot in which the interviewee to be added is not available for interview, i.e. is "0" instead of "1".
        johnathan.addAllocatedSlot(new IntervieweeSlot(interviewee1, Slot.fromString("10/09/2019 20:30-21:00")));
        assertThrows(ScheduleException.class, () ->
                subjectSchedule.addAllocatedInterviewees(johnathan, johnathan.getAllocatedSlots()));
    }

    @Test
    public void hasInterviewer_existingInterviewer_true() {
        Schedule schedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer hazel = SampleInterviewer.getHazel();
        assertTrue(schedule.hasInterviewer(hazel));
    }

    @Test
    public void hasInterviewer_nonExistingInterviewer_false() {
        Schedule schedule = SampleSchedules.getSampleAvailabilityTable();
        Interviewer bernard = SampleInterviewer.getBernard();
        assertFalse(schedule.hasInterviewer(bernard));
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

        String newTitle = "Technical - Alice Pauline";
        String[] newColumn = new String[]{"0", "0", "1", "0", "1", "0"};
        addColumn(schedule, newTitle, newColumn);

        assertNotEquals(schedule, cloneSchedule);
        assertNotEquals(schedule.getObservableList(), cloneSchedule.getObservableList());
        assertNotEquals(schedule.getTitles(), cloneSchedule.getTitles());
    }

    /**
     * Add a column into the schedule.
     */
    private void addColumn(Schedule schedule, String newTitle, String[] newColumn) {
        ObservableList<String> titles = schedule.getTitles();
        titles.add(newTitle);

        ObservableList<ObservableList<String>> data = schedule.getObservableList();
        int numRows = data.size();
        IntStream.range(0, numRows).forEach(i -> {
            ObservableList<String> row = data.get(i);
            row.add(newColumn[i]);
        });
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
