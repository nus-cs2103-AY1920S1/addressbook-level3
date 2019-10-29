package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.mark.logic.commands.CommandTestUtil.NOTE_DESC_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.TIME_DESC_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_OPEN;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.EditReminderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.reminder.Note;
import seedu.mark.testutil.EditReminderDescriptorBuilder;

class EditReminderCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NOTE_OPEN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditReminderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NOTE_DESC_OPEN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NOTE_DESC_OPEN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS); // invalid note
        assertParseFailure(parser, "1" + INVALID_TIME_DESC, MESSAGE_INVALID_TIME_FORMAT); // invalid time

        // invalid note followed by valid time
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC + TIME_DESC_OPEN, Note.MESSAGE_CONSTRAINTS);

        // valid note followed by invalid note.
        //TODO: Find error
        assertParseFailure(parser, "1" + VALID_NOTE_OPEN + INVALID_NOTE_DESC, MESSAGE_INVALID_FORMAT);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC + INVALID_TIME_DESC,
                Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + NOTE_DESC_OPEN + TIME_DESC_OPEN;

        EditReminderCommand.EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withNote(VALID_NOTE_OPEN)
                .withTime(VALID_TIME_OPEN).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
