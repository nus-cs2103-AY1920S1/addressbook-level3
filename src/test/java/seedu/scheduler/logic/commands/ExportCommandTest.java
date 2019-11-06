package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;


public class ExportCommandTest {
    public static final String DESTINATION_FILE = "src/test/data/ImportsTest/storage.csv";
    private static Model model = new ModelManager();

    @Test
    public void execute_exportCommand_success() {
        ExportCommand exportCommand = new ExportCommand(new FilePath(DESTINATION_FILE));
        CommandResult expectedCommandResult = new CommandResult(ExportCommand.SUCCESS_MESSAGE, false, false);
        model.setScheduled(true);
        assertCommandSuccess(exportCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_exportCommand_throwsCommandException() {
        ExportCommand exportCommand = new ExportCommand(new FilePath(DESTINATION_FILE));
        model.setScheduled(false);
        assertCommandFailure(exportCommand, model, ExportCommand.NOT_SCHEDULED_ERROR);
    }
}
