package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.parser.exceptions.ParseException;

public class QuantifierParserTest {

    private QuantifierParser parser = new QuantifierParser();

    @Test
    public void parse_emptyArguments_failure() {
        String emptyLine = "               ";
        assertThrows(ParseException.class,
                QuantifierParser.NO_ARGUMENTS_USAGE, () -> parser.parse(emptyLine));
    }

    @Test
    public void poarse_wrongArguments_failure() {
        assertThrows(ParseException.class,
                QuantifierParser.WRONG_ARGUMENT_NUMBER, () -> parser.parse(FIELD_NAME_PRICE));
    }

    @Test
    public void parse_invalidField_failure() {
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_FIELD_USAGE, () -> parser.parse("OOPS "
                        + QUANTIFY_LESS_THAN + " 321"));
        assertThrows(ParseException.class,
                QuantifierParser.WRONG_CASE_FIELD_USAGE, () -> parser.parse("prIcE "
                        + QUANTIFY_LESS_THAN + " 321"));
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_FIELD_USAGE, () -> parser.parse("zasbd "
                        + QUANTIFY_LESS_THAN + " 321"));
    }

    @Test
    public void parse_invalidQuantifiers_failure() {
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_QUANTIFIER_USAGE, () -> parser.parse(FIELD_NAME_PRICE + " less 321"));
        assertThrows(ParseException.class,
                QuantifierParser.WRONG_CASE_QUANTIFIER_USAGE, () -> parser.parse(FIELD_NAME_PRICE
                        + " leSS_tHaN 321"));
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_QUANTIFIER_USAGE, () -> parser.parse(FIELD_NAME_PRICE + " equals 4.00"));
    }

    @Test
    public void parse_invalidValues_failure() {
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_VALUE_USAGE, () -> parser.parse(FIELD_NAME_PRICE
                        + " " + QUANTIFY_LESS_THAN + " eheh"));
        assertThrows(ParseException.class,
                QuantifierParser.INVALID_VALUE_USAGE, () -> parser.parse(FIELD_NAME_PRICE
                        + " " + QUANTIFY_LESS_THAN + " 4.2322"));
    }

    @Test
    public void parse_duplicateFields_failure() {
        assertThrows(ParseException.class,
                QuantifierParser.DUPLICATE_FIELD_USAGE, () -> parser.parse(FIELD_NAME_PRICE
                        + " " + QUANTIFY_LESS_THAN + " 4.20 "
                        + FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " 4.00"));
    }

    @Test
    public void check_validValues() {
        assertTrue(parser.isValidValue(FIELD_NAME_PRICE, "4.00"));
        assertTrue(parser.isValidValue(FIELD_NAME_PRICE, "4"));
        assertFalse(parser.isValidValue(FIELD_NAME_PRICE, "oeoeiie"));
        assertFalse(parser.isValidValue("Invalid Field", "0000 0001"));
    }

    @Test
    public void check_validQuantifier() {
        assertTrue(parser.isValidQuantifier(QUANTIFY_LESS_THAN));
        assertTrue(parser.isValidQuantifier(QUANTIFY_EQUALS_TO));
        assertFalse(parser.isValidQuantifier("hehe"));
        assertFalse(parser.isValidQuantifier("12345"));
    }

    @Test
    public void check_validFields() {
        assertTrue(parser.isValidField(FIELD_NAME_PRICE));
        assertFalse(parser.isValidField(FIELD_NAME_CATEGORY));
        assertFalse(parser.isValidField("4.20"));
    }

    @Test
    public void test_duplicate_fields() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20", FIELD_NAME_PRICE,
            QUANTIFY_LESS_THAN, "4.00"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20"};
        assertTrue(parser.areFieldsDuplicate(nonLegitFields));
        assertFalse(parser.areFieldsDuplicate(legitFields));
    }

    @Test
    public void test_values_validity() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.201"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20"};
        assertTrue(parser.areValuesInvalid(nonLegitFields));
        assertFalse(parser.areValuesInvalid(legitFields));
    }

    @Test
    public void test_quantifiers_validity() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, "jeje", "4.20"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20"};
        assertTrue(parser.areQuantifiersInvalid(nonLegitFields));
        assertFalse(parser.areQuantifiersInvalid(legitFields));
    }

    @Test
    public void test_fields_validity() {
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20"};
        String[] nonLegitFields = {FIELD_NAME_PRICE + "heh", QUANTIFY_LESS_THAN, "4.20"};
        assertTrue(parser.areFieldsInvalid(nonLegitFields));
        assertFalse(parser.areFieldsInvalid(legitFields));
    }

    @Test
    public void test_fields_length() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, QUANTIFY_LESS_THAN, "400"};
        assertTrue(parser.isWrongArgumentNumber(nonLegitFields));
        assertFalse(parser.isWrongArgumentNumber(legitFields));
    }
}
