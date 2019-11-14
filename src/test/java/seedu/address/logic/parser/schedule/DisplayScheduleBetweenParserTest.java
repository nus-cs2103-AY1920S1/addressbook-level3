/*
@@author shihaoyap
 */

package seedu.address.logic.parser.schedule;

import static seedu.address.commons.core.Messages.MESSAGE_DATE_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.schedule.DisplayScheduleBetweenCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeyDateRangePredicate;
import seedu.address.model.event.EventDate;


public class DisplayScheduleBetweenParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayScheduleBetweenCommand.MESSAGE_USAGE);

    private DisplayScheduleBetweenParser parser = new DisplayScheduleBetweenParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start date, valid end date format : Input - "start/2019/10/29 end/30/12/2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_RANGE_1,
                String.format(MESSAGE_DATE_INVALID, "2019/10/29"));

        // invalid end date, valid start date format : Input - "start/01/01/2019 end/30 Aug 2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_RANGE_2,
                String.format(MESSAGE_DATE_INVALID, "30 Aug 2019"));

        // invalid start date, invalid start date format : Input - "start/10 Aug 2019 end/30 Aug 2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_RANGE_3,
                String.format(MESSAGE_DATE_INVALID, "10 Aug 2019"));

        // invalid date range specified, start date is after the end date : Input - "start/30/12/2019 end/01/01/2019"
        assertParseFailure(parser, CommandTestUtil.INVALID_DATE_RANGE_4,
                String.format(MESSAGE_DATE_START_AFTER_END, "30/12/2019", "01/01/2019"));
    }

    @Test
    public void parse_validDateSpecified1_success() throws ParseException {
        EventDate expectedStart = ParserUtil.parseEventDate("01/10/2019");
        EventDate expectedEnd = ParserUtil.parseEventDate("30/12/2019");
        DisplayScheduleBetweenCommand expectedCommand =
                new DisplayScheduleBetweenCommand(new EventContainsKeyDateRangePredicate(expectedStart, expectedEnd));
        assertParseSuccess(parser, CommandTestUtil.VALID_DATE_RANGE_1, expectedCommand);
    }

    @Test
    public void parse_validDateSpecified2_success() throws ParseException {
        EventDate expectedStart = ParserUtil.parseEventDate("10/10/2019");
        EventDate expectedEnd = ParserUtil.parseEventDate("12/10/2019");
        DisplayScheduleBetweenCommand expectedCommand =
                new DisplayScheduleBetweenCommand(new EventContainsKeyDateRangePredicate(expectedStart, expectedEnd));
        assertParseSuccess(parser, CommandTestUtil.VALID_DATE_RANGE_2, expectedCommand);
    }
}
