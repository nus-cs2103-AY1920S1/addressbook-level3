package seedu.address.calendar.logic.parser;

import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

class YearParser {
    private static final String MESSAGE_NON_INT_YEAR_ERROR = "Invalid year. Year should be represented numerically.";
    private static final String YEAR_EXTRA_ARG = "after year";

    Year parse(int year) throws ParseException {
        try {
            return new Year(year);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
    }

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
