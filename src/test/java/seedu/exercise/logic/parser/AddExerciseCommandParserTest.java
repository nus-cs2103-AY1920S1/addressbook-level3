package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.CommonTestData.CALORIES_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.CALORIES_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.CATEGORY_DESC_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.DATE_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.DATE_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.INVALID_CALORIES_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_DATE_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_MUSCLE_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_NAME_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_QUANTITY_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_UNIT_DESC;
import static seedu.exercise.testutil.CommonTestData.MUSCLE_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.MUSCLE_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.NAME_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.NAME_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.QUANTITY_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.QUANTITY_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.UNIT_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.UNIT_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_UNIT_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.AEROBICS;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;

public class AddExerciseCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exercise expectedExercise = new ExerciseBuilder(BASKETBALL).withMuscles(VALID_MUSCLE_BASKETBALL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL
                + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple names - last name accepted
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_AEROBICS + NAME_DESC_BASKETBALL
                + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple dates - last date accepted
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_AEROBICS
                + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple calories - last calories accepted
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
                + CALORIES_DESC_AEROBICS + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
                + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_AEROBICS
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple units - last unit accepted
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
                + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
                + UNIT_DESC_AEROBICS + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddExerciseCommand(expectedExercise));

        // multiple muscles - all accepted
        Exercise expectedExerciseMultipleTags = new ExerciseBuilder(BASKETBALL)
            .withMuscles(VALID_MUSCLE_BASKETBALL, VALID_MUSCLE_AEROBICS).build();
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL, new AddExerciseCommand(expectedExerciseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Exercise expectedExercise = new ExerciseBuilder(AEROBICS).withMuscles().build();
        assertParseSuccess(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_AEROBICS + DATE_DESC_AEROBICS
                + CALORIES_DESC_AEROBICS + QUANTITY_DESC_AEROBICS + UNIT_DESC_AEROBICS,
            new AddExerciseCommand(expectedExercise));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BASKETBALL + CATEGORY_DESC_EXERCISE + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + VALID_DATE_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BASKETBALL + VALID_DATE_BASKETBALL
                + VALID_CALORIES_BASKETBALL + VALID_QUANTITY_BASKETBALL + VALID_UNIT_BASKETBALL
                + CATEGORY_DESC_EXERCISE,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + INVALID_NAME_DESC + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + INVALID_DATE_DESC
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Date.MESSAGE_CONSTRAINTS);

        // invalid calories
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + INVALID_CALORIES_DESC + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Calories.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + INVALID_QUANTITY_DESC
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Quantity.MESSAGE_CONSTRAINTS);

        // invalid unit
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + INVALID_UNIT_DESC, Unit.MESSAGE_CONSTRAINTS);

        // invalid muscle
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL
            + INVALID_MUSCLE_DESC + VALID_MUSCLE_BASKETBALL, Muscle.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, CATEGORY_DESC_EXERCISE + INVALID_NAME_DESC + DATE_DESC_BASKETBALL
                + CALORIES_DESC_BASKETBALL + INVALID_QUANTITY_DESC + UNIT_DESC_BASKETBALL,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CATEGORY_DESC_EXERCISE + NAME_DESC_BASKETBALL
                + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExerciseCommand.MESSAGE_USAGE));
    }
}
