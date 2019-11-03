package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CollapseCommand.MESSAGE_COLLAPSE_FOLDER_ACKNOWLEDGEMENT;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.ExpandCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.storage.StorageStub;

public class CollapseCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        int levelsToCollapse = 2;
        CommandResult expectedCommandResult = new ExpandCommandResult(
                String.format(MESSAGE_COLLAPSE_FOLDER_ACKNOWLEDGEMENT, levelsToCollapse), -levelsToCollapse);
        assertCommandSuccess(new CollapseCommand(levelsToCollapse), model, new StorageStub(),
                expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        CollapseCommand collapseOneCommand = new CollapseCommand(1);
        CollapseCommand collapseTwoCommand = new CollapseCommand(2);

        // same object -> returns true
        assertTrue(collapseOneCommand.equals(collapseOneCommand));

        // same values -> returns true
        CollapseCommand collapseOneCommandCopy = new CollapseCommand(1);
        assertTrue(collapseOneCommand.equals(collapseOneCommandCopy));

        // different types -> returns false
        assertFalse(collapseOneCommand.equals(1));

        // null -> returns false
        assertFalse(collapseOneCommand.equals(null));

        // different levelsToCollapse -> returns false
        assertFalse(collapseOneCommand.equals(collapseTwoCommand));
    }
}
