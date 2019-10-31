// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.INVALID_CONTENT_DESC;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_PROTECTOR;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.EditNoteCommand;
import tagline.logic.commands.note.EditNoteCommand.EditNoteDescriptor;
import tagline.model.note.Content;
import tagline.model.note.NoteId;
import tagline.testutil.EditNoteDescriptorBuilder;

class EditNoteParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNoteCommand.MESSAGE_USAGE);

    private EditNoteParser parser = new EditNoteParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CONTENT_PROTECTOR, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditNoteCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative id
        assertParseFailure(parser, "-1" + CONTENT_DESC_PROTECTOR, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 invalid arguments", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 c/note content", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parser_invalidValue_failure() {
        assertParseFailure(parser, "1 " + INVALID_CONTENT_DESC, Content.MESSAGE_CONSTRAINTS);

        // invalid content followed by valid title
        assertParseFailure(parser, "1 " + INVALID_CONTENT_DESC + TITLE_DESC_PROTECTOR,
                Content.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        NoteId targetNoteId = new NoteId(1);
        String userInput = targetNoteId.toString() + TITLE_DESC_PROTECTOR + CONTENT_DESC_PROTECTOR;

        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder()
                .withContent(VALID_CONTENT_PROTECTOR).withTitle(VALID_TITLE_PROTECTOR).build();
        EditNoteCommand expectedCommand = new EditNoteCommand(targetNoteId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldspecified_success() {
        NoteId targetNoteId = new NoteId(1);

        // title
        String userInput = targetNoteId.toString() + TITLE_DESC_PROTECTOR;
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_PROTECTOR).build();
        EditNoteCommand expectedCommand = new EditNoteCommand(targetNoteId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // content
        userInput = targetNoteId.toString() + CONTENT_DESC_PROTECTOR;
        descriptor = new EditNoteDescriptorBuilder().withContent(VALID_CONTENT_PROTECTOR).build();
        expectedCommand = new EditNoteCommand(targetNoteId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
