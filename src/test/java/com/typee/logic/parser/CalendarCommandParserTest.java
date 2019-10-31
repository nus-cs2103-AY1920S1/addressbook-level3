package com.typee.logic.parser;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.CalendarPreviousMonthCommand;

public class CalendarCommandParserTest {

    private CalendarCommandParser parser = new CalendarCommandParser();

    @Test
    public void parse_validNextMonthArgs_returnsCalendarNextMonthCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "nextmonth",
                new CalendarNextMonthCommand());
    }

    @Test
    public void parse_invalidNextMonthArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "nextmonth 1",
                CalendarNextMonthCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_validPreviousMonthArgs_returnsCalendarPreviousMonthCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "previousmonth",
                new CalendarPreviousMonthCommand());
    }

    @Test
    public void parse_invalidPreviousMonthArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "previousmonth 1",
                CalendarPreviousMonthCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidCalendarCommand_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "next month",
                CalendarCommandParser.INVALID_CALENDAR_COMMAND_MESSAGE);
    }

}
