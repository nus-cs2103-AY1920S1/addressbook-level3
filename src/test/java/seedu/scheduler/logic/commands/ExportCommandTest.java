package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.SampleDataUtil;
import seedu.scheduler.model.Schedule;


public class ExportCommandTest {
    public static final String DESTINATION_FILE = "src/test/data/ImportsTest/storage.csv";
    private static Model model = new ModelManager();

    @Test
    public void execute_exportCommand_success() {
        ExportCommand exportCommand = new ExportCommand(new FilePath(DESTINATION_FILE));
        Model expectedModel = new ModelManager();
        expectedModel.setSchedulesList(SampleDataUtil.getSampleSchedulesList());
        CommandResult expectedCommandResult = new CommandResult(ExportCommand.SUCCESS_MESSAGE, false, false);
        expectedModel.setScheduled(true);
        assertCommandSuccess(exportCommand, expectedModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exportCommand_throwsCommandException() {
        //Empty schedules
        ExportCommand exportCommand = new ExportCommand(new FilePath(DESTINATION_FILE));
        model.setScheduled(true);
        assertCommandFailure(exportCommand, model, ExportCommand.EMPTY_SCHEDULES_ERROR);
        //Not scheduled
        model.setScheduled(false);
        List<Schedule> schedules = SampleDataUtil.getSampleSchedulesList();
        model.setSchedulesList(schedules);
        assertCommandFailure(exportCommand, model, ExportCommand.NOT_SCHEDULED_ERROR);
    }
}
