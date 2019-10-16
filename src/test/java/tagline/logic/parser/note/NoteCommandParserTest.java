package tagline.logic.parser.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.testutil.NoteBuilder.DEFAULT_NOTEID;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.model.note.Note;
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

}
