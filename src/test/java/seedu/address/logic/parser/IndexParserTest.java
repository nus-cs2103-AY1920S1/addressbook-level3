package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class IndexParserTest {

    private static final IndexParser PARSER = new IndexParser();

    @Test
    void parse_invalidIndex_failure() {
        String[] tests = { "", " ", "1 1", "-1", "2147483648" };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> PARSER.parse(test));
        }
    }

    @Test
    void parse_validIndex_success() {
        String[] tests = { "0", "2147483647" };
        for (String test : tests) {
            assertDoesNotThrow(() -> PARSER.parse(test));
        }
    }
}
