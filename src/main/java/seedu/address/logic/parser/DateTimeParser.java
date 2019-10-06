package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.DateTime;

/**
 * Represents a {@link Parser} that can parse user input into a {@link DateTime} object.
 */
public class DateTimeParser implements Parser<DateTime> {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final DateTimeFormatter DATE_TIME_PARSER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        .withZone(ZoneId.systemDefault());

    @Override
    public DateTime parse(String userInput) throws ParseException {
        try {
            Instant instant = Instant.from(DATE_TIME_PARSER.parse(userInput));
            return new DateTime(instant);
        } catch (DateTimeParseException e) {
            System.out.println(userInput);
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_TIME, DATE_TIME_PATTERN));
        }
    }
}
