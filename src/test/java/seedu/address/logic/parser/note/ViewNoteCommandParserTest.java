package seedu.address.logic.parser.note;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.ViewNoteCommand;

public class ViewNoteCommandParserTest {
    private ViewNoteCommandParser parser = new ViewNoteCommandParser();

    @Test
    public void parse_validArgs_returnsViewNoteCommand() {
        assertParseSuccess(parser, "1", new ViewNoteCommand(INDEX_FIRST_NOTE));
    }

    @Test
    public void parse_validArgsWithAdditionalWhitespace_returnsViewNoteCommand() {
        assertParseSuccess(parser, " \t\r\n1", new ViewNoteCommand(INDEX_FIRST_NOTE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewNoteCommand.MESSAGE_USAGE));
    }
}
