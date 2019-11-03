package seedu.elisa.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parse string Date Time in the standard way using LocalDateTime.parse().
 */
public class StandardDateTimeParser implements DateTimeParser {

    /**
     * Parse string using LocalDateTime.parse()
     * @param stringDateTime of the unprocessed date time string
     * @return LocalDateTime representation of this string
     * @throws DateTimeParseException if the format of this string is incorrect
     */
    public LocalDateTime parseDateTime(String stringDateTime) throws ParseException {
        try {
            return LocalDateTime.parse(stringDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Date Time format given is incorrect."
                    + " Should be \"25-09-2019T23:00\"");
        }
    }
}
