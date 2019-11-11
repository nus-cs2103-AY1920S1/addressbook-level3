package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_THIRD_IN_LIST;
import static seedu.tarence.testutil.TypicalModules.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.builder.TutorialBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DisplayAssignmentListCommand.
 */
public class DisplayAssignmentListCommandTest {
    private Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalApplication(), new UserPrefs());

    @Test
    public void execute_displayAssignmentsIsNotFiltered_showsSameList() {
        model.addTutorial(new TutorialBuilder().build());
        expectedModel = new ModelManager(model.getApplication(), new UserPrefs());
        assertCommandSuccess(new DisplayAssignmentListCommand(INDEX_FIRST_IN_LIST), model,
                DisplayAssignmentListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DisplayAssignmentListCommand displayAssignmentListCommand =
                new DisplayAssignmentListCommand(INDEX_FIRST_IN_LIST);

        // Same object, returns true
        assertTrue(displayAssignmentListCommand.equals(displayAssignmentListCommand));

        // Same values returns true
        DisplayAssignmentListCommand sameDisplayAssignmentListCommand =
                new DisplayAssignmentListCommand(INDEX_FIRST_IN_LIST);
        assertTrue(displayAssignmentListCommand.equals(sameDisplayAssignmentListCommand));

        // different types -> returns false
        assertFalse(displayAssignmentListCommand.equals(1));

        // null -> returns false
        assertFalse(displayAssignmentListCommand.equals(null));

        // different index -> returns false
        DisplayAssignmentListCommand thirdDisplayAssignmentListCommand =
                new DisplayAssignmentListCommand(INDEX_THIRD_IN_LIST);
        assertFalse(displayAssignmentListCommand.equals(thirdDisplayAssignmentListCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTutorialList().size() + 1);
        DisplayAssignmentListCommand displayAssignmentListCommand = new DisplayAssignmentListCommand(outOfBoundIndex);

        assertCommandFailure(displayAssignmentListCommand, model, MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
    }
}
