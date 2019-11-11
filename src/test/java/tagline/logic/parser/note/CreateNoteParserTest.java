// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.INVALID_TITLE_DESC;
import static tagline.logic.commands.NoteCommandTestUtil.PREAMBLE_WHITESPACE;
import static tagline.logic.commands.NoteCommandTestUtil.TAG_DESC_AVENGER;
import static tagline.logic.commands.NoteCommandTestUtil.TAG_DESC_MOVIE;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_PROTECTOR;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.logic.parser.CommandParserTestUtil.assertPromptRequest;
import static tagline.logic.parser.note.CreateNoteParser.CREATE_NOTE_MISSING_CONTENT_PROMPT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;
import static tagline.logic.parser.note.NoteParserUtil.ERROR_SINGLE_PREFIX_USAGE;
import static tagline.testutil.note.TypicalNotes.PROTECTOR;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.CreateNoteCommand;
import tagline.logic.parser.Prompt;
import tagline.model.note.Note;
import tagline.model.note.NoteIdCounter;
import tagline.model.note.Title;
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
                PREAMBLE_WHITESPACE + TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR
                        + TAG_DESC_AVENGER + TAG_DESC_MOVIE,
                new CreateNoteCommand(expectedNote));

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }

    @Test
    public void parse_invalidFieldsPresent_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleSingleFieldsPresent_failure() {

        // multiple title - throw ParseException
        assertParseFailure(parser,
                TITLE_DESC_INCIDENT + TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR,
                String.format(ERROR_SINGLE_PREFIX_USAGE, PREFIX_TITLE));

        // multiple content - throw ParseException
        assertParseFailure(parser,
                TITLE_DESC_PROTECTOR + CONTENT_DESC_INCIDENT + CONTENT_DESC_PROTECTOR,
                String.format(ERROR_SINGLE_PREFIX_USAGE, PREFIX_CONTENT));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // zero title, but with content and tags
        Note expectedNote = new NoteBuilder(PROTECTOR).withTitle("").build();
        assertParseSuccess(parser,
                CONTENT_DESC_PROTECTOR + TAG_DESC_AVENGER + TAG_DESC_MOVIE,
                new CreateNoteCommand(expectedNote));

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // zero content, but with title and tags
        expectedNote = new NoteBuilder(PROTECTOR).withContent("").build();
        assertParseSuccess(parser,
                TITLE_DESC_PROTECTOR + TAG_DESC_AVENGER + TAG_DESC_MOVIE,
                new CreateNoteCommand(expectedNote));

        // Set note id counter so generated id is VALID_NOTEID_PROTECTOR
        NoteIdCounter.setCount(VALID_NOTEID_PROTECTOR - 1);

        // zero test, but with title and content
        expectedNote = new NoteBuilder(PROTECTOR).withTags().build();
        assertParseSuccess(parser,
                TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR,
                new CreateNoteCommand(expectedNote));

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }

    @Test
    public void parse_compulsaryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateNoteCommand.MESSAGE_USAGE);

        // missing content and title
        assertPromptRequest(parser, "", Arrays.asList(
                new Prompt(PREFIX_CONTENT.getPrefix(), CREATE_NOTE_MISSING_CONTENT_PROMPT)));

        // missing prefix for argument
        assertParseFailure(parser, VALID_TITLE_PROTECTOR + CONTENT_DESC_PROTECTOR, expectedMessage);

        // Set note id counter BACK TO 0
        NoteIdCounter.setCount(0);
    }
}
