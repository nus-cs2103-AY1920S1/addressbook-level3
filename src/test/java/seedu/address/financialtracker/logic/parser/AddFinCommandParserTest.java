package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;

class AddFinCommandParserTest {

    private AddFinCommandParser parser = new AddFinCommandParser();

    @Test
    void parser_allFieldsPresent_success() throws ParseException {
        final String USER_INPUT = " a/4 d/breakfast t/Food date/27102016 time/1720";
        parser.parse(USER_INPUT);
    }

    @Test
    void parser_optionalFieldsAbsence_success() throws ParseException {
        final String USER_INPUT = " a/4 d/breakfast t/Food date/27102016 time/1720";
        parser.parse(USER_INPUT);
    }

    @Test
    void parser_compulsoryFieldMissing_failure() {
        final String USER_INPUT1 = "a/4 t/Food date/27102016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        final String USER_INPUT2 = "d/breakfast t/Food date/27102016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        final String USER_INPUT3 = "a/4 d/breakfast";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));

        final String USER_INPUT4 = "date/27102016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT4));
    }

    @Test
    void parser_invalidValue_failure() {
        // amount invalid
        final String USER_INPUT1 = "a/4.000 d/breakfast t/Food date/27102016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        // description invalid
        final String USER_INPUT2 = "a/4 d/@#$%breakfast-- t/Food date/27102016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        // type invalid
        final String USER_INPUT3 = "a/4 d/breakfast t/100";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));

        // date invalid
        final String USER_INPUT4 = "a/4 d/breakfast t/Food date/2710162";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT4));

        //time invalid
        final String USER_INPUT5 = "a/4 d/breakfast t/Food date/271016 time/234";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT5));
    }

}
