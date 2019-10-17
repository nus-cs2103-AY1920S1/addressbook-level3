package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDateTime;

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