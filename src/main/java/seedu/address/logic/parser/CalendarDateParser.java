package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CALENDAR_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;

/**
 * Represents a {@link Parser} that can parse user input into a {@link DateTime} object.
 */
public class CalendarDateParser implements Parser<CalendarDate> {

    private String pattern;

    public CalendarDateParser(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public CalendarDate parse(String userInput) throws ParseException {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(this.pattern);
            LocalDate localDate = LocalDate.from(dateTimeFormatter.parse(userInput));
            return new CalendarDate(localDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_CALENDAR_DATE, pattern));
        }
    }
}
