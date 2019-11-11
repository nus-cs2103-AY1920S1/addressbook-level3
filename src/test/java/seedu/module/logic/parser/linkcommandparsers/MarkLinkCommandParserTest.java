package seedu.module.logic.parser.linkcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.linkcommands.MarkLinkCommand;
import seedu.module.logic.parser.LinkCommandParser;
import seedu.module.logic.parser.exceptions.ParseException;

public class MarkLinkCommandParserTest {
    private static final String VALID_COMMAND = "mark n/website";
    private static final String VALID_COMMAND_UNMARK = "unmark n/website";
    private static final String INVALID_COMMAND = "mark";
    private MarkLinkCommand expectedMarkCommand = new MarkLinkCommand("website", true);
    private MarkLinkCommand expectedUnmarkCommand = new MarkLinkCommand("website", false);
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse() throws ParseException {
        //valid mark
        assertEquals(parser.parse(VALID_COMMAND), expectedMarkCommand);
        //valid unmark
        assertEquals(parser.parse(VALID_COMMAND_UNMARK), expectedUnmarkCommand);
        //invalid missing argument
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND));
    }
}
