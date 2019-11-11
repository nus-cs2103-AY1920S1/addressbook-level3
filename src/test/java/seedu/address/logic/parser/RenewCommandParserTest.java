package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RenewCommand;

class RenewCommandParserTest {
    private static final String VALID_INDEX_ONE = "1";
    private static final String EMPTY_STRING = "";
    private static final String INVALID_INDEX = "a";
    private static final String ALL_FLAG = " -all";

    private RenewCommandParser renewCommandParser = new RenewCommandParser();

    @Test
    public void parse_indexPresent_success() {
        Index index = INDEX_FIRST_BOOK;
        assertParseSuccess(renewCommandParser, VALID_INDEX_ONE, new RenewCommand(index));
    }

    @Test
    public void parse_allFlagPresent_success() {
        assertParseSuccess(renewCommandParser, ALL_FLAG, new RenewCommand());
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(renewCommandParser, EMPTY_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(renewCommandParser, INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE));
    }
}
