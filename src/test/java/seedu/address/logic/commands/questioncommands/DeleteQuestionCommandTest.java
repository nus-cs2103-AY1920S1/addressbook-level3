package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.Question;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteQuestionCommand}.
 */
class DeleteQuestionCommandTest {

    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Question questionToDelete = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        DeleteQuestionCommand deleteQuestionCommand = new DeleteQuestionCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteQuestionCommand.MESSAGE_DELETE_QUESTION_SUCCESS, questionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.deleteQuestion(questionToDelete);

        assertCommandSuccess(deleteQuestionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        DeleteQuestionCommand deleteQuestionCommand = new DeleteQuestionCommand(outOfBoundIndex);

        assertCommandFailure(deleteQuestionCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showQuestionAtIndex(model, INDEX_FIRST);

        Question questionToDelete = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        DeleteQuestionCommand deleteQuestionCommand = new DeleteQuestionCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteQuestionCommand.MESSAGE_DELETE_QUESTION_SUCCESS, questionToDelete);

        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.deleteQuestion(questionToDelete);
        showNoQuestion(expectedModel);

        assertCommandSuccess(deleteQuestionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showQuestionAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of app data list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppData().getQuestionList().size());

        DeleteQuestionCommand deleteQuestionCommand = new DeleteQuestionCommand(outOfBoundIndex);

        assertCommandFailure(deleteQuestionCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteQuestionCommand deleteFirstCommand = new DeleteQuestionCommand(INDEX_FIRST);
        DeleteQuestionCommand deleteSecondCommand = new DeleteQuestionCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteQuestionCommand deleteFirstCommandCopy = new DeleteQuestionCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different question -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoQuestion(Model model) {
        model.updateFilteredQuestionList(p -> false);

        assertTrue(model.getFilteredQuestionList().isEmpty());
    }
}
