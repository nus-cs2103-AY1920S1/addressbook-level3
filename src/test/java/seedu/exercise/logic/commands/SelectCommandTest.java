package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.PREDICATE_SHOW_ALL_EXERCISES;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.TestUtil.assertCommonEqualsTest;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_VERY_LARGE_NUMBER;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.getTypicalRegimeBook;
import static seedu.exercise.testutil.typicalutil.TypicalSchedule.getTypicalScheduleBook;
import static seedu.exercise.ui.ListResourceType.EXERCISE;
import static seedu.exercise.ui.ListResourceType.REGIME;
import static seedu.exercise.ui.ListResourceType.SCHEDULE;
import static seedu.exercise.ui.ListResourceType.SUGGESTION;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.ui.ListResourceType;

//@@author weihaw08
public class SelectCommandTest {

    private Model model = new ModelManager(getTypicalExerciseBook(), getTypicalRegimeBook(), getTypicalExerciseBook(),
        getTypicalScheduleBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalExerciseBook(), getTypicalRegimeBook(),
        getTypicalExerciseBook(), getTypicalScheduleBook(), new UserPrefs());

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SelectCommand(null, EXERCISE));
        assertThrows(NullPointerException.class, () -> new SelectCommand(INDEX_ONE_BASED_FIRST, null));
    }

    @Test
    public void execute_nullArgument_throwsNullPointerException() {
        SelectCommand selectCommand = new SelectCommand(INDEX_ONE_BASED_FIRST, EXERCISE);
        assertThrows(NullPointerException.class, () -> selectCommand.execute(null));
    }

    @Test
    public void execute_indexInRange_success() {
        // Selecting exercise
        SelectCommand selectExerciseCommand = new SelectCommand(INDEX_ONE_BASED_FIRST, EXERCISE);
        CommandResult expectedResult = createdExpectedCommandResult(EXERCISE);
        assertCommandSuccess(selectExerciseCommand, model, expectedResult, expectedModel);

        // Selecting regime
        SelectCommand selectRegimeCommand = new SelectCommand(INDEX_ONE_BASED_FIRST, REGIME);
        expectedResult = createdExpectedCommandResult(REGIME);
        assertCommandSuccess(selectRegimeCommand, model, expectedResult, expectedModel);

        // Selecting schedule
        SelectCommand selectScheduleCommand = new SelectCommand(INDEX_ONE_BASED_FIRST, SCHEDULE);
        expectedResult = createdExpectedCommandResult(SCHEDULE);
        assertCommandSuccess(selectScheduleCommand, model, expectedResult, expectedModel);

        // Selecting suggestion
        model.updateSuggestedExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        expectedModel.updateSuggestedExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        SelectCommand selectSuggestionCommand = new SelectCommand(INDEX_ONE_BASED_FIRST, SUGGESTION);
        expectedResult = createdExpectedCommandResult(SUGGESTION);
        assertCommandSuccess(selectSuggestionCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_indexOutOfRange_failure() {
        SelectCommand selectExerciseCommand = new SelectCommand(INDEX_VERY_LARGE_NUMBER, EXERCISE);
        assertCommandFailure(selectExerciseCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);

        SelectCommand selectRegimeCommand = new SelectCommand(INDEX_VERY_LARGE_NUMBER, REGIME);
        assertCommandFailure(selectRegimeCommand, model, Messages.MESSAGE_INVALID_REGIME_DISPLAYED_INDEX);

        SelectCommand selectScheduleCommand = new SelectCommand(INDEX_VERY_LARGE_NUMBER, SCHEDULE);
        assertCommandFailure(selectScheduleCommand, model, Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);

        SelectCommand selectSuggestionCommand = new SelectCommand(INDEX_VERY_LARGE_NUMBER, SUGGESTION);
        assertCommandFailure(selectSuggestionCommand, model, Messages.MESSAGE_INVALID_SUGGESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectExerciseCommand = new SelectCommand(INDEX_ONE_BASED_SECOND, EXERCISE);
        SelectCommand selectExerciseCommandSameIndex = new SelectCommand(INDEX_ONE_BASED_SECOND, EXERCISE);
        SelectCommand selectRegimeCommandSameIndex = new SelectCommand(INDEX_ONE_BASED_SECOND, REGIME);
        SelectCommand selectExerciseCommandDiffIndex = new SelectCommand(INDEX_VERY_LARGE_NUMBER, EXERCISE);
        SelectCommand selectRegimeCommandDiffIndex = new SelectCommand(INDEX_VERY_LARGE_NUMBER, REGIME);

        // selectExerciseCommand == this -> true and selectExerciseCommand == null -> false
        assertCommonEqualsTest(selectExerciseCommand);

        // same values -> true
        assertTrue(selectExerciseCommand.equals(selectExerciseCommandSameIndex));

        // same index, diff list type -> false
        assertFalse(selectExerciseCommand.equals(selectRegimeCommandSameIndex));

        // diff index, same list type -> false
        assertFalse(selectExerciseCommand.equals(selectExerciseCommandDiffIndex));

        // diff index, diff list type -> false
        assertFalse(selectExerciseCommand.equals(selectRegimeCommandDiffIndex));

        // diff object -> false
        assertFalse(selectExerciseCommand.equals(5.00));
    }

    /**
     * A utility method that helps to generate the correct {@code CommandResult} based on the {@code resourceType}.
     */
    private CommandResult createdExpectedCommandResult(ListResourceType resourceType) {
        int selectedIndex = INDEX_ONE_BASED_FIRST.getOneBased();
        String selectedResource = resourceType.toString().toLowerCase();
        String expectedMessage = String.format(SelectCommand.MESSAGE_SUCCESS, selectedResource, selectedIndex);
        return new CommandResult(expectedMessage, resourceType, Optional.of(INDEX_ONE_BASED_FIRST));
    }
}
