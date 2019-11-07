package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.DisplayCommand;


public class DisplayCommandParserTest {
    private DisplayCommandParser displayCommandParser = new DisplayCommandParser();

    @Test
    public void parse_emptyArgs_throwParseException() {
        assertParseFailure(displayCommandParser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE));
    }
}
