package seedu.elisa.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.elisa.logic.parser.exceptions.MidnightParseException;
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
        LocalDateTime parsed;
        try {
            parsed = LocalDateTime.parse(stringDateTime);
            return parsed;
        } catch (DateTimeParseException e) {
            if (stringDateTime.contains("24:00")) {
                throw new MidnightParseException("00:00");
            }
            throw new ParseException("Date Time format given is incorrect."
                    + " Should be \"2019-12-03T23:00\"");
        }
    }
}
