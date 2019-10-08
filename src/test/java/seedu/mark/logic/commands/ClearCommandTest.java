package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.Test;

import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyMark_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitMark();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMark_success() {
        Model model = new ModelManager(getTypicalMark(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
        expectedModel.setMark(new Mark());
        expectedModel.commitMark();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
