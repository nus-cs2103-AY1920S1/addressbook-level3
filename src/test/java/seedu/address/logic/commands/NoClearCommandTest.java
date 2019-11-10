package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.NoClearCommand.SHOWING_NO_CLEAR;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class NoClearCommandTest {

    private NoClearCommand noClearCommand = new NoClearCommand();
    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void execute_command_success() {
        CommandResult commandResult = new NoClearCommand().execute(model);
        assertEquals(SHOWING_NO_CLEAR, commandResult.getFeedbackToUser());
    }
}
