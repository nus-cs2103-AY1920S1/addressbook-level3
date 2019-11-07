package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class CalendarMonthParserTest {

    private static final CalendarMonthParser PARSER = new CalendarMonthParser();

    @Test
    void parse_invalidMonth_failure() {
        String[] tests = { "", " ", "010001", "1/1", "13/2019" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validMonth_success() {
        String[] tests = { "01/0001", "12/9999" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
