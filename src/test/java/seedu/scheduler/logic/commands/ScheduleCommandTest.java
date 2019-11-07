package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import javafx.collections.ObservableList;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.model.person.Role;

class ScheduleCommandTest {
    private static final String INTERVIEWER = "interviewer";
    private static final String INTERVIEWEE = "interviewee";

    // ========================================= Hybrid Testing =================================================
    @Test
    public void execute_allAllocated() {
        Model model = importData("src/test/data/ScheduleTest/InterviewerTestData_1.csv",
                "src/test/data/ScheduleTest/Interviewee_AllAllocated.csv");
        CommandResult result = executeScheduleCommand(model);

        // Test if message returned is correct
        String expectedFeedback = "Successfully scheduled!\nAll interviewees are allocated with a slot!";
        assertEquals(expectedFeedback, result.getFeedbackToUser());

        // Test if underlying schedule structure is correct
        String[] slotJohnDoeDetails = getSlotDescription("John Doe", model.getSchedulesList().get(0))
                .get(0).split(",");
        String[] slotJohnTohDetails = getSlotDescription("John Toh", model.getSchedulesList().get(2))
                .get(0).split(",");

        assertEquals("Department A", slotJohnDoeDetails[0]);
        assertEquals("09/10/2019", slotJohnDoeDetails[1].trim());
        assertEquals("18:30-19:00", slotJohnDoeDetails[2].trim());

        assertEquals("Department B", slotJohnTohDetails[0]);
        assertEquals("11/10/2019", slotJohnTohDetails[1].trim());
        assertEquals("20:00-20:30", slotJohnTohDetails[2].trim());
    }

    // ========================================= Helper methods =================================================
    /**
     * Import the interviewer availability and interviewee details accordingly, and execute the schedule command.
     */
    private Model importData(String interviewerFilePath, String intervieweeFilePath) {
        FilePath interviewerAvailability = new FilePath(interviewerFilePath);
        FilePath intervieweeDetails = new FilePath(intervieweeFilePath);

        Model model = new ModelManager();

        ImportCommand importInterviewer = new ImportCommand(new Role(INTERVIEWER), interviewerAvailability);
        ImportCommand importInterviewee = new ImportCommand(new Role(INTERVIEWEE), intervieweeDetails);
        assertDoesNotThrow(() -> importInterviewer.execute(model));
        assertDoesNotThrow(() -> importInterviewee.execute(model));
        assertDoesNotThrow(() -> model.setEmptyScheduleList());

        return model;
    }

    /**
     * Executes the schedule command using the given model.
     */
    private CommandResult executeScheduleCommand(Model model) {
        // Check state
        assertFalse(model.isScheduled());

        // Execute command
        ScheduleCommand scheduleCommand = new ScheduleCommand();
        ThrowingSupplier<CommandResult> supplier = () -> scheduleCommand.execute(model);
        CommandResult result = assertDoesNotThrow(supplier);

        assertTrue(model.isScheduled());
        return result;
    }

    /**
     * Returns a list of strings describing the slot allocated to the interviewee in the schedule. Each string is in
     * the format of "[Department], [Date], [Timing]". If the interviewee is not present in the schedule, then
     * an empty string is returned.
     */
    private List<String> getSlotDescription(String intervieweeName, Schedule schedule) {
        List<String> descriptions = new ArrayList<>();
        ObservableList<ObservableList<String>> data = schedule.getObservableList();
        int numRows = data.size();

        for (int i = 0; i < numRows; i++) {
            ObservableList<String> row = data.get(i);
            int numColumns = row.size();

            for (int j = 0; j < numColumns; j++) {
                if (row.get(j).equals(intervieweeName)) {
                    String department = schedule.getTitles().get(j).split("-")[0].trim();
                    String date = schedule.getDate();
                    String timing = row.get(0);

                    String description = String.format("%s, %s, %s", department, date, timing);
                    descriptions.add(description);
                }
            }
        }

        return descriptions;
    }
}
