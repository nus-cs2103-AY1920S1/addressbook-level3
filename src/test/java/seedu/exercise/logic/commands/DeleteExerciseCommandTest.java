package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteExerciseCommand}.
 */
public class DeleteExerciseCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
        new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Exercise exerciseToDelete = model.getFilteredExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete);

        ModelManager expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());
        expectedModel.deleteExercise(exerciseToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(deleteExerciseCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Exercise exerciseToDelete = model.getFilteredExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        DeleteExerciseCommand deleteExerciseCommand = new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST);

        String expectedMessage = String.format(DeleteExerciseCommand.MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete);

        Model expectedModel = new ModelManager(model.getExerciseBookData(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs());
        expectedModel.deleteExercise(exerciseToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(deleteExerciseCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        DeleteExerciseCommand deleteFirstCommand = new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST);
        DeleteExerciseCommand deleteSecondCommand = new DeleteExerciseCommand(INDEX_ONE_BASED_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExerciseCommand deleteFirstCommandCopy = new DeleteExerciseCommand(INDEX_ONE_BASED_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different exercise -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
