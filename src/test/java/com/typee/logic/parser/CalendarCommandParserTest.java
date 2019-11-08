package com.typee.logic.parser;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarCloseDisplayCommand;
import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.CalendarOpenDisplayCommand;
import com.typee.logic.commands.CalendarPreviousMonthCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;

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
    public void parse_invalidFormat_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "next month",
                CalendarCommandParser.INVALID_CALENDAR_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validOpenDisplayArgs_returnsCalendarOpenDisplayCommand() {
        LocalDate date = LocalDate.of(2019, 10, 22);
        CommandParserTestUtil.assertParseSuccess(parser, "opendisplay 22/10/2019",
                new CalendarOpenDisplayCommand(date));
    }

    @Test
    public void parse_tooFewOpenDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "opendisplay",
                CalendarOpenDisplayCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_tooManyOpenDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "opendisplay 22/10/2019 hi",
                CalendarOpenDisplayCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidOpenDisplayArgs_throwsParseException() {
        String invalidArgs = "abcde";
        CommandParserTestUtil.assertParseFailure(parser, "opendisplay " + invalidArgs,
                InteractiveParserUtil.MESSAGE_INVALID_DATE_STRING);
    }

    @Test
    public void parse_invalidOpenDisplayDateArgs_throwsParseException() {
        String invalidDate = "29/02/2019";
        CommandParserTestUtil.assertParseFailure(parser, "opendisplay " + invalidDate,
                String.format(InteractiveParserUtil.MESSAGE_INVALID_DATE_FORMAT, invalidDate));
    }

    @Test
    public void parse_validCloseDisplayArgs_returnsCalendarCloseDisplayCommand() {
        LocalDate date = LocalDate.of(2019, 10, 22);
        CommandParserTestUtil.assertParseSuccess(parser, "closedisplay 22/10/2019",
                new CalendarCloseDisplayCommand(date));
    }

    @Test
    public void parse_tooFewCloseDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "closedisplay",
                CalendarCloseDisplayCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_tooManyCloseDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "closedisplay 22/10/2019 hi",
                CalendarCloseDisplayCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidCloseDisplayArgs_throwsParseException() {
        String invalidArgs = "abcde";
        CommandParserTestUtil.assertParseFailure(parser, "closedisplay " + invalidArgs,
                InteractiveParserUtil.MESSAGE_INVALID_DATE_STRING);
    }

    @Test
    public void parse_invalidCloseDisplayDateArgs_throwsParseException() {
        String invalidDate = "29/02/2019";
        CommandParserTestUtil.assertParseFailure(parser, "closedisplay " + invalidDate,
                String.format(InteractiveParserUtil.MESSAGE_INVALID_DATE_FORMAT, invalidDate));
    }

}
