package com.typee.logic.parser;

import java.time.LocalDate;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.CalendarCloseDisplayCommand;
import com.typee.logic.commands.CalendarCommand;
import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.CalendarOpenDisplayCommand;
import com.typee.logic.commands.CalendarPreviousMonthCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarCommand object.
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

    public static final String INVALID_CALENDAR_COMMAND_MESSAGE = "Invalid calendar command.";

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarCommand
     * and returns a CalendarCommand object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public CalendarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
        }
        String[] individualArgs = trimmedArgs.split("\\s+");
        String calendarCommandType = individualArgs[0].toLowerCase();
        switch (calendarCommandType) {
        case CalendarOpenDisplayCommand.COMMAND_WORD:
            return prepareCalendarOpenDisplayCommand(individualArgs);
        case CalendarCloseDisplayCommand.COMMAND_WORD:
            return prepareCalendarCloseDisplayCommand(individualArgs);
        case CalendarNextMonthCommand.COMMAND_WORD:
            return prepareCalendarNextMonthCommand(individualArgs);
        case CalendarPreviousMonthCommand.COMMAND_WORD:
            return prepareCalendarPreviousMonthCommand(individualArgs);
        default:
            throw new ParseException(INVALID_CALENDAR_COMMAND_MESSAGE);
        }
    }

    /**
     * Prepares a {@code CalendarOpenDisplayCommand} based on the specified arguments.
     * @param individualArgs The specified arguments.
     * @return A {@code CalendarOpenDisplayCommand} based on the specified arguments.
     * @throws ParseException If the specified arguments are invalid.
     */
    private CalendarOpenDisplayCommand prepareCalendarOpenDisplayCommand(
            String[] individualArgs) throws ParseException {
        if (individualArgs.length != 2) {
            throw new ParseException(CalendarOpenDisplayCommand.INVALID_COMMAND_FORMAT);
        }
        LocalDate date = InteractiveParserUtil.parseDate(individualArgs[1]);
        return new CalendarOpenDisplayCommand(date);
    }

    /**
     * Prepares a {@code CalendarCloseDisplayCommand} based on the specified arguments.
     * @param individualArgs The specified arguments.
     * @return A {@code CalendarCloseDisplayCommand} based on the specified arguments.
     * @throws ParseException If the specified arguments are invalid.
     */
    private CalendarCloseDisplayCommand prepareCalendarCloseDisplayCommand(
            String[] individualArgs) throws ParseException {
        if (individualArgs.length != 2) {
            throw new ParseException(CalendarCloseDisplayCommand.INVALID_COMMAND_FORMAT);
        }
        LocalDate date = InteractiveParserUtil.parseDate(individualArgs[1]);
        return new CalendarCloseDisplayCommand(date);
    }

    /**
     * Prepares a {@code CalendarNextMonthCommand}.
     * @param individualArgs The specified arguments.
     * @return A {@code CalendarNextMonthCommand}.
     * @throws ParseException If the specified arguments are invalid.
     */
    private CalendarNextMonthCommand prepareCalendarNextMonthCommand(
            String[] individualArgs) throws ParseException {
        if (individualArgs.length != 1) {
            throw new ParseException(CalendarNextMonthCommand.INVALID_COMMAND_FORMAT);
        }
        return new CalendarNextMonthCommand();
    }

    /**
     * Prepares a {@code CalendarPreviousMonthCommand}.
     * @param individualArgs The specified arguments.
     * @return A {@code CalendarPreviousMonthCommand}.
     * @throws ParseException If the specified arguments are invalid.
     */
    private CalendarPreviousMonthCommand prepareCalendarPreviousMonthCommand(
            String[] individualArgs) throws ParseException {
        if (individualArgs.length != 1) {
            throw new ParseException(CalendarPreviousMonthCommand.INVALID_COMMAND_FORMAT);
        }
        return new CalendarPreviousMonthCommand();
    }

}
