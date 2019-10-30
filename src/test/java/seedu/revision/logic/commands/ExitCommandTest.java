package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.main.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() throws ParseException {
        CommandResult expectedCommandResult = new CommandResult().withFeedBack(MESSAGE_EXIT_ACKNOWLEDGEMENT)
                .withHelp(false).withExit(true).build();
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
