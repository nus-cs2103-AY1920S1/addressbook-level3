package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.commands.CommandTestUtil.CALORIES_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.CALORIES_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.DATE_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.DATE_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_CALORIES_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_MUSCLE_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.INVALID_UNIT_DESC;
import static seedu.exercise.logic.commands.CommandTestUtil.MUSCLE_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.MUSCLE_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.NAME_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.NAME_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.exercise.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.exercise.logic.commands.CommandTestUtil.QUANTITY_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.QUANTITY_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.UNIT_DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.UNIT_DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.TypicalExercises.AEROBICS;
import static seedu.exercise.testutil.TypicalExercises.BASKETBALL;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.exercise.Unit;
import seedu.exercise.model.tag.Muscle;
import seedu.exercise.testutil.ExerciseBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exercise expectedExercise = new ExerciseBuilder(BASKETBALL).withMuscles(VALID_MUSCLE_BASKETBALL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BASKETBALL
            + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
            + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AEROBICS + NAME_DESC_BASKETBALL
            + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
            + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + DATE_DESC_AEROBICS
            + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
            + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple calories - last calories accepted
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_AEROBICS + CALORIES_DESC_BASKETBALL
            + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_AEROBICS
            + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple quantities - last unit accepted
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + UNIT_DESC_AEROBICS + UNIT_DESC_BASKETBALL + MUSCLE_DESC_BASKETBALL,
            new AddCommand(expectedExercise));

        // multiple muscles - all accepted
        Exercise expectedExerciseMultipleTags = new ExerciseBuilder(BASKETBALL)
            .withMuscles(VALID_MUSCLE_BASKETBALL, VALID_MUSCLE_AEROBICS).build();
        assertParseSuccess(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL, new AddCommand(expectedExerciseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Exercise expectedExercise = new ExerciseBuilder(AEROBICS).withMuscles().build();
        assertParseSuccess(parser, NAME_DESC_AEROBICS + DATE_DESC_AEROBICS
            + CALORIES_DESC_AEROBICS + QUANTITY_DESC_AEROBICS + UNIT_DESC_AEROBICS, new AddCommand(expectedExercise));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BASKETBALL + VALID_DATE_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // missing calories prefix
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + VALID_CALORIES_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // missing qauntity prefix
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + VALID_QUANTITY_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BASKETBALL + VALID_DATE_BASKETBALL
            + VALID_CALORIES_BASKETBALL + VALID_QUANTITY_BASKETBALL + UNIT_DESC_BASKETBALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BASKETBALL + INVALID_DATE_DESC
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Date.MESSAGE_CONSTRAINTS);

        // invalid calories
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + INVALID_CALORIES_DESC + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Calories.MESSAGE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + INVALID_QUANTITY_DESC
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + UNIT_DESC_BASKETBALL, Quantity.MESSAGE_CONSTRAINTS);

        // invalid unit
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + INVALID_UNIT_DESC, Unit.MESSAGE_CONSTRAINTS);

        // invalid muscle
        assertParseFailure(parser, NAME_DESC_BASKETBALL + DATE_DESC_BASKETBALL
            + CALORIES_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL
            + INVALID_MUSCLE_DESC + VALID_MUSCLE_BASKETBALL, Muscle.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BASKETBALL
                + CALORIES_DESC_BASKETBALL + INVALID_QUANTITY_DESC + UNIT_DESC_BASKETBALL,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BASKETBALL
                + DATE_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL
                + QUANTITY_DESC_BASKETBALL + UNIT_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
