package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.AutotagDeleteCommand;

public class AutotagDeleteCommandParserTest {

    private final AutotagDeleteCommandParser parser = new AutotagDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsAutotagDeleteCommand() {
        assertParseSuccess(parser, "myTag", new AutotagDeleteCommand("myTag"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutotagDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "multiple tagNames not allowed",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutotagDeleteCommand.MESSAGE_USAGE));
    }
}
