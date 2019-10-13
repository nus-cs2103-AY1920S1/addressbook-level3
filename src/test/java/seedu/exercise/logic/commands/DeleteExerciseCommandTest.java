package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.exercise.model.util.DefaultPropertyManagerUtil.getDefaultPropertyManager;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.RegimeBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.exercise.Exercise;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteExerciseCommand}.
 */
public class DeleteExerciseCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), new RegimeBook(),
        new UserPrefs(), getDefaultPropertyManager());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Exercise exerciseToDelete = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete);

        ModelManager expectedModel = new ModelManager(model.getAllExerciseData(), new RegimeBook(),
            new UserPrefs(), getDefaultPropertyManager());
        expectedModel.deleteExercise(exerciseToDelete);

        assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        Exercise exerciseToDelete = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete);

        Model expectedModel = new ModelManager(model.getAllExerciseData(), new RegimeBook(),
            new UserPrefs(), getDefaultPropertyManager());
        expectedModel.deleteExercise(exerciseToDelete);
        showNoExercise(expectedModel);

        assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        Index outOfBoundIndex = INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of exercise book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAllExerciseData().getExerciseList().size());

        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteExerciseCommand deleteFirstCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);
        DeleteExerciseCommand deleteSecondCommand = new DeleteExerciseCommand(INDEX_SECOND_EXERCISE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExerciseCommand deleteFirstCommandCopy = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different exercise -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoExercise(Model model) {
        model.updateFilteredExerciseList(p -> false);

        assertTrue(model.getFilteredExerciseList().isEmpty());
    }
}
