package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import org.junit.jupiter.api.Test;

import seedu.address.model.AppData;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearNoteCommandTest {

    @Test
    public void execute_emptyAppData_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearNoteCommand(), model, ClearNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAppData_success() {
        Model model = new ModelManager(getTypicalAppData(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAppData(), new UserPrefs());
        expectedModel.setAppData(new AppData());

        assertCommandSuccess(new ClearNoteCommand(), model, ClearNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
