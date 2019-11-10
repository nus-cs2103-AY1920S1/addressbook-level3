package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.TopUpCommand;

//@@author raikonen
public class TopUpCommandParserTest {
    private TopUpCommandParser parser = new TopUpCommandParser();

    @Test
    public void parse_validArgs_returnsTopUpCommand() {
        assertParseSuccess(parser, "100", new TopUpCommand(new BigDecimal(100)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abcd asd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopUpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopUpCommand.MESSAGE_USAGE));
    }
}
