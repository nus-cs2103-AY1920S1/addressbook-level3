package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.NoShortCutCommand.SHOWING_NOSHORTCUT_MESSAGE;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class NoShortCutCommandTest {

    private NoShortCutCommand noShortcutCommand = new NoShortCutCommand();
    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void isValid() {
        CommandResult commandResult = new NoShortCutCommand().execute(model);
        assertEquals(SHOWING_NOSHORTCUT_MESSAGE, commandResult.getFeedbackToUser());
    }

}
