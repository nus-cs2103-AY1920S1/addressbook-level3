package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class EditFinCommandParserTest {

    private EditFinCommandParser parser = new EditFinCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // fields missing
        final String USER_INPUT1 = " 1";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        // index missing
        final String USER_INPUT2 = " a/4";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        // all missing
        final String USER_INPUT3 = " ";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-positive index
        final String USER_INPUT1 = " 0";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        // invalid field
        final String USER_INPUT2 = " 1 abc";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        // invalid prefix
        final String USER_INPUT3 = " 1 unknownPrefix/4";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));
    }

    @Test
    public void parse_invalidValue_failure() {
        // negative amount
        final String USER_INPUT1 = " 1 a/4.000 d/breakfast t/Food";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        // invalid time
        final String USER_INPUT3 = " 1 a/4 d/breakfast t/Food time/9999 date/27102016";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));

        // invalid date
        final String USER_INPUT5 = " 1 a/4 d/breakfast t/Food time/1212 date/271020166";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT5));
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        final String USER_INPUT1 = " 1 a/4 d/breakfast t/Food time/1212 date/27102016";
        parser.parse(USER_INPUT1);

        final String USER_INPUT2 = " 1 d/breakfast";
        parser.parse(USER_INPUT2);

        final String USER_INPUT3 = " 1 t/Food";
        parser.parse(USER_INPUT3);

        final String USER_INPUT4 = " 1 time/1212";
        parser.parse(USER_INPUT4);

        final String USER_INPUT5 = " 1 date/27102016";
        parser.parse(USER_INPUT5);

        final String USER_INPUT6 = " 1 a/4";
        parser.parse(USER_INPUT6);
    }

}
