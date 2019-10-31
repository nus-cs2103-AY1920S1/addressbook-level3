// @@author shiweing
package tagline.logic.parser.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.testutil.NoteBuilder.DEFAULT_NOTEID;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.logic.commands.note.DeleteNoteCommand;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.NoteIdCounter;
import tagline.testutil.NoteBuilder;
import tagline.testutil.NoteUtil;

class NoteCommandParserTest {
    private final NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parseCommand_create() throws Exception {
        // Set note id counter so generated id is DEFAULT_NOTEID
        NoteIdCounter.setCount(DEFAULT_NOTEID - 1);

        Note note = new NoteBuilder().build();
        CreateNoteCommand command = (CreateNoteCommand) parser.parseCommand(NoteUtil.getCreateCommand(note));
        assertEquals(new CreateNoteCommand(note), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        NoteId noteId = new NoteId(1);
        DeleteNoteCommand command = (DeleteNoteCommand) parser.parseCommand(
                DeleteNoteCommand.COMMAND_WORD + " " + noteId.toString());
        assertEquals(new DeleteNoteCommand(noteId), command);
    }
}
