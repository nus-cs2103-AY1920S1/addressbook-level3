package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.commandresult.ExitCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.storage.StorageStub;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new ExitCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        assertCommandSuccess(new ExitCommand(), model, new StorageStub(),
                expectedCommandResult, expectedModel);
    }
}
