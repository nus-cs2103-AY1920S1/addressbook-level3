// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.DeleteNoteCommand;
import tagline.model.note.NoteId;

class DeleteNoteParserTest {

    private DeleteNoteParser parser = new DeleteNoteParser();

    @Test
    public void parse_validArgs_returnsDeleteNoteCommand() {
        assertParseSuccess(parser, "1", new DeleteNoteCommand(new NoteId(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteNoteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteNoteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "10a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteNoteCommand.MESSAGE_USAGE));
    }
}
