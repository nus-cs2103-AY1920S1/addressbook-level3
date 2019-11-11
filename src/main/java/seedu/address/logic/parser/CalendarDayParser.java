package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CALENDAR_DAY;
import static seedu.address.model.CalendarDate.CALENDAR_DAY_PATTERN;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;

//@@author Kyzure
/**
 * Represents a {@link Parser} that can parse user input into a {@link CalendarDate} object.
 */
public class CalendarDayParser implements Parser<CalendarDate> {

    private static final DateTimeFormatter PARSER = DateTimeFormatter.ofPattern(CALENDAR_DAY_PATTERN)
        .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public CalendarDate parse(String userInput) throws ParseException {
        try {
            return new CalendarDate(LocalDate.parse(userInput, PARSER));
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_CALENDAR_DAY);
        }
    }
}
