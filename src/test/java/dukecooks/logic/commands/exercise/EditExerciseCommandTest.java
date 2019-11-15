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
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.exercise.EditExerciseDescriptorBuilder;
import dukecooks.testutil.exercise.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditExerciseCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutPlanner(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(editedExercise)
                .build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                descriptor);

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseCatalogue(model.getExerciseCatalogue()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        CommandTestUtil.assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExercise = Index.fromOneBased(model.getFilteredExerciseList().size());
        Exercise lastExercise = model.getFilteredExerciseList().get(indexLastExercise.getZeroBased());

        ExerciseBuilder exerciseInList = new ExerciseBuilder(lastExercise);
        Exercise editedExercise = exerciseInList.withName(CommandTestUtil.VALID_NAME_SITUP)
                .withDetails(null, null, null, null, null,
                        CommandTestUtil.VALID_SETS_FIVE)
                .build();

        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_SITUP)
                .withDetails(null, null, null, null, null,
                        CommandTestUtil.VALID_SETS_FIVE)
                .build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(indexLastExercise, descriptor);

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseCatalogue(model.getExerciseCatalogue()), new UserPrefs());
        expectedModel.setExercise(lastExercise, editedExercise);

        CommandTestUtil.assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                new EditExerciseCommand.EditExerciseDescriptor());
        Exercise editedExercise = model.getFilteredExerciseList().get(TypicalIndexes.INDEX_FIRST_EXERCISE
                .getZeroBased());

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseCatalogue(model.getExerciseCatalogue()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);

        Exercise exerciseInFilteredList = model.getFilteredExerciseList().get(TypicalIndexes.INDEX_FIRST_EXERCISE
                .getZeroBased());
        Exercise editedExercise = new ExerciseBuilder(exerciseInFilteredList).withName(CommandTestUtil
                .VALID_NAME_SITUP).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                new EditExerciseDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_SITUP).build());

        String expectedMessage = String.format(EditExerciseCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseCatalogue(model.getExerciseCatalogue()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        CommandTestUtil.assertCommandSuccess(editExerciseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        Exercise firstExercise = model.getFilteredExerciseList().get(TypicalIndexes.INDEX_FIRST_EXERCISE
                .getZeroBased());
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(firstExercise)
                .build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(TypicalIndexes.INDEX_SECOND_EXERCISE,
                descriptor);

        CommandTestUtil.assertExerciseCommandFailure(editExerciseCommand, model,
                EditExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_duplicateExerciseFilteredList_failure() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);

        // edit exercise in filtered list into a duplicate in Duke Cooks
        Exercise exerciseInList = model.getExerciseCatalogue().getExerciseList().get(TypicalIndexes
                .INDEX_SECOND_EXERCISE.getZeroBased());
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                new EditExerciseDescriptorBuilder(exerciseInList).build());

        CommandTestUtil.assertExerciseCommandFailure(editExerciseCommand, model,
                EditExerciseCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_invalidExerciseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        EditExerciseCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_SITUP).build();
        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertExerciseCommandFailure(editExerciseCommand, model,
                Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Duke Cooks
     */
    @Test
    public void execute_invalidExerciseIndexFilteredList_failure() {
        CommandTestUtil.showExerciseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXERCISE);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of Duke Cooks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExerciseCatalogue().getExerciseList().size());

        EditExerciseCommand editExerciseCommand = new EditExerciseCommand(outOfBoundIndex,
                new EditExerciseDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_SITUP).build());

        CommandTestUtil.assertExerciseCommandFailure(editExerciseCommand, model,
                Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditExerciseCommand standardCommand = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                CommandTestUtil.DESC_PUSHUP);

        // same values -> returns true
        EditExerciseCommand.EditExerciseDescriptor copyDescriptor = new EditExerciseCommand
                .EditExerciseDescriptor(CommandTestUtil.DESC_PUSHUP);
        EditExerciseCommand commandWithSameValues = new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearExerciseCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(TypicalIndexes.INDEX_SECOND_EXERCISE,
                CommandTestUtil.DESC_PUSHUP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExerciseCommand(TypicalIndexes.INDEX_FIRST_EXERCISE,
                CommandTestUtil.DESC_SITUP)));
    }

}
