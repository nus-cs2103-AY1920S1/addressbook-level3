package seedu.address.calendar.parser;

import seedu.address.calendar.model.Year;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

class YearParser {
    private static final String MESSAGE_NEGATIVE_YEAR_ERROR = "Invalid year. Year should be a positive number.";
    private static final String MESSAGE_NON_INT_YEAR_ERROR = "Invalid year. Year should ba represented numerically.";

    Year parse(int year) throws ParseException {
        if (year < 0) {
            throw new ParseException(MESSAGE_NEGATIVE_YEAR_ERROR);
        }

        return new Year(year);
    }

    Optional<Year> parse(Optional<String> yearInput) throws ParseException {
        if (yearInput.isEmpty()) {
            return Optional.empty();
        }

        try {
            int year = Integer.parseInt(yearInput.get());
            return Optional.of(parse(year));
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_NON_INT_YEAR_ERROR);
        }
    }
}
