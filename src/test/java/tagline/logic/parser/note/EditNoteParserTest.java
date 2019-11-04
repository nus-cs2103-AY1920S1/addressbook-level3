// @@author shiweing
package tagline.logic.parser.note;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.CONTENT_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.INVALID_TITLE_DESC;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.TITLE_DESC_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_PROTECTOR;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_CONTENT;
import static tagline.logic.parser.note.NoteCliSyntax.PREFIX_TITLE;
import static tagline.logic.parser.note.NoteParserUtil.ERROR_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.note.EditNoteCommand;
import tagline.logic.commands.note.EditNoteCommand.EditNoteDescriptor;
import tagline.model.note.NoteId;
import tagline.model.note.Title;
import tagline.testutil.note.EditNoteDescriptorBuilder;

class EditNoteParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditNoteCommand.MESSAGE_USAGE);

    private EditNoteParser parser = new EditNoteParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CONTENT_DESC_INCIDENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditNoteCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative id
        String invalidPreamble = "-1";
        assertParseFailure(parser, invalidPreamble + CONTENT_DESC_PROTECTOR,
                String.format(ERROR_INVALID_INDEX, invalidPreamble));

        // invalid arguments being parsed as preamble
        invalidPreamble = "1 invalid arguments";
        assertParseFailure(parser, invalidPreamble,
                String.format(ERROR_INVALID_INDEX, invalidPreamble));

        // invalid prefix being parsed as preamble
        invalidPreamble = "1 c/note content";
        assertParseFailure(parser, invalidPreamble,
                String.format(ERROR_INVALID_INDEX, invalidPreamble));
    }

    @Test
    public void parse_invalidFieldsPresent_failure() {
        // invalid title
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleSingleFieldsPresent_failure() {
        // title provided twice
        assertParseFailure(parser,
                "1 " + TITLE_DESC_PROTECTOR + TITLE_DESC_INCIDENT,
                String.format(NoteParserUtil.ERROR_SINGLE_PREFIX_USAGE, PREFIX_TITLE));

        // content provided twice
        assertParseFailure(parser,
                "1 " + CONTENT_DESC_PROTECTOR + CONTENT_DESC_INCIDENT,
                String.format(NoteParserUtil.ERROR_SINGLE_PREFIX_USAGE, PREFIX_CONTENT));
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
