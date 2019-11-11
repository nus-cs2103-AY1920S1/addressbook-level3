package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CALENDAR_MONTH;
import static seedu.address.model.CalendarDate.USER_CALENDAR_MONTH_PATTERN;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;

//@@author Kyzure
/**
 * Represents a {@link Parser} that can parse user input into a {@link CalendarDate} object.
 */
public class CalendarMonthParser implements Parser<CalendarDate> {

    private static final DateTimeFormatter PARSER = DateTimeFormatter.ofPattern(USER_CALENDAR_MONTH_PATTERN)
        .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public CalendarDate parse(String userInput) throws ParseException {
        try {
            return new CalendarDate(YearMonth.parse(userInput, PARSER).atDay(1));
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_CALENDAR_MONTH);
        }
    }
}
