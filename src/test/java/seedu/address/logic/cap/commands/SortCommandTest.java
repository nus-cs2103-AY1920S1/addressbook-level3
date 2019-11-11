package seedu.address.logic.cap.commands;

import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;

/**
 * Encapsulates a test for sort command.
 */
public class SortCommandTest {

    @Test
    public void execute_emptyCapLog_success() {
        Model model = new ModelCapManager();
        Model expectedModel = new ModelCapManager();

        assertCommandSuccess(new SortCommand(), model, SortCommand.MESSAGE_SORT_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCapLog_success() {
        Model model = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());
        Model expectedModel = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());

        assertCommandSuccess(new SortCommand(), model, SortCommand.MESSAGE_SORT_SUCCESS, expectedModel);
    }
}
