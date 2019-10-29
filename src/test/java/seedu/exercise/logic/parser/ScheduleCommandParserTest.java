package seedu.exercise.logic.parser;

import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_DATE_ALPHABETS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_DATE_DAY;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_DATE_SYMBOLS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_DATE_WRONG_FORMAT;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_ALPHABETS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_EMPTY;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_NEGATIVE;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_NOT_ENGLISH;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_SYMBOLS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_ZERO;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_NAME_NOT_ENGLISH;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_NAME_SYMBOLS;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_CARDIO;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_DATE_2;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX_2;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_CARDIO;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_LEGS;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.ScheduleCompleteCommand;
import seedu.exercise.logic.commands.ScheduleRegimeCommand;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.testutil.typicalutil.TypicalIndexes;

/**
 * Unit tests for parsing both {@code ScheduleRegimeCommand} and {@code ScheduleCompleteCommand}.
 */
public class ScheduleCommandParserTest {


    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresentForScheduleComplete_success() {
        Index index = TypicalIndexes.INDEX_ONE_BASED_FIRST;

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VALID_PREFIX_INDEX, new ScheduleCompleteCommand(index));

        // multiple indexes - last index accepted
        assertParseSuccess(parser,
                VALID_PREFIX_INDEX_2 + VALID_PREFIX_INDEX, new ScheduleCompleteCommand(index));
    }

    @Test
    public void parse_allFieldsPresentForScheduleRegime_success() {
        Name regimeName = new Name(VALID_NAME_CARDIO);
        Date expectedDate = new Date(VALID_DATE);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_PREFIX_NAME_CARDIO + VALID_PREFIX_DATE,
                new ScheduleRegimeCommand(regimeName, expectedDate));

        // multiple name - last name accepted
        assertParseSuccess(parser, VALID_PREFIX_NAME_LEGS + VALID_PREFIX_NAME_CARDIO + VALID_PREFIX_DATE,
                new ScheduleRegimeCommand(regimeName, expectedDate));

        // multiple dates - last date accepted
        assertParseSuccess(parser, VALID_PREFIX_DATE_2 + VALID_PREFIX_DATE + VALID_PREFIX_NAME_CARDIO,
                new ScheduleRegimeCommand(regimeName, expectedDate));
    }

    @Test
    public void parse_compulsoryFieldMissingForScheduleComplete_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCompleteCommand.MESSAGE_USAGE);

        //missing indexes prefix
        assertParseFailure(parser, VALID_INDEX, expectedMessage);

    }

    @Test
    public void parse_compulsoryFieldMissingForScheduleRegime_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleRegimeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CARDIO + VALID_PREFIX_DATE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE + VALID_PREFIX_NAME_CARDIO, expectedMessage);

        // all prefix missing
        assertParseFailure(parser, VALID_DATE + VALID_NAME_CARDIO, expectedMessage);
    }

    @Test
    public void parse_invalidValueForScheduleComplete_failure() {
        String expectedIndexInvalidMessage = Index.MESSAGE_CONSTRAINTS;

        //Invalid index types
        assertParseFailure(parser, INVALID_PREFIX_INDEX_ZERO, expectedIndexInvalidMessage);
        assertParseFailure(parser, INVALID_PREFIX_INDEX_ALPHABETS, expectedIndexInvalidMessage);
        assertParseFailure(parser, INVALID_PREFIX_INDEX_EMPTY, expectedIndexInvalidMessage);
        assertParseFailure(parser, INVALID_PREFIX_INDEX_NEGATIVE, expectedIndexInvalidMessage);
        assertParseFailure(parser, INVALID_PREFIX_INDEX_NOT_ENGLISH, expectedIndexInvalidMessage);
        assertParseFailure(parser, INVALID_PREFIX_INDEX_SYMBOLS, expectedIndexInvalidMessage);

        //non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_PREFIX_INDEX,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCompleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValueForScheduleRegime_failure() {
        // invalid names
        assertParseFailure(parser, INVALID_PREFIX_NAME_NOT_ENGLISH + VALID_PREFIX_DATE,
                Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_PREFIX_NAME_SYMBOLS + VALID_PREFIX_DATE,
                Name.MESSAGE_CONSTRAINTS);

        // invalid dates
        assertParseFailure(parser, INVALID_PREFIX_DATE_ALPHABETS + VALID_PREFIX_NAME_CARDIO,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_PREFIX_DATE_DAY + VALID_PREFIX_NAME_CARDIO,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_PREFIX_DATE_WRONG_FORMAT + VALID_PREFIX_NAME_CARDIO,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_PREFIX_DATE_SYMBOLS + VALID_PREFIX_NAME_CARDIO,
                Date.MESSAGE_CONSTRAINTS);

        // non-empty preambles
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_PREFIX_NAME_CARDIO + VALID_PREFIX_DATE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScheduleRegimeCommand.MESSAGE_USAGE));
    }
}
