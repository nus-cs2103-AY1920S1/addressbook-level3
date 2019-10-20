package seedu.address.calendar.parser;

import seedu.address.calendar.model.Year;
import seedu.address.logic.parser.exceptions.ParseException;

class YearParser {
    private static final String YEAR_ERROR_MESSAGE = "Invalid year. Year should be a positive number.";

    Year parse(int year) throws ParseException {
        if (year < 0) {
            throw new ParseException(YEAR_ERROR_MESSAGE);
        }

        return new Year(year);
    }
}
