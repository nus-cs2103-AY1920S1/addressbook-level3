package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.ExpandCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ExpandCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ExpandCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ExpandCommandParserTest {

    private ExpandCommandParser parser = new ExpandCommandParser();

    @Test
    public void parse_validArgs_returnsExpandCommand() {
        assertParseSuccess(parser, "2", new ExpandCommand(2));
    }

    @Test
    public void parse_emptyArgs_returnsExpandCommand() {
        assertParseSuccess(parser, "", new ExpandCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpandCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zero_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpandCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeNumber_throwsParseException() {
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpandCommand.MESSAGE_USAGE));
    }
}
