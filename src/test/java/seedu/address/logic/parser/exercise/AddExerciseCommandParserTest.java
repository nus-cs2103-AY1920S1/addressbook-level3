package seedu.address.logic.parser.exercise;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INTENSITY_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.INTENSITY_DESC_MEDIUM;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOOD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SETS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_ABS;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_CHEST;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REPS_DESC_SIXTY;
import static seedu.address.logic.commands.CommandTestUtil.SETS_DESC_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPS_SIXTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SETS_FIVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.exercise.TypicalExercises.PUSHUP;
import static seedu.address.testutil.exercise.TypicalExercises.SITUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exercise.AddExerciseCommand;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.ExerciseName;
import seedu.address.model.exercise.components.Intensity;
import seedu.address.model.exercise.details.ExerciseDetail;
import seedu.address.testutil.exercise.ExerciseBuilder;

public class AddExerciseCommandParserTest {
    private AddExerciseCommandParser parser = new AddExerciseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exercise expectedExercise = new ExerciseBuilder(SITUP)
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SITUP + MUSCLE_DESC_ABS
                + INTENSITY_DESC_MEDIUM + SETS_DESC_FIVE, new AddExerciseCommand(expectedExercise));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_PUSHUP + NAME_DESC_SITUP + MUSCLE_DESC_ABS
                + INTENSITY_DESC_MEDIUM + SETS_DESC_FIVE, new AddExerciseCommand(expectedExercise));

        // multiple tags - all accepted
        Exercise expectedExerciseMultipleTags = new ExerciseBuilder(SITUP)
                .withMusclesTrained("Abs")
                .withIntensity(Intensity.MEDIUM)
                .withDetails(null, null, null, null,
                        VALID_REPS_SIXTY, VALID_SETS_FIVE)
                .build();
        assertParseSuccess(parser, NAME_DESC_SITUP + MUSCLE_DESC_ABS + INTENSITY_DESC_MEDIUM
                + REPS_DESC_SIXTY + SETS_DESC_FIVE, new AddExerciseCommand(expectedExerciseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Exercise expectedExercise = new ExerciseBuilder(PUSHUP)
                .withDetails(null, null, null, null, null, null)
                .build();
        assertParseSuccess(parser, NAME_DESC_PUSHUP + MUSCLE_DESC_CHEST + INTENSITY_DESC_HIGH,
                new AddExerciseCommand(expectedExercise));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_SITUP,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_SITUP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_FOOD_NAME_DESC + MUSCLE_DESC_CHEST + INTENSITY_DESC_MEDIUM
                + REPS_DESC_SIXTY + SETS_DESC_FIVE, ExerciseName.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_SITUP + MUSCLE_DESC_ABS + INTENSITY_DESC_MEDIUM
                + INVALID_SETS_DESC + VALID_SETS_FIVE, ExerciseDetail.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_FOOD_NAME_DESC + MUSCLE_DESC_ABS + INTENSITY_DESC_MEDIUM,
                ExerciseName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_SITUP + MUSCLE_DESC_ABS
                        + INTENSITY_DESC_MEDIUM + REPS_DESC_SIXTY + SETS_DESC_FIVE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE));
    }
}
