package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.testutil.CommonTestData.DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.EditCommand.EditExerciseDescriptor;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), new ReadOnlyResourceBook<>(),
        new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new UserPrefs(), getDefaultPropertyBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(editedExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(model.getExerciseBookData()),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new UserPrefs(), getDefaultPropertyBook());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
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

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(model.getExerciseBookData()),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new UserPrefs(), getDefaultPropertyBook());
        expectedModel.setExercise(lastExercise, editedExercise);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST, new EditCommand.EditExerciseDescriptor());
        Exercise editedExercise = model.getFilteredExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(model.getExerciseBookData()),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new UserPrefs(), getDefaultPropertyBook());

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showExerciseAtIndex(model, INDEX_ONE_BASED_FIRST);

        Exercise exerciseInFilteredList = model.getFilteredExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        Exercise editedExercise =
            new ExerciseBuilder(exerciseInFilteredList).withName(VALID_NAME_BASKETBALL).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST,
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(model.getExerciseBookData()),
            new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new UserPrefs(), getDefaultPropertyBook());
        expectedModel.setExercise(model.getFilteredExerciseList().get(0), editedExercise);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        Exercise firstExercise = model.getFilteredExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        EditCommand.EditExerciseDescriptor descriptor = new EditExerciseDescriptorBuilder(firstExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_duplicateExerciseFilteredList_failure() {
        showExerciseAtIndex(model, INDEX_ONE_BASED_FIRST);

        // edit exercise in filtered list into a duplicate in exercise book
        Exercise exerciseInList = model.getExerciseBookData().getResourceList()
            .get(INDEX_ONE_BASED_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST,
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
        showExerciseAtIndex(model, INDEX_ONE_BASED_FIRST);
        Index outOfBoundIndex = INDEX_ONE_BASED_SECOND;
        // ensures that outOfBoundIndex is still in bounds of exercise book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExerciseBookData().getResourceList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_ONE_BASED_FIRST, DESC_AEROBICS);

        // same values -> returns true
        EditExerciseDescriptor copyDescriptor = new EditExerciseDescriptor(DESC_AEROBICS);
        EditCommand commandWithSameValues = new EditCommand(INDEX_ONE_BASED_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_ONE_BASED_SECOND, DESC_AEROBICS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_ONE_BASED_FIRST, DESC_BASKETBALL)));
    }

}
