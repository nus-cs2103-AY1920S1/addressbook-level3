package com.typee.logic.parser;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarDateDisplayEngagementsCommand;
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
    public void parse_invalidFormat_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "next month",
                CalendarCommandParser.INVALID_CALENDAR_COMMAND_MESSAGE);
    }

    @Test
    public void parse_validDisplayArgs_returnsCalendarDateDisplayEngagementsCommand() {
        LocalDate date = LocalDate.of(2019, 10, 22);
        CommandParserTestUtil.assertParseSuccess(parser, "display 22/10/2019",
                new CalendarDateDisplayEngagementsCommand(date));
    }

    @Test
    public void parse_tooFewDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "display",
                CalendarDateDisplayEngagementsCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_tooManyDisplayArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "display 22/10/2019 hi",
                CalendarDateDisplayEngagementsCommand.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidDisplayArgs_throwsParseException() {
        String invalidArgs = "abcde";
        CommandParserTestUtil.assertParseFailure(parser, "display " + invalidArgs,
                ParserUtil.MESSAGE_INVALID_DATE_STRING);
    }

    @Test
    public void parse_invalidDisplayDateArgs_throwsParseException() {
        String invalidDate = "29/02/2019";
        CommandParserTestUtil.assertParseFailure(parser, "display " + invalidDate,
                String.format(ParserUtil.MESSAGE_INVALID_DATE_FORMAT, invalidDate));
    }

}
