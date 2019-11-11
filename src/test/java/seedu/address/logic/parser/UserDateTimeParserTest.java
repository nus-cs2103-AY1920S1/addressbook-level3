package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class UserDateTimeParserTest {

    private static final UserDateTimeParser PARSER = new UserDateTimeParser();

    @Test
    void parse_invalidDateTime_failure() {
        String[] tests = { "", " ", "29/02/2019 00:00" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validDateTime_success() {
        String[] tests = { "01/01/0001 00:00", "31/12/9999 23:59" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
