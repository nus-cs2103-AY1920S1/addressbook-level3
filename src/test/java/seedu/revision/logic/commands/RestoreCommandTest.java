package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.main.RestoreCommand.SHOWING_CONFIRMATION_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.commands.main.RestoreCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;

public class RestoreCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_restore_success() throws ParseException {
        CommandResult expectedCommandResult = new CommandResultBuilder().withFeedBack(SHOWING_CONFIRMATION_MESSAGE)
                .withRestore(true).build();
        assertCommandSuccess(new RestoreCommand(), model, expectedCommandResult, expectedModel);
    }
}
