package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalDukeCooks;

import org.junit.jupiter.api.Test;

import seedu.address.model.DukeCooks;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyDukeCooks_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDukeCooks_success() {
        Model model = new ModelManager(getTypicalDukeCooks(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalDukeCooks(), new UserPrefs());
        expectedModel.setDukeCooks(new DukeCooks());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
