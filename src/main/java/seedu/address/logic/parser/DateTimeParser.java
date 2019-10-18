package seedu.address.logic.parser;

import java.time.LocalDateTime;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse DateTime input into a {@code LocalDateTime}.
 */
public interface DateTimeParser {
    /**
     * Parses {@code stringDateTime} into a LocalDateTime and returns it
     * @param stringDateTime of the unprocessed date time string
     * @return LocalDateTime representation of the given string
     */
    LocalDateTime parseDateTime(String stringDateTime) throws ParseException;
}
