package seedu.module.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.linkcommands.AddLinkCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Link;

public class LinkCommandParserTest {
    private static final String VALID_COMMAND = "add n/website l/example.com";
    private static final String INVALID_COMMAND = "rubbish placeholder";
    private AddLinkCommand expectedCommand = new AddLinkCommand(new Link("website", "http://example.com"));
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse() throws ParseException {
        //valid input
        assertEquals(parser.parse(VALID_COMMAND), expectedCommand);
        //invalid input
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND));
    }
}
