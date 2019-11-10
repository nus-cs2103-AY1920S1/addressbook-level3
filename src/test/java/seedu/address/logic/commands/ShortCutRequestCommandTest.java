package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ShortCutRequestCommand.SHOWING_SHORTCUT_MESSAGE;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;
import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShortCutRequestCommandTest {

    private ShortCutRequestCommand shortCutRequestCommand = new ShortCutRequestCommand("exit");
    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void isValid() {
        CommandResult commandResult = shortCutRequestCommand.execute(model);
        assertEquals("exit" + SHOWING_SHORTCUT_MESSAGE, commandResult.getFeedbackToUser());
    }


}
