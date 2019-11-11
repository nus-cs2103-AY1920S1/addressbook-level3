package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.model.util.TimeStamp;

//@@author fatclarence
public class SaveCommandParserTest {
    private SaveCommandParser parser = new SaveCommandParser();

    @Test
    public void parse_validArgs_returnsTopUpCommand() {
        assertParseSuccess(parser, "100",
                 new SaveCommand("100", TimeStamp.generateCurrentTimeStamp()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abcd asd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_empty_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }
}
