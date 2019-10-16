package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SETS_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.address.testutil.TypicalExercises.getTypicalWorkoutPlanner;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.exercise.Exercise;
import seedu.address.testutil.EditExerciseDescriptorBuilder;
import seedu.address.testutil.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditExerciseCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(editedExercise).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE, descriptor);

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new WorkoutPlanner(model.getWorkoutPlanner()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExercise = Index.fromOneBased(model.getFilteredExerciseList().size());
        Exercise lastExercise = model.getFilteredExerciseList().get(indexLastExercise.getZeroBased());

        ExerciseBuilder exerciseInList = new ExerciseBuilder(lastExercise);
        Exercise editedExercise = exerciseInList.withName(VALID_NAME_SITUP)
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(VALID_NAME_SITUP)
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(indexLastExercise, descriptor);

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new WorkoutPlanner(model.getWorkoutPlanner()), new UserPrefs());
        expectedModel.setExercise(lastExercise, editedExercise);

        assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE,
                new EditExerciseCommand.EditExerciseDescriptor());
        Exercise editedExercise = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new WorkoutPlanner(model.getWorkoutPlanner()), new UserPrefs());

        assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        Exercise exerciseInFilteredList = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        Exercise editedExercise = new ExerciseBuilder(exerciseInFilteredList).withName(VALID_NAME_SITUP).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE,
                new EditExerciseDescriptorBuilder().withName(VALID_NAME_SITUP).build());

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new WorkoutPlanner(model.getWorkoutPlanner()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        Exercise firstExercise = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(firstExercise)
                .build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_SECOND_EXERCISE, descriptor);

        assertCommandFailure(editExerciseCommand, model, EditExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_duplicateExerciseFilteredList_failure() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        // edit exercise in filtered list into a duplicate in Duke Cooks
        Exercise exerciseInList = model.getWorkoutPlanner().getExerciseList().get(INDEX_SECOND_EXERCISE.getZeroBased());
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE,
                new EditExerciseDescriptorBuilder(exerciseInList).build());

        assertCommandFailure(editExerciseCommand, model, EditExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_invalidExerciseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(VALID_NAME_SITUP).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Duke Cooks
     */
    @Test
    public void execute_invalidExerciseIndexFilteredList_failure() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);
        Index outOfBoundIndex = INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutPlanner().getExerciseList().size());

        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(outOfBoundIndex,
                new EditExerciseDescriptorBuilder().withName(VALID_NAME_SITUP).build());

        assertCommandFailure(editExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditExerciseCommand standardCommand = new EditExerciseCommand(INDEX_FIRST_EXERCISE, DESC_PUSHUP);

        // same values -> returns true
        EditExerciseCommand.EditExerciseDescriptor copyDescriptor = new EditExerciseCommand
                .EditExerciseDescriptor(DESC_PUSHUP);
        EditExerciseCommand commandWithSameValues = new EditExerciseCommand(INDEX_FIRST_EXERCISE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearExerciseCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(INDEX_SECOND_EXERCISE, DESC_PUSHUP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(INDEX_FIRST_EXERCISE, DESC_SITUP)));
    }

}
