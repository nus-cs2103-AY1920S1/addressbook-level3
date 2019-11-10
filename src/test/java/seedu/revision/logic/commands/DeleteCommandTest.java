package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalIndexes.INDEX_SECOND_ANSWERABLE;
import static seedu.revision.testutil.TypicalMcqs.getTypicalRevisionTool;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.main.DeleteCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.History;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answerable;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalRevisionTool(), new UserPrefs(), new History());

    @Test
    public void execute_validIndexUnfilteredList_success() throws ParseException {
        ArrayList<Answerable> answerablesToDelete = new ArrayList<>();
        answerablesToDelete.add(model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased()));
        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(INDEX_FIRST_ANSWERABLE);
        DeleteCommand deleteCommand = new DeleteCommand(indexToDelete);

        String expectedMessage =
                String.format(DeleteCommand.MESSAGE_DELETE_ANSWERABLE_SUCCESS, answerablesToDelete.toString());

        ModelManager expectedModel = new ModelManager(model.getRevisionTool(), new UserPrefs(), new History());
        expectedModel.deleteAnswerable(model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased()));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnswerableList().size() + 1);
        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }
    /*
    @Test
    public void execute_validIndexFilteredList_success() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);
        Answerable answerableToDelete = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(INDEX_FIRST_ANSWERABLE);
        DeleteCommand deleteCommand = new DeleteCommand(indexToDelete);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ANSWERABLE_SUCCESS, answerableToDelete);
        Model expectedModel = new ModelManager(model.getRevisionTool(), new UserPrefs());
        expectedModel.deleteAnswerable(answerableToDelete);
        showNoAnswerable(expectedModel);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        Index outOfBoundIndex = INDEX_SECOND_ANSWERABLE;
        // ensures that outOfBoundIndex is still in bounds of revision tool list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRevisionTool().getAnswerableList().size());

        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(indexToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }
    /*
    @Test
    public void equals() {
        ArrayList<Index> firstDelete = new ArrayList<>();
        ArrayList<Index> secondDelete = new ArrayList<>();
        firstDelete.add(INDEX_FIRST_ANSWERABLE);
        secondDelete.add(INDEX_SECOND_ANSWERABLE);
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstDelete);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondDelete);
        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstDelete);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        // different answerable -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
    */
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAnswerable(Model model) {
        model.updateFilteredAnswerableList(p -> false);

        assertTrue(model.getFilteredAnswerableList().isEmpty());
    }
}
