package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

public class CalendarCommandParserTest {

    private CalendarCommandParser parser = new CalendarCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs1_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs2_throwsParseException() {
        assertParseFailure(parser, "test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCalendarCommand() throws ParseException {
        assertParseSuccess(parser, "01012019", new CalendarCommand(new AthletickDate(1, 1, 2019, 1, "January")));
    }
}
