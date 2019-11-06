package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for HelpCommand.
 */
public class HelpCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalUserState(), new UserPrefs());
        expectedModel = new ModelManager(model.getUserState(), new UserPrefs());
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
            new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false, null);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

}
