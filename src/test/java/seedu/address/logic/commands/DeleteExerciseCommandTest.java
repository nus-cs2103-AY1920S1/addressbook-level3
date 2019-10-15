package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.address.testutil.TypicalExercises.getTypicalDukeCooks;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.WorkoutPlannerUserPrefs;
import seedu.address.model.exercise.Exercise;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteExerciseCommandTest {

    private Model model = new ModelManager(getTypicalDukeCooks(), new WorkoutPlannerUserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Exercise exerciseToDelete = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_FIRST_EXERCISE);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete);

        ModelManager expectedModel = new ModelManager(model.getDukeCooks(), new WorkoutPlannerUserPrefs());
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

        Model expectedModel = new ModelManager(model.getDukeCooks(), new WorkoutPlannerUserPrefs());
        expectedModel.deleteExercise(exerciseToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        Index outOfBoundIndex = INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDukeCooks().getExerciseList().size());

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
