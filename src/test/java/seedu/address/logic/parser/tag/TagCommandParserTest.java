package seedu.address.logic.parser.tag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test for TagCommandParser.
 */
public class TagCommandParserTest {
    private final TagCommandParser parser = new TagCommandParser();

    /**
     * Test for successfully creating TagCommand.
     */
    @Test
    public void parseCommand_tagValidCommand_success() throws Exception {
        Command command = parser.parse(" index/1 tag/newTag");
        assertTrue(command instanceof TagCommand);
    }

    /**
     * Test for unsuccessfully creating TagCommand, due to no tags specified.
     */
    @Test
    public void parseCommand_tagNoTagSpecifiedCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/1 tag/"));
    }

    /**
     * Test for unsuccessfully creating TagCommand, due to index out of bounds.
     */
    @Test
    public void parseCommand_tagIncorrectIndexSpecifiedCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0 tag/newTag"));
    }

    /**
     * Test for unsuccessfully creating TagCommand, due to no tags specified and index out of bounds.
     */
    @Test
    public void parseCommand_tagNoTagSpecifiedIncorrectIndexSpecifiedCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0 tag/"));
    }

    /**
     * Test for unsuccessfully creating TagCommand, due to incomplete command.
     */
    @Test
    public void parseCommand_tagIncompleteCommand_failure() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" "));
    }
}
