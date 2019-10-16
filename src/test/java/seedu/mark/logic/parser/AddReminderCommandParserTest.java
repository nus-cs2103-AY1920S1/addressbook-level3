package seedu.mark.logic.parser;

import java.time.LocalDateTime;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_READ;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_TIME_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_READ;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.model.reminder.Note;
import seedu.mark.logic.commands.AddReminderCommand;


class AddReminderCommandParserTest {
    //TODO: Add more test cases, for now the coverage is okay
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TIME_OPEN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TIME_OPEN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TIME_OPEN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

}
