package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_SECOND_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.EditCommand.EditExerciseDescriptor;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.testutil.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(editedExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXERCISE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseBook(model.getAllData()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExercise = Index.fromOneBased(model.getFilteredExerciseList().size());
        Exercise lastExercise = model.getFilteredExerciseList().get(indexLastExercise.getZeroBased());

        ExerciseBuilder exerciseInList = new ExerciseBuilder(lastExercise);
        Exercise editedExercise = exerciseInList.withName(VALID_NAME_BASKETBALL).withDate(VALID_DATE_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();

        EditCommand.EditExerciseDescriptor descriptor =
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL)
                .withDate(VALID_DATE_BASKETBALL).withMuscles(VALID_MUSCLE_AEROBICS).build();
        EditCommand editCommand = new EditCommand(indexLastExercise, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseBook(model.getAllData()), new UserPrefs());
        expectedModel.setExercise(lastExercise, editedExercise);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXERCISE, new EditCommand.EditExerciseDescriptor());
        Exercise editedExercise = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseBook(model.getAllData()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        Exercise exerciseInFilteredList = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        Exercise editedExercise =
            new ExerciseBuilder(exerciseInFilteredList).withName(VALID_NAME_BASKETBALL).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXERCISE,
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ExerciseBook(model.getAllData()), new UserPrefs());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        Exercise firstExercise = model.getFilteredExerciseList().get(INDEX_FIRST_EXERCISE.getZeroBased());
        EditCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(firstExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_EXERCISE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_duplicateExerciseFilteredList_failure() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);

        // edit exercise in filtered list into a duplicate in exercise book
        Exercise exerciseInList = model.getAllData().getExerciseList().get(INDEX_SECOND_EXERCISE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_EXERCISE,
            new EditExerciseDescriptorBuilder(exerciseInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_invalidExerciseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExerciseList().size() + 1);
        EditExerciseDescriptor descriptor =
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of exercise book
     */
    @Test
    public void execute_invalidExerciseIndexFilteredList_failure() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);
        Index outOfBoundIndex = INDEX_SECOND_EXERCISE;
        // ensures that outOfBoundIndex is still in bounds of exercise book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAllData().getExerciseList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_EXERCISE, DESC_AEROBICS);

        // same values -> returns true
        EditExerciseDescriptor copyDescriptor = new EditExerciseDescriptor(DESC_AEROBICS);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_EXERCISE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_EXERCISE, DESC_AEROBICS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_EXERCISE, DESC_BASKETBALL)));
    }

}
