package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class DeleteFinCommandParserTest {

    private DeleteFinCommandParser parser = new DeleteFinCommandParser();

    @Test
    void parser_indexPresent_success() throws ParseException {
        final String USER_INPUT = " 1";
        parser.parse(USER_INPUT);
    }

    @Test
    void parser_indexAbsence_failure() throws ParseException {
        final String USER_INPUT = " ";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT));
    }
}
