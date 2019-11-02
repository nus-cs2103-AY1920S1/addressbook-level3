package dukecooks.logic.commands.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteExerciseCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Exercise exerciseToDelete = model.getFilteredExerciseList().get(TypicalIndexes.INDEX_FIRST_EXERCISE
                .getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS,
                exerciseToDelete);

        ModelManager expectedModel = new ModelManager(model.getExerciseCatalogue(), new UserPrefs());
        expectedModel.deleteExercise(exerciseToDelete);

        CommandTestUtil.assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        CommandTestUtil.assertExerciseCommandFailure(deleteExerciseCommand, model,
                Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);

        Exercise exerciseToDelete = model.getFilteredExerciseList().get(TypicalIndexes.INDEX_FIRST_EXERCISE
                .getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS,
                exerciseToDelete);

        Model expectedModel = new ModelManager(model.getExerciseCatalogue(), new UserPrefs());
        expectedModel.deleteExercise(exerciseToDelete);
        showNoPerson(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExerciseCatalogue().getExerciseList().size());

        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        CommandTestUtil.assertExerciseCommandFailure(deleteExerciseCommand, model,
                Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteExerciseCommand deleteFirstCommand = new DeleteExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE);
        DeleteExerciseCommand deleteSecondCommand = new DeleteExerciseCommand(TypicalIndexes.INDEX_SECOND_EXERCISE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExerciseCommand deleteFirstCommandCopy = new DeleteExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredExerciseList(p -> false);

        assertTrue(model.getFilteredExerciseList().isEmpty());
    }
}
