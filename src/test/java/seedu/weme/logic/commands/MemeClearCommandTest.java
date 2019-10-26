package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.Weme;

public class MemeClearCommandTest {

    @Test
    public void execute_emptyWeme_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitWeme();

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyWeme_success() {
        Model model = new ModelManager(getTypicalWeme(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel.setWeme(new Weme());
        expectedModel.commitWeme();

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
