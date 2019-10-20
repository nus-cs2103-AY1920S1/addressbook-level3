package seedu.address.calendar.parser;

import seedu.address.calendar.model.Year;
import seedu.address.logic.parser.exceptions.ParseException;

class YearParser {
    private static final String MESSAGE_NEGATIVE_YEAR_ERROR = "Invalid year. Year should be a positive number.";
    private static final String MESSAGE_NON_INT_YEAR_ERROR = "Invalid year. Year should ba represented numerically.";

    Year parse(int year) throws ParseException {
        if (year < 0) {
            throw new ParseException(MESSAGE_NEGATIVE_YEAR_ERROR);
        }

        return new Year(year);
    }

    Year parse(String yearStr) throws ParseException {
        try {
            int year = Integer.parseInt(yearStr);
            return parse(year);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_NON_INT_YEAR_ERROR);
        }
    }
}
