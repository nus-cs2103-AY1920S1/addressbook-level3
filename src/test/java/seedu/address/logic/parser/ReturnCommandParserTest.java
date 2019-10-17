package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReturnCommand;

class ReturnCommandParserTest {
    private static final String VALID_INDEX_ONE = "1";
    private static final String EMPTY_STRING = "";
    private static final String INVALID_INDEX = "a";

    private ReturnCommandParser returnCommandParser = new ReturnCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index = INDEX_FIRST_BOOK;
        assertParseSuccess(returnCommandParser, VALID_INDEX_ONE, new ReturnCommand(index));
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
}
