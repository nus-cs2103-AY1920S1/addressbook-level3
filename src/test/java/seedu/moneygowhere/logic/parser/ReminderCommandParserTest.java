package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.COMMAND_WORD_DESC_ADD;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.COMMAND_WORD_DESC_DELETE;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_VALID;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_COMMAND_WORD_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.REMINDER_MESSAGE_DESC_VALID;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMINDER_MESSAGE;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.logic.commands.reminder.AddReminderCommand;
import seedu.moneygowhere.logic.commands.reminder.DeleteReminderCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.testutil.ReminderBuilder;

class ReminderCommandParserTest {

    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder(BILL_REMINDER).build();

        // whitespace only preamble for adding reminder
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COMMAND_WORD_DESC_ADD + DATE_DESC_VALID
                        + REMINDER_MESSAGE_DESC_VALID,
                new AddReminderCommand(expectedReminder));

        // multiple dates for adding reminder - last date accepted
        assertParseSuccess(parser, COMMAND_WORD_DESC_ADD + DATE_DESC_VALID + REMINDER_MESSAGE_DESC_VALID,
                new AddReminderCommand(expectedReminder));

        // Deleting reminder
        assertParseSuccess(parser, COMMAND_WORD_DESC_DELETE + " 1",
                new DeleteReminderCommand(INDEX_FIRST_REMINDER));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE);

        // missing command word
        assertParseFailure(parser, DATE_DESC_VALID + REMINDER_MESSAGE_DESC_VALID,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE + REMINDER_MESSAGE_DESC_VALID,
                expectedMessage);

        // missing message prefix
        assertParseFailure(parser, DATE_DESC_VALID + VALID_REMINDER_MESSAGE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DATE + VALID_REMINDER_MESSAGE,
                expectedMessage);
    }

    @Test
    public void parse_invalidCommandWord_failure() {
        assertParseFailure(parser, INVALID_COMMAND_WORD_DESC + DATE_DESC_VALID + REMINDER_MESSAGE_DESC_VALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReminderCommand.MESSAGE_USAGE), () -> parser.parse("unknownCommand"));
    }
}
