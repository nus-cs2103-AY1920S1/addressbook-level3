package seedu.module.logic.parser.linkcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.linkcommands.DeleteLinkCommand;
import seedu.module.logic.parser.LinkCommandParser;
import seedu.module.logic.parser.exceptions.ParseException;

public class DeleteLinkCommandParserTest {
    private static final String VALID_COMMAND = "delete n/website";
    private static final String INVALID_COMMAND = "delete";
    private DeleteLinkCommand expectedCommand = new DeleteLinkCommand("website");
    private LinkCommandParser parser = new LinkCommandParser();
    @Test
    public void parse() throws ParseException {
        //valid
        assertEquals(parser.parse(VALID_COMMAND), expectedCommand);
        //invalid missing arguments
        assertThrows((ParseException.class), () -> parser.parse(INVALID_COMMAND));
    }
}
