package seedu.address.logic.parser.mark;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.mark.AddMarkCommand;
import seedu.address.logic.commands.mark.RemoveMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

public class MarkCommandParserTest {
    private final MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parseCommand_markValidCommand_success() throws Exception {
        Command command = parser.parse(" index/1");
        assertTrue(command instanceof AddMarkCommand);
    }

    @Test
    public void parseCommand_unmarkValidCommand_success() throws Exception {
        Command command = parser.parse(" unmark index/1");
        assertTrue(command instanceof RemoveMarkCommand);
    }

    @Test
    public void parseCommand_markInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMarkCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0"));
    }

    @Test
    public void parseCommand_unmarkInvalidCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMarkCommand.MESSAGE_USAGE), () ->
                        parser.parse(" unmark index/0"));
    }

}
