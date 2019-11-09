package seedu.address.logic.parser.note;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.ViewRawNoteCommand;

public class ViewRawNoteCommandParserTest {
    private ViewRawNoteCommandParser parser = new ViewRawNoteCommandParser();

    @Test
    public void parse_validArgs_returnsViewRawNoteCommand() {
        assertParseSuccess(parser, "1", new ViewRawNoteCommand(INDEX_FIRST_NOTE));
    }

    @Test
    public void parse_validArgsWithAdditionalWhitespace_returnsViewRawNoteCommand() {
        assertParseSuccess(parser, " \t\r\n1", new ViewRawNoteCommand(INDEX_FIRST_NOTE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewRawNoteCommand.MESSAGE_USAGE));
    }
}
