package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.Intensity;
import seedu.address.model.exercise.MuscleType;
import seedu.address.model.exercise.MusclesTrained;
import seedu.address.model.exercise.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditExerciseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_PUSHUP = "Pushup";
    public static final String VALID_NAME_SITUP = "Situp";
    public static final MusclesTrained VALID_MUSCLES_TRAINED = new MusclesTrained(new MuscleType("Chest"),
            new ArrayList<MuscleType>());
    public static final Intensity VALID_INTENSITY_NAME = Intensity.MEDIUM;
    public static final Integer VALID_REPS_SIXTY = 60;
    public static final Integer VALID_SETS_FIVE = 5;


    public static final String NAME_DESC_PUSHUP = " " + PREFIX_NAME + VALID_NAME_PUSHUP;
    public static final String NAME_DESC_SITUP = " " + PREFIX_NAME + VALID_NAME_SITUP;
    public static final String SETS_DESC_FIVE = " " + PREFIX_REPETITIONS + VALID_SETS_FIVE;
    public static final String REPS_DESC_SIXTY = " " + PREFIX_REPETITIONS + VALID_REPS_SIXTY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Pushup&"; // '&' not allowed in names
    public static final String INVALID_SETS_DESC = " " + PREFIX_SETS + "5*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditExerciseCommand.EditExerciseDescriptor DESC_PUSHUP;
    public static final EditExerciseCommand.EditExerciseDescriptor DESC_SITUP;

    static {
        DESC_PUSHUP = new EditExerciseDescriptorBuilder().withName(VALID_NAME_PUSHUP)
                .withDetails(null, null, null, null, null,
                        VALID_SETS_FIVE).build();
        DESC_SITUP = new EditExerciseDescriptorBuilder().withName(VALID_NAME_SITUP)
                .withDetails(null, null, null, null, VALID_SETS_FIVE,
                        VALID_REPS_SIXTY).build();
    }

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
     * - Duke Cooks, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WorkoutPlanner expectedDukeCooks = new WorkoutPlanner(actualModel.getDukeCooks());
        List<Exercise> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExerciseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDukeCooks, actualModel.getDukeCooks());
        assertEquals(expectedFilteredList, actualModel.getFilteredExerciseList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showExerciseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExerciseList().size());

        Exercise exercise = model.getFilteredExerciseList().get(targetIndex.getZeroBased());
        final String[] splitName = exercise.getExerciseName().exerciseName.split("\\s+");
        model.updateFilteredExerciseList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExerciseList().size());
    }

}
