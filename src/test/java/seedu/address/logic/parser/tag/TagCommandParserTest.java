package seedu.address.logic.parser.tag;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

public class TagCommandParserTest {
    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parseCommand_tagValidCommand_success() throws Exception {
        Command command = parser.parse(" index/1 tag/newTag");
        assertTrue(command instanceof TagCommand);
    }

    @Test
    public void parseCommand_tagNoTagSpecifiedCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/1 tag/"));
    }

    @Test
    public void parseCommand_tagIncorrectIndexSpecifiedCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0 tag/newTag"));
    }

    @Test
    public void parseCommand_tagNoTagSpecifiedIncorrectIndexSpecifiedCommand_success() throws Exception {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), () ->
                        parser.parse(" index/0 tag/"));
    }
}
