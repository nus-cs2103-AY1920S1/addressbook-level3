package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.CommonTestData.CALORIES_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.CALORIES_DESC_BASKETBALL;
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
import static seedu.exercise.testutil.CommonTestData.QUANTITY_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.QUANTITY_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.UNIT_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX_2;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX_3;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_UNIT_AEROBICS;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_THIRD;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_MUSCLE;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AEROBICS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_PREFIX_INDEX, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AEROBICS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AEROBICS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 3/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " i/1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " i/1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, " i/1" + INVALID_CALORIES_DESC, Calories.MESSAGE_CONSTRAINTS); // invalid calories
        assertParseFailure(parser, " i/1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, " i/1" + INVALID_UNIT_DESC, Unit.MESSAGE_CONSTRAINTS); // invalid unit
        assertParseFailure(parser, " i/1" + INVALID_MUSCLE_DESC, Muscle.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid date followed by valid calories
        assertParseFailure(parser, " i/1" + INVALID_DATE_DESC + CALORIES_DESC_AEROBICS,
            Date.MESSAGE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " i/1" + DATE_DESC_BASKETBALL + INVALID_DATE_DESC,
            Date.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_MUSCLE} alone will reset the muscles of the {@code Exercise} being edited,
        // parsing it together with a valid muscle results in error
        assertParseFailure(parser, " i/1" + MUSCLE_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS + TAG_EMPTY,
            Muscle.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " i/1" + MUSCLE_DESC_BASKETBALL + TAG_EMPTY + MUSCLE_DESC_AEROBICS,
            Muscle.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " i/1" + TAG_EMPTY + MUSCLE_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS,
            Muscle.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " i/1" + INVALID_NAME_DESC + INVALID_CALORIES_DESC
            + VALID_QUANTITY_AEROBICS + VALID_DATE_AEROBICS, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_ONE_BASED_SECOND;
        String userInput = VALID_PREFIX_INDEX_2 + DATE_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS + UNIT_DESC_AEROBICS
            + CALORIES_DESC_AEROBICS + QUANTITY_DESC_AEROBICS + NAME_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL;

        EditExerciseBuilder descriptor =
            new EditExerciseDescriptorBuilder().withName(VALID_NAME_AEROBICS)
                .withDate(VALID_DATE_BASKETBALL).withCalories(VALID_CALORIES_AEROBICS)
                .withQuantity(VALID_QUANTITY_AEROBICS).withUnit(VALID_UNIT_AEROBICS)
                .withMuscles(VALID_MUSCLE_AEROBICS, VALID_MUSCLE_BASKETBALL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_ONE_BASED_FIRST;
        String userInput = VALID_PREFIX_INDEX + DATE_DESC_BASKETBALL + CALORIES_DESC_AEROBICS;

        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder().withDate(VALID_DATE_BASKETBALL)
            .withCalories(VALID_CALORIES_AEROBICS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_ONE_BASED_THIRD;
        String userInput = VALID_PREFIX_INDEX_3 + NAME_DESC_AEROBICS;
        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder()
            .withName(VALID_NAME_AEROBICS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = VALID_PREFIX_INDEX_3 + DATE_DESC_AEROBICS;
        descriptor = new EditExerciseDescriptorBuilder().withDate(VALID_DATE_AEROBICS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // calories
        userInput = VALID_PREFIX_INDEX_3 + CALORIES_DESC_AEROBICS;
        descriptor = new EditExerciseDescriptorBuilder().withCalories(VALID_CALORIES_AEROBICS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = VALID_PREFIX_INDEX_3 + QUANTITY_DESC_AEROBICS;
        descriptor = new EditExerciseDescriptorBuilder().withQuantity(VALID_QUANTITY_AEROBICS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // unit
        userInput = VALID_PREFIX_INDEX_3 + UNIT_DESC_AEROBICS;
        descriptor = new EditExerciseDescriptorBuilder().withUnit(VALID_UNIT_AEROBICS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // muscles
        userInput = VALID_PREFIX_INDEX_3 + MUSCLE_DESC_BASKETBALL;
        descriptor = new EditExerciseDescriptorBuilder().withMuscles(VALID_MUSCLE_BASKETBALL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_ONE_BASED_FIRST;
        String userInput = VALID_PREFIX_INDEX + DATE_DESC_AEROBICS + QUANTITY_DESC_AEROBICS
            + CALORIES_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL + DATE_DESC_AEROBICS
            + QUANTITY_DESC_AEROBICS + CALORIES_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL
            + DATE_DESC_BASKETBALL + QUANTITY_DESC_BASKETBALL + CALORIES_DESC_BASKETBALL + MUSCLE_DESC_AEROBICS;

        EditExerciseBuilder descriptor =
            new EditExerciseDescriptorBuilder().withDate(VALID_DATE_BASKETBALL)
                .withCalories(VALID_CALORIES_BASKETBALL).withQuantity(VALID_QUANTITY_BASKETBALL)
                .withMuscles(VALID_MUSCLE_BASKETBALL, VALID_MUSCLE_AEROBICS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_ONE_BASED_FIRST;
        String userInput = VALID_PREFIX_INDEX + INVALID_DATE_DESC + DATE_DESC_BASKETBALL;
        EditExerciseBuilder descriptor =
            new EditExerciseDescriptorBuilder().withDate(VALID_DATE_BASKETBALL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_PREFIX_INDEX + CALORIES_DESC_BASKETBALL + INVALID_DATE_DESC
            + QUANTITY_DESC_BASKETBALL + DATE_DESC_BASKETBALL;
        descriptor = new EditExerciseDescriptorBuilder().withDate(VALID_DATE_BASKETBALL)
            .withCalories(VALID_CALORIES_BASKETBALL).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_ONE_BASED_THIRD;
        String userInput = VALID_PREFIX_INDEX_3 + TAG_EMPTY;

        EditExerciseBuilder descriptor = new EditExerciseDescriptorBuilder().withMuscles().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
