package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.logic.commands.AddFinCommand;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.Command;
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
        final String USER_INPUT1 = "a/4 t/Food date/271016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        final String USER_INPUT2 = "d/breakfast t/Food date/271016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        final String USER_INPUT3 = "a/4 d/breakfast";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));

        final String USER_INPUT4 = "date/271016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT4));
    }

    @Test
    void parser_invalidValue_failure() {
        final String USER_INPUT1 = "a/4.000 d/breakfast t/Food date/271016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT1));

        final String USER_INPUT2 = "a/4 d/@#$%breakfast-- t/Food date/271016 time/1720";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT2));

        final String USER_INPUT3 = "a/4 d/breakfast t/100";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT3));

        final String USER_INPUT4 = "a/4 d/breakfast t/Food date/2710162";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT4));

        final String USER_INPUT5 = "a/4 d/breakfast t/Food date/271016 time/234";
        assertThrows(ParseException.class, () -> parser.parse(USER_INPUT5));
    }

}