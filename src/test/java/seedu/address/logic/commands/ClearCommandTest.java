package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Notebook;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {


    @Test
    public void execute_emptyClassroom_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void execute_nonEmptyClassroom_success() {
        Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalNotebook(), new UserPrefs());
        expectedModel.setNotebook(new Notebook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
