// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.PREAMBLE_WHITESPACE;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_PROTECTOR;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.testutil.note.TypicalNotes.PROTECTOR;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.model.note.Note;
import tagline.model.note.NoteIdCounter;
import tagline.testutil.note.NoteBuilder;

class CreateNoteParserTest {
    private CreateNoteParser parser = new CreateNoteParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Note expectedNote = new NoteBuilder(PROTECTOR).build();

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR,
                new CreateNoteCommand(expectedNote));

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // multiple title - last title accepted
        assertParseSuccess(parser,
                TITLE_DESC_INCIDENT + TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR,
                new CreateNoteCommand(expectedNote));

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // multiple content - last content accepted
        assertParseSuccess(parser,
                TITLE_DESC_PROTECTOR + CONTENT_DESC_INCIDENT + CONTENT_DESC_PROTECTOR,
                new CreateNoteCommand(expectedNote));

        /* TO ADD TEST FOR TAGS WHEN TAG IMPLEMENTED */

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // zero title
        Note expectedNote = new NoteBuilder(PROTECTOR).withTitle("").build();
        assertParseSuccess(parser,
                CONTENT_DESC_PROTECTOR,
                new CreateNoteCommand(expectedNote));

        /* TO ADD TEST FOR TAGS WHEN TAG IMPLEMENTED */

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }

    @Test
    public void parse_compulsaryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateNoteCommand.MESSAGE_USAGE);

        // missing content
        assertParseFailure(parser, TITLE_DESC_PROTECTOR, expectedMessage);

        // missing prefix for argument
        assertParseFailure(parser, VALID_TITLE_PROTECTOR + CONTENT_DESC_PROTECTOR, expectedMessage);

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }
}
