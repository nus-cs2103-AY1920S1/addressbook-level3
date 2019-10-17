package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalIndexes.INDEX_SECOND_ANSWERABLE;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answerable;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Answerable answerableToDelete = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ANSWERABLE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ANSWERABLE_SUCCESS, answerableToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAnswerable(answerableToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnswerableList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        Answerable answerableToDelete = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ANSWERABLE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ANSWERABLE_SUCCESS, answerableToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAnswerable(answerableToDelete);
        showNoAnswerable(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        Index outOfBoundIndex = INDEX_SECOND_ANSWERABLE;
        // ensures that outOfBoundIndex is still in bounds of revision tool list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getAnswerableList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ANSWERABLE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ANSWERABLE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ANSWERABLE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different answerable -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAnswerable(Model model) {
        model.updateFilteredAnswerableList(p -> false);

        assertTrue(model.getFilteredAnswerableList().isEmpty());
    }
}
