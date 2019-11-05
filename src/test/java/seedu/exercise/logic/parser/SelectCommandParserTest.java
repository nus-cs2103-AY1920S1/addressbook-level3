package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_ALPHABETS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_NEGATIVE;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_ZERO;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_LIST_TYPE_ADDRESS;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.VALID_CATEGORY_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_LIST_TYPE_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX_2;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_REGIME;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_SCHEDULE;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_FIRST;
import static seedu.exercise.testutil.typicalutil.TypicalIndexes.INDEX_ONE_BASED_SECOND;
import static seedu.exercise.ui.ListResourceType.EXERCISE;
import static seedu.exercise.ui.ListResourceType.LIST_RESOURCE_TYPE_CONSTRAINTS;
import static seedu.exercise.ui.ListResourceType.REGIME;
import static seedu.exercise.ui.ListResourceType.SCHEDULE;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.SelectCommand;

public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_PREFIX_LIST_TYPE_REGIME + VALID_PREFIX_INDEX,
            new SelectCommand(INDEX_ONE_BASED_FIRST, REGIME));

        // multiple list type - last list type accepted
        assertParseSuccess(parser, VALID_PREFIX_LIST_TYPE_REGIME + VALID_PREFIX_INDEX
            + VALID_PREFIX_LIST_TYPE_EXERCISE, new SelectCommand(INDEX_ONE_BASED_FIRST, EXERCISE));

        // multiple index - last index accepted
        assertParseSuccess(parser, VALID_PREFIX_LIST_TYPE_REGIME + VALID_PREFIX_INDEX
            + VALID_PREFIX_INDEX_2, new SelectCommand(INDEX_ONE_BASED_SECOND, REGIME));

        // in random order
        assertParseSuccess(parser, VALID_PREFIX_INDEX + VALID_PREFIX_LIST_TYPE_SCHEDULE,
            new SelectCommand(INDEX_ONE_BASED_FIRST, SCHEDULE));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);

        // missing list type prefix
        assertParseFailure(parser, VALID_LIST_TYPE_EXERCISE + VALID_PREFIX_INDEX, expectedMessage);

        // missing index prefix
        assertParseFailure(parser, VALID_PREFIX_LIST_TYPE_EXERCISE + VALID_INDEX, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CATEGORY_EXERCISE + VALID_INDEX, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid list type
        assertParseFailure(parser, INVALID_PREFIX_LIST_TYPE_ADDRESS + VALID_PREFIX_INDEX,
            LIST_RESOURCE_TYPE_CONSTRAINTS);

        // invalid index - alphabets index
        assertParseFailure(parser, VALID_PREFIX_LIST_TYPE_EXERCISE + INVALID_PREFIX_INDEX_ALPHABETS,
            Index.MESSAGE_CONSTRAINTS);

        // invalid index - negative index
        assertParseFailure(parser, VALID_PREFIX_LIST_TYPE_EXERCISE + INVALID_PREFIX_INDEX_NEGATIVE,
            Index.MESSAGE_CONSTRAINTS);

        // invalid index - zero index
        assertParseFailure(parser, VALID_PREFIX_LIST_TYPE_EXERCISE + INVALID_PREFIX_INDEX_ZERO,
            Index.MESSAGE_CONSTRAINTS);

        // two invalid values - only list type reported
        assertParseFailure(parser, INVALID_PREFIX_INDEX_ALPHABETS + INVALID_PREFIX_LIST_TYPE_ADDRESS,
            LIST_RESOURCE_TYPE_CONSTRAINTS);

        // nonempty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_PREFIX_LIST_TYPE_EXERCISE
            + VALID_PREFIX_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }

}

