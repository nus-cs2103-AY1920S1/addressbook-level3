package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class CalendarDayParserTest {

    private static final CalendarDayParser PARSER = new CalendarDayParser();

    @Test
    void parse_invalidDay_failure() {
        String[] tests = { "", " ", "01010001", "1/1/1", "29/02/2019" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validDay_success() {
        String[] tests = { "01/01/0001", "31/12/9999" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
