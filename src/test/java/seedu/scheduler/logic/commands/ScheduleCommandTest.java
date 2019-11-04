package seedu.scheduler.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.testutil.SampleInterviewee;
import seedu.scheduler.testutil.SampleInterviewer;


class ScheduleCommandTest {
    @Test
    public void execute_sampleGraphData1() {
        Model model = new ModelManager();
        model.setIntervieweeList(SampleInterviewee.getSampleIntervieweesForGraph1());
        model.setInterviewerList(SampleInterviewer.getSampleInterviewersForGraph1());

        ScheduleCommand scheduleCommand = new ScheduleCommand();
        assertDoesNotThrow(()-> scheduleCommand.execute(model));
        assertTrue(model.isScheduled());
    }
}
