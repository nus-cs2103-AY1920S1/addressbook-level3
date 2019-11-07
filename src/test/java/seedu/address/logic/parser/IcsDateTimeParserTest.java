package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class IcsDateTimeParserTest {

    private static final IcsDateTimeParser PARSER = new IcsDateTimeParser();

    @Test
    void parse_invalidDateTime_failure() {
        String[] tests = { "", " ", "29022019T000000Z" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validDateTime_success() {
        String[] tests = { "00010101T000000Z", "99991231T235900Z" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
