package dukecooks.logic.parser.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.PUSHUP;
import static dukecooks.testutil.exercise.TypicalExercises.SITUP;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.exercise.AddExerciseCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.testutil.exercise.ExerciseBuilder;

public class AddExerciseCommandParserTest {
    private AddExerciseCommandParser parser = new AddExerciseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exercise expectedExercise = new ExerciseBuilder(SITUP)
                .withDetails(null, null, null, null, null, CommandTestUtil.VALID_SETS_FIVE)
                .withEmptyHistory()
                .build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_SITUP + CommandTestUtil.MUSCLE_DESC_ABS
                + CommandTestUtil.INTENSITY_DESC_MEDIUM + CommandTestUtil.SETS_DESC_FIVE,
                new AddExerciseCommand(expectedExercise));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_PUSHUP
                + CommandTestUtil.NAME_DESC_SITUP + CommandTestUtil.MUSCLE_DESC_ABS
                + CommandTestUtil.INTENSITY_DESC_MEDIUM + CommandTestUtil.SETS_DESC_FIVE,
                new AddExerciseCommand(expectedExercise));

        // multiple tags - all accepted
        Exercise expectedExerciseMultipleTags = new ExerciseBuilder(SITUP)
                .withMusclesTrained("Abs")
                .withIntensity(Intensity.MEDIUM)
                .withDetails(null, null, null, null,
                        CommandTestUtil.VALID_REPS_SIXTY, CommandTestUtil.VALID_SETS_FIVE)
                .withEmptyHistory()
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_SITUP
                + CommandTestUtil.MUSCLE_DESC_ABS + CommandTestUtil.INTENSITY_DESC_MEDIUM
                + CommandTestUtil.REPS_DESC_SIXTY + CommandTestUtil.SETS_DESC_FIVE,
                new AddExerciseCommand(expectedExerciseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Exercise expectedExercise = new ExerciseBuilder(PUSHUP)
                .withDetails(null, null, null, null, null, null)
                .withEmptyHistory()
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_PUSHUP
                        + CommandTestUtil.MUSCLE_DESC_CHEST + CommandTestUtil.INTENSITY_DESC_HIGH,
                new AddExerciseCommand(expectedExercise));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddExerciseCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_SITUP,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_SITUP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.MUSCLE_DESC_CHEST + CommandTestUtil.INTENSITY_DESC_MEDIUM
                + CommandTestUtil.REPS_DESC_SIXTY + CommandTestUtil.SETS_DESC_FIVE, ExerciseName.MESSAGE_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_SITUP
                + CommandTestUtil.MUSCLE_DESC_ABS + CommandTestUtil.INTENSITY_DESC_MEDIUM
                + CommandTestUtil.INVALID_SETS_DESC + CommandTestUtil.VALID_SETS_FIVE,
                ExerciseDetail.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                        + CommandTestUtil.MUSCLE_DESC_ABS + CommandTestUtil.INTENSITY_DESC_MEDIUM,
                ExerciseName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_SITUP + CommandTestUtil.MUSCLE_DESC_ABS
                        + CommandTestUtil.INTENSITY_DESC_MEDIUM + CommandTestUtil.REPS_DESC_SIXTY
                        + CommandTestUtil.SETS_DESC_FIVE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE));
    }
}
