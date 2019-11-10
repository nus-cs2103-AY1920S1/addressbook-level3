package seedu.module.logic.parser.linkcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.linkcommands.AddLinkCommand;
import seedu.module.logic.parser.LinkCommandParser;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

public class AddLinkCommandParserTest {
    private static final String VALID_COMMAND_NO_SCHEME = "add n/website l/example.com";
    private static final String VALID_COMMAND_WITH_SCHEME = "add n/website l/http://example.com";
    private static final String INVALID_COMMAND_URL = "add n/website l/a.bc";
    private static final String INVALID_COMMAND_NO_URL = "add n/website";
    private static final String INVALID_COMMAND_NO_NAME = "add l/google.com";
    private AddLinkCommand expectedCommand = new AddLinkCommand(new Link("website", "http://example.com"));
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void addLinkCommand_test() throws ParseException {
        //valid
        assertEquals(parser.parse(VALID_COMMAND_NO_SCHEME), expectedCommand);
        //valid
        assertEquals(parser.parse(VALID_COMMAND_WITH_SCHEME), expectedCommand);
        //invalid url
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND_URL));
        //invalid missing argument
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND_NO_URL));
        //invalid missing argument
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND_NO_NAME));
    }
}
