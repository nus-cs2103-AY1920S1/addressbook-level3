/*
@@author shihaoyap
 */

package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.schedule.DisplayScheduleForDateCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeyDatePredicate;
import seedu.address.model.event.EventDate;


public class DisplayScheduleForDateParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayScheduleForDateCommand.MESSAGE_USAGE);

    private DisplayScheduleForDateParser parser = new DisplayScheduleForDateParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date format : Input - "2019/10/29"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_1, String.format(MESSAGE_DATE_INVALID, "2019/10/29"));
        // invalid date format : Input - "20 Aug 2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_2, String.format(MESSAGE_DATE_INVALID, "20 Aug 2019"));
        // invalid date format : Input - "date"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_3, String.format(MESSAGE_DATE_INVALID, "date"));
        // invalid date format : Input - "10/12"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_4, String.format(MESSAGE_DATE_INVALID, "10/12"));
    }

    @Test
    public void parse_validDateSpecified1_success() throws ParseException {
        EventDate expectedStart = ParserUtil.parseEventDate("20/10/2019");
        DisplayScheduleForDateCommand expectedCommand =
                new DisplayScheduleForDateCommand(new EventContainsKeyDatePredicate(expectedStart));
        assertParseSuccess(parser, CommandTestUtil.VALID_DATE_1, expectedCommand);
    }

    @Test
    public void parse_validDateSpecified2_success() throws ParseException {
        EventDate expectedStart = ParserUtil.parseEventDate("22/10/2019");
        DisplayScheduleForDateCommand expectedCommand =
                new DisplayScheduleForDateCommand(new EventContainsKeyDatePredicate(expectedStart));
        assertParseSuccess(parser, CommandTestUtil.VALID_DATE_2, expectedCommand);
    }
}
