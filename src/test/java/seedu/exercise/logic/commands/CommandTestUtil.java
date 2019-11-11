package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - All resources in the model are expected to be unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ReadOnlyResourceBook<Exercise> expectedExerciseBook =
            new ReadOnlyResourceBook<>(actualModel.getExerciseBookData(), DEFAULT_EXERCISE_COMPARATOR);
        List<Exercise> expectedFilteredList = new ArrayList<>(actualModel.getSortedExerciseList());

        ReadOnlyResourceBook<Regime> expectedRegimeBook =
                new ReadOnlyResourceBook<>(actualModel.getAllRegimeData(), DEFAULT_REGIME_COMPARATOR);
        List<Regime> expectedRegimeList = new ArrayList<>(actualModel.getSortedRegimeList());

        ReadOnlyResourceBook<Schedule> expectedScheduleBook =
                new ReadOnlyResourceBook<>(actualModel.getAllScheduleData(), DEFAULT_SCHEDULE_COMPARATOR);
        List<Schedule> expectedScheduleList = new ArrayList<>(actualModel.getSortedScheduleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExerciseBook, actualModel.getExerciseBookData());
        assertEquals(expectedFilteredList, actualModel.getSortedExerciseList());

        assertEquals(expectedRegimeBook, actualModel.getAllRegimeData());
        assertEquals(expectedRegimeList, actualModel.getSortedRegimeList());

        assertEquals(expectedScheduleBook, actualModel.getAllScheduleData());
        assertEquals(expectedScheduleList, actualModel.getSortedScheduleList());
    }
}
