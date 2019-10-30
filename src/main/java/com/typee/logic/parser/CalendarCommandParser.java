package com.typee.logic.parser;

import java.time.LocalDate;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.CalendarCommand;
import com.typee.logic.commands.CalendarDateDisplayEngagementsCommand;
import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarCommand object.
 */
public class CalendarCommandParser implements Parser<CalendarCommand> {

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
        case CalendarDateDisplayEngagementsCommand.COMMAND_WORD:
            return prepareCalendarDateDisplayEngagementsCommand(individualArgs);
        case CalendarNextMonthCommand.COMMAND_WORD:
            return prepareCalendarNextMonthCommand(individualArgs);
        default:
            throw new ParseException("Invalid calendar command.\n");
        }
    }

    /**
     * Prepares a {@code CalendarDateDisplayEngagementsCommand} based on the specified arguments.
     * @param individualArgs The specified arguments.
     * @return A {@code CalendarDateDisplayEngagementsCommand} based on the specified arguments.
     * @throws ParseException If the specified arguments are invalid.
     */
    private CalendarDateDisplayEngagementsCommand prepareCalendarDateDisplayEngagementsCommand(
            String[] individualArgs) throws ParseException {
        if (individualArgs.length != 2) {
            throw new ParseException(CalendarDateDisplayEngagementsCommand.INVALID_COMMAND_FORMAT);
        }
        LocalDate date = ParserUtil.parseDate(individualArgs[1]);
        return new CalendarDateDisplayEngagementsCommand(date);
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

}
