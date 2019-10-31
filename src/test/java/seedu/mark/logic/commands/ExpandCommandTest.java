package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.ExpandCommand.MESSAGE_EXPAND_FOLDER_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.ExpandCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.storage.StorageStub;

public class ExpandCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        int levelsToExpand = 2;
        CommandResult expectedCommandResult = new ExpandCommandResult(
                String.format(MESSAGE_EXPAND_FOLDER_ACKNOWLEDGEMENT, levelsToExpand), levelsToExpand);
        assertCommandSuccess(new ExpandCommand(levelsToExpand), model, new StorageStub(),
                expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ExpandCommand expandOneCommand = new ExpandCommand(1);
        ExpandCommand expandTwoCommand = new ExpandCommand(2);

        // same object -> returns true
        assertTrue(expandOneCommand.equals(expandOneCommand));

        // same values -> returns true
        ExpandCommand expandOneCommandCopy = new ExpandCommand(1);
        assertTrue(expandOneCommand.equals(expandOneCommandCopy));

        // different types -> returns false
        assertFalse(expandOneCommand.equals(1));

        // null -> returns false
        assertFalse(expandOneCommand.equals(null));

        // different levelsToExpand -> returns false
        assertFalse(expandOneCommand.equals(expandTwoCommand));
    }
}
