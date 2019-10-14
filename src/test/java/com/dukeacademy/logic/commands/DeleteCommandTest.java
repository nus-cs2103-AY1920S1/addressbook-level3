package com.dukeacademy.logic.commands;

import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.dukeacademy.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static com.dukeacademy.testutil.TypicalIndexes.INDEX_FIRST_QUESTION;
import static com.dukeacademy.testutil.TypicalIndexes.INDEX_SECOND_QUESTION;
import static com.dukeacademy.testutil.TypicalQuestions.getTypicalQuestionBank;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.ModelManager;
import com.dukeacademy.model.UserPrefs;
import com.dukeacademy.model.question.Question;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalQuestionBank(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Question questionToDelete =
            model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QUESTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUESTION_SUCCESS,
            questionToDelete);

        ModelManager expectedModel = new ModelManager(model.getQuestionBank(), new UserPrefs());
        expectedModel.deleteQuestion(questionToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        Question questionToDelete = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_QUESTION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_QUESTION_SUCCESS,
            questionToDelete);

        Model expectedModel = new ModelManager(model.getQuestionBank(), new UserPrefs());
        expectedModel.deleteQuestion(questionToDelete);
        showNoQuestion(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        Index outOfBoundIndex = INDEX_SECOND_QUESTION;
        // ensures that outOfBoundIndex is still in bounds of question bank list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getQuestionBank().getQuestionList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_QUESTION);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_QUESTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_QUESTION);
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
