package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.CommonTestData.DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());

    @BeforeEach
    public void reset() {
        PropertyBook.getInstance().clearCustomProperties();
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Exercise editedExercise = new ExerciseBuilder().build();
        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder(editedExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());

        expectedModel.setExercise(expectedModel.getSortedExerciseList().get(0), editedExercise);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExercise = Index.fromOneBased(model.getSortedExerciseList().size());
        Exercise lastExercise = model.getSortedExerciseList().get(indexLastExercise.getZeroBased());

        ExerciseBuilder exerciseInList = new ExerciseBuilder(lastExercise);
        Exercise editedExercise = exerciseInList.withName(VALID_NAME_BASKETBALL).withDate(VALID_DATE_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();

        EditExerciseBuilder descriptor =
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL)
                .withDate(VALID_DATE_BASKETBALL).withMuscles(VALID_MUSCLE_AEROBICS).build();
        EditCommand editCommand = new EditCommand(indexLastExercise, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());
        expectedModel.setExercise(lastExercise, editedExercise);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST, new EditExerciseBuilder());
        Exercise editedExercise = model.getSortedExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

        Exercise exerciseInFilteredList = model.getSortedExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        Exercise editedExercise =
            new ExerciseBuilder(exerciseInFilteredList).withName(VALID_NAME_BASKETBALL).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST,
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise);

        Model expectedModel = new ModelManager(
                new ReadOnlyResourceBook<>(model.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR),
                 new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());
        expectedModel.setExercise(model.getSortedExerciseList().get(0), editedExercise);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.EXERCISE);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateExerciseUnfilteredList_failure() {
        Exercise firstExercise = model.getSortedExerciseList().get(INDEX_ONE_BASED_FIRST.getZeroBased());
        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder(firstExercise).build();
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_duplicateExerciseFilteredList_failure() {

        // edit exercise in filtered list into a duplicate in exercise book
        Exercise exerciseInList = model.getExerciseBookData().getSortedResourceList()
            .get(INDEX_ONE_BASED_SECOND.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_ONE_BASED_FIRST,
            new EditExerciseDescriptorBuilder(exerciseInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

    @Test
    public void execute_invalidExerciseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedExerciseList().size() + 1);
        EditExerciseBuilder descriptor =
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_ONE_BASED_FIRST, DESC_AEROBICS);

        // same values -> returns true
        EditExerciseBuilder copyDescriptor = new EditExerciseBuilder(DESC_AEROBICS);
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
