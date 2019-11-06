package seedu.address.logic.parser.note;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_CONTENT_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_TAG_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTE_TITLE_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_CONTENT_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TAG_1_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TAG_2_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TITLE_PIPELINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_PIPELINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailureIllegalArgument;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNotes.PIPELINE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.NoteBuilder;

public class AddNoteCommandParserTest {
    public AddNoteCommandParser parser = new AddNoteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Note expectedNote = new NoteBuilder(PIPELINE).withTags(VALID_TAG_CS2100).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE
                        + VALID_NOTE_TAG_1_PIPELINE, new AddNoteCommand(expectedNote));

        // duplicate tags - only one accepted
        assertParseSuccess(parser,
                VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE
                        + VALID_NOTE_TAG_1_PIPELINE, new AddNoteCommand(expectedNote));

        expectedNote = new NoteBuilder(PIPELINE).build();

        // multiple tags - all accepted
        assertParseSuccess(parser,
                VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE
                        + VALID_NOTE_TAG_2_PIPELINE, new AddNoteCommand(expectedNote));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Note expectedNote = new NoteBuilder(PIPELINE).withTags().build();
        assertParseSuccess(parser, VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE,
                new AddNoteCommand(expectedNote));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE);

        // missing space in front
        assertParseFailure(parser,
                PREFIX_TITLE + VALID_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE,
                expectedMessage);

        // nonempty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE
                        + VALID_NOTE_TAG_1_PIPELINE, expectedMessage);

        // missing title prefix
        assertParseFailure(parser,
                VALID_NOTE_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE,
                expectedMessage);

        // missing content prefix
        assertParseFailure(parser,
                 VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_TAG_1_PIPELINE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                SPACE + VALID_TITLE_PIPELINE + SPACE + VALID_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailureIllegalArgument(parser, INVALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE
                        + VALID_NOTE_TAG_1_PIPELINE, Title.MESSAGE_CONSTRAINTS);

        // invalid content
        assertParseFailureIllegalArgument(parser, VALID_NOTE_TITLE_PIPELINE + INVALID_NOTE_CONTENT_PIPELINE
                + VALID_NOTE_TAG_1_PIPELINE, Content.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailureIllegalArgument(parser, VALID_NOTE_TITLE_PIPELINE + VALID_NOTE_CONTENT_PIPELINE
                + INVALID_NOTE_TAG_PIPELINE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailureIllegalArgument(parser, INVALID_NOTE_TITLE_PIPELINE + INVALID_NOTE_CONTENT_PIPELINE
                + VALID_NOTE_TAG_1_PIPELINE, Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_NOTE_TITLE_PIPELINE
                + VALID_NOTE_CONTENT_PIPELINE + VALID_NOTE_TAG_1_PIPELINE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
    }
}
