package seedu.address.calendar.logic.parser;

import java.util.Optional;

import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses year.
 */
class YearParser {
    private static final String MESSAGE_NON_INT_YEAR_ERROR = "Invalid year. Year should be represented numerically.";
    private static final String YEAR_EXTRA_ARG = "after year";

    /**
     * Parses a year that is represented by an {@code int}.
     *
     * @param year The representative {@code int}.
     * @return The required year
     * @throws ParseException If the parsing cannot be done successfully
     */
    Year parse(int year) throws ParseException {
        try {
            return new Year(year);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a year input that is represented by a {@code String}.
     *
     * @param yearInput The representative {@code String}.
     * @return The required year
     * @throws ParseException If the parsing cannot be done successfully
     */
    Optional<Year> parse(Optional<String> yearInput) throws ParseException {
        if (yearInput.isEmpty()) {
            return Optional.empty();
        }

        String yearInputStr = yearInput.get().trim();

        if (ParserUtil.hasCharInValue(yearInputStr)) {
            throw new ParseException(String.format(ParserUtil.MESSAGE_ARG_EXTRA, YEAR_EXTRA_ARG));
        }

        try {
            int year = Integer.parseInt(yearInputStr);
            return Optional.of(parse(year));
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_NON_INT_YEAR_ERROR);
        }
    }
}
