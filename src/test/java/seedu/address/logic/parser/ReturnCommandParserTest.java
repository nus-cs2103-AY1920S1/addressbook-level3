package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_ALL_FLAG_CONSTRAINTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.Flag.RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReturnCommand;

class ReturnCommandParserTest {
    private static final String VALID_INDEX_ONE = "1";
    private static final String EMPTY_STRING = "";
    private static final String INVALID_INDEX = "a";
    private static final String ALL_FLAG = " -all";
    private static final String AVAILABLE_FLAG = " -available";
    private static final String INVALID_FLAG = " -abc";
    private static final String EMPTY_FLAG = " -";

    private ReturnCommandParser returnCommandParser = new ReturnCommandParser();

    @Test
    public void parse_indexPresent_success() {
        Index index = INDEX_FIRST_BOOK;
        assertParseSuccess(returnCommandParser, VALID_INDEX_ONE, new ReturnCommand(index));
    }

    @Test
    public void parse_allFlagPresent_success() {
        assertParseSuccess(returnCommandParser, ALL_FLAG, new ReturnCommand());
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(returnCommandParser, EMPTY_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(returnCommandParser, INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFlag_failure() {
        assertParseFailure(returnCommandParser, INVALID_FLAG,
                String.format(RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS, ReturnCommand.COMMAND_WORD));
    }

    @Test
    public void parse_unusableFlag_failure() {
        assertParseFailure(returnCommandParser, AVAILABLE_FLAG,
                String.format(RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS, ReturnCommand.COMMAND_WORD));
    }

    @Test
    public void parse_emptyFlag_failure() {
        assertParseFailure(returnCommandParser, EMPTY_FLAG,
                String.format(RETURN_AND_RENEW_FLAG_MESSAGE_CONSTRAINTS, ReturnCommand.COMMAND_WORD));
    }

    @Test
    public void parse_mixedInputArguments_failure() {
        assertParseFailure(returnCommandParser, VALID_INDEX_ONE + ALL_FLAG, MESSAGE_ALL_FLAG_CONSTRAINTS);
    }
}
