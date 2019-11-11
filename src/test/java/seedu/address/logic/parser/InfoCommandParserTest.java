package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.InfoCommand;

class InfoCommandParserTest {

    private static final String VALID_INDEX_ONE = "1";
    private static final String VALID_INDEX_TWO = "2";
    private static final String SPACES = "     ";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE);

    private InfoCommandParser parser = new InfoCommandParser();

    @Test
    public void parse_nullArguments_failure() {
        assertParseFailure(parser, SPACES, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArguments_failure() {
        assertParseFailure(parser, VALID_INDEX_ONE + " " + VALID_INDEX_ONE, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, VALID_INDEX_ONE + " " + VALID_INDEX_TWO, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, " some random string", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "0", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validArgument_success() {
        InfoCommand expectedCommand1 = new InfoCommand(INDEX_FIRST_BOOK);
        assertParseSuccess(parser, VALID_INDEX_ONE, expectedCommand1);

        InfoCommand expectedCommand2 = new InfoCommand(INDEX_SECOND_BOOK);
        assertParseSuccess(parser, VALID_INDEX_TWO, expectedCommand2);
    }
}
