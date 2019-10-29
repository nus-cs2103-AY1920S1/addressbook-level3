package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CheckCommand;

/**
 * Tests the CheckCommandParser
 */
public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    //    @Test
    //    public void parse_validArgs_returnsCheckCommand() throws ParseException {
    //        Index index = Index.fromOneBased(1);
    //        assertParseSuccess(parser, "1", parser.parse("1"));
    //    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "checks 111", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CheckCommand.MESSAGE_USAGE));
    }
}
