package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.DATE_DESC_VALID;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.REMINDER_MESSAGE_DESC_VALID;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMINDER_MESSAGE;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.ReminderCommand;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.testutil.ReminderBuilder;

class ReminderCommandParserTest {

    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder(BILL_REMINDER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_VALID + REMINDER_MESSAGE_DESC_VALID,
                new ReminderCommand(expectedReminder));

        // multiple dates - last date accepted
        assertParseSuccess(parser, DATE_DESC_AMY + DATE_DESC_VALID + REMINDER_MESSAGE_DESC_VALID,
                new ReminderCommand(expectedReminder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE);

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
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + REMINDER_MESSAGE_DESC_VALID, Date.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DATE_DESC_VALID
                        + REMINDER_MESSAGE_DESC_VALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }
}
