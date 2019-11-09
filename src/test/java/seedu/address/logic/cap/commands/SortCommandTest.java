package seedu.address.logic.cap.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import java.util.*;

import org.junit.jupiter.api.Test;
import seedu.address.logic.cap.commands.exceptions.*;
import seedu.address.model.cap.*;
import seedu.address.model.common.Module;
import seedu.address.testutil.*;

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
