package seedu.address.logic.cap.commands;

import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;


public class ClearCommandTest {

    @Test
    public void execute_emptyCapLog_success() {
        Model model = new ModelCapManager();
        Model expectedModel = new ModelCapManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCapLog_success() {
        Model model = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());
        Model expectedModel = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());
        expectedModel.setCapLog(new CapLog());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
