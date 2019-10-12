package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.NameContainsKeywordsPredicate;
import seedu.exercise.testutil.EditExerciseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CATEGORY_EXERCISE = "exercise";
    public static final String VALID_NAME_AEROBICS = "Aerobics";
    public static final String VALID_NAME_BASKETBALL = "Basketball";
    public static final String VALID_DATE_AEROBICS = "26/09/2019";
    public static final String VALID_DATE_BASKETBALL = "27/09/2019";
    public static final String VALID_CALORIES_AEROBICS = "333";
    public static final String VALID_CALORIES_BASKETBALL = "444";
    public static final String VALID_QUANTITY_AEROBICS = "30";
    public static final String VALID_QUANTITY_BASKETBALL = "3";
    public static final String VALID_UNIT_AEROBICS = "counts";
    public static final String VALID_UNIT_BASKETBALL = "hour";
    public static final String VALID_MUSCLE_AEROBICS = "Back";
    public static final String VALID_MUSCLE_BASKETBALL = "Arms";

    public static final String CATEGORY_DESC_EXERCISE = " " + PREFIX_CATEGORY + VALID_CATEGORY_EXERCISE;
    public static final String NAME_DESC_AEROBICS = " " + PREFIX_NAME + VALID_NAME_AEROBICS;
    public static final String NAME_DESC_BASKETBALL = " " + PREFIX_NAME + VALID_NAME_BASKETBALL;
    public static final String DATE_DESC_AEROBICS = " " + PREFIX_DATE + VALID_DATE_AEROBICS;
    public static final String DATE_DESC_BASKETBALL = " " + PREFIX_DATE + VALID_DATE_BASKETBALL;
    public static final String CALORIES_DESC_AEROBICS = " " + PREFIX_CALORIES + VALID_CALORIES_AEROBICS;
    public static final String CALORIES_DESC_BASKETBALL = " " + PREFIX_CALORIES + VALID_CALORIES_BASKETBALL;
    public static final String QUANTITY_DESC_AEROBICS = " " + PREFIX_QUANTITY + VALID_QUANTITY_AEROBICS;
    public static final String QUANTITY_DESC_BASKETBALL = " " + PREFIX_QUANTITY + VALID_QUANTITY_BASKETBALL;
    public static final String UNIT_DESC_AEROBICS = " " + PREFIX_UNIT + VALID_UNIT_AEROBICS;
    public static final String UNIT_DESC_BASKETBALL = " " + PREFIX_UNIT + VALID_UNIT_BASKETBALL;
    public static final String MUSCLE_DESC_AEROBICS = " " + PREFIX_MUSCLE + VALID_MUSCLE_AEROBICS;
    public static final String MUSCLE_DESC_BASKETBALL = " " + PREFIX_MUSCLE + VALID_MUSCLE_BASKETBALL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Dance&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "31a/10/2019"; // 'a' not allowed in date
    public static final String INVALID_CALORIES_DESC = " " + PREFIX_CALORIES + "33a"; // 'a' not allowed in calories
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "22a"; // 'a' not allowed in quantity
    public static final String INVALID_UNIT_DESC = " " + PREFIX_UNIT; // empty string not allowed in unit
    public static final String INVALID_MUSCLE_DESC = " " + PREFIX_MUSCLE + "Chest*"; // '*' not allowed in muscle

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditExerciseDescriptor DESC_AEROBICS;
    public static final EditCommand.EditExerciseDescriptor DESC_BASKETBALL;

    static {
        DESC_AEROBICS = new EditExerciseDescriptorBuilder().withName(VALID_NAME_AEROBICS)
            .withDate(VALID_DATE_AEROBICS).withCalories(VALID_CALORIES_AEROBICS)
            .withQuantity(VALID_QUANTITY_AEROBICS).withMuscles(VALID_MUSCLE_BASKETBALL).build();
        DESC_BASKETBALL = new EditExerciseDescriptorBuilder().withName(VALID_NAME_BASKETBALL)
            .withDate(VALID_DATE_BASKETBALL).withCalories(VALID_CALORIES_BASKETBALL)
            .withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS, VALID_MUSCLE_BASKETBALL).build();
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
     * - the exercise book, filtered exercise list and selected exercise in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ExerciseBook expectedExerciseBook = new ExerciseBook(actualModel.getAllExerciseData());
        List<Exercise> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExerciseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExerciseBook, actualModel.getAllExerciseData());
        assertEquals(expectedFilteredList, actualModel.getFilteredExerciseList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the exercise at the given {@code targetIndex} in the
     * {@code model}'s exercise book.
     */
    public static void showExerciseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExerciseList().size());

        Exercise exercise = model.getFilteredExerciseList().get(targetIndex.getZeroBased());
        final String[] splitName = exercise.getName().fullName.split("\\s+");
        model.updateFilteredExerciseList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExerciseList().size());
    }
}
