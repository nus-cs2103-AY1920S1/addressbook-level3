package seedu.address.logic.parser;

import static seedu.address.MainApp.TIME_ZONE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.model.DateTime.USER_DATE_TIME_PATTERN;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;

/**
 * Represents a {@link Parser} that can parse user input dates into a {@link DateTime} object.
 */
public class UserDateTimeParser implements Parser<DateTime> {

    private static final DateTimeFormatter PARSER = DateTimeFormatter.ofPattern(USER_DATE_TIME_PATTERN)
        .withZone(TIME_ZONE)
        .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public DateTime parse(String userInput) throws ParseException {
        try {
            Instant instant = Instant.from(PARSER.parse(userInput));
            return DateTime.newBuilder(instant).build();
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME);
        }
    }
}
