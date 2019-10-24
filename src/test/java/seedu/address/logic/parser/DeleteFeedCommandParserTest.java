package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EATBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EATBOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteFeedCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteFeedCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteFeedCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteFeedCommandParserTest {

    private DeleteFeedCommandParser parser = new DeleteFeedCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteFeedCommand() {
        assertParseSuccess(parser, NAME_DESC_EATBOOK, new DeleteFeedCommand(VALID_NAME_EATBOOK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, VALID_NAME_EATBOOK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFeedCommand.MESSAGE_USAGE));
    }
}
