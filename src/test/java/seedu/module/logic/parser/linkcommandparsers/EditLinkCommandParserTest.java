package seedu.module.logic.parser.linkcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.linkcommands.EditLinkCommand;
import seedu.module.logic.parser.LinkCommandParser;
import seedu.module.logic.parser.exceptions.ParseException;

public class EditLinkCommandParserTest {
    private static final String VALID_COMMAND_NAME_LINK = "edit n/website nn/new website nl/example.com";
    private static final String VALID_COMMAND_NO_ARGUMENTS = "edit n/website";
    private static final String INVALID_COMMAND_WRONG_URL = "edit n/website nl/wrong.a";
    private static final String INVALID_COMMAND_NO_LINK_NAME = "edit nn/new website";
    private EditLinkCommand expectedCommandNameLink = new EditLinkCommand("website", Optional.of("new website"),
            Optional.of("http://example.com"));
    private EditLinkCommand expectedCommandNoArguments = new EditLinkCommand("website", Optional.empty(),
            Optional.empty());
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse() throws ParseException {
        //valid both arguments
        assertEquals(parser.parse(VALID_COMMAND_NAME_LINK), expectedCommandNameLink);
        //valid no edit arguments
        assertEquals(parser.parse(VALID_COMMAND_NO_ARGUMENTS), expectedCommandNoArguments);
        //invalid wrong url
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND_WRONG_URL));
        //invalid no link specified
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND_NO_LINK_NAME));
    }
}
