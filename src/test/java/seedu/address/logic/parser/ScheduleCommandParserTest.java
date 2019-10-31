package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FRIDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_MONDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ScheduleCommand;

public class ScheduleCommandParserTest {

    private static final ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_MONDAY,
                new ScheduleCommand(VALID_SCHEDULE_MONDAY));

        // multiple dates - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_FRIDAY + DATE_DESC_MONDAY,
                new ScheduleCommand(VALID_SCHEDULE_MONDAY));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC, Messages.DATE_MESSAGE_CONSTRAINTS);
    }

}
