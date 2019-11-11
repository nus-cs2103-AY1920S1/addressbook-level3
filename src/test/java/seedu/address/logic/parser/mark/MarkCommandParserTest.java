package seedu.address.logic.parser.mark;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.mark.AddMarkCommand;
import seedu.address.logic.commands.mark.RemoveMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test for MarkCommandParser.
 */
public class MarkCommandParserTest {
    private final MarkCommandParser parser = new MarkCommandParser();

    /**
     * Tests for successfully creating a AddMarkCommand.
     */
    @Test
    public void parseCommand_markValidCommand_success() throws Exception {
        Command command = parser.parse(" index/1");
        assertTrue(command instanceof AddMarkCommand);
    }

    /**
     * Tests for successfully creating a RemoveMarkCommand.
     */
    @Test
    public void parseCommand_unmarkValidCommand_success() throws Exception {
        Command command = parser.parse(" unmark index/1");
        assertTrue(command instanceof RemoveMarkCommand);
    }

    /**
     * Tests for unsuccessfully creating a AddMarkCommand, due to invalid index.
     */
    @Test
    public void parseCommand_markInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMarkCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0"));
    }

    /**
     * Tests for unsuccessfully creating a RemoveMarkCommand, due to invalid index.
     */
    @Test
    public void parseCommand_unmarkInvalidCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMarkCommand.MESSAGE_USAGE), () ->
                        parser.parse(" unmark index/0"));
    }

    /**
     * Tests for unsuccessfully creating an AddMarkCommand, due to incomplete command.
     */
    @Test
    public void parseCommand_markIncompleteCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMarkCommand.MESSAGE_USAGE), () ->
                        parser.parse(" "));
    }

}
