package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.FilterCommand;

public class FilterCommandParserTest {
    private FilterCommandParser parser;
    private String properArguments;

    @BeforeEach
    public void set_up() {
        parser = new FilterCommandParser();
        properArguments = FIELD_NAME_PRICE + " " + QUANTIFY_MORE_THAN + " 4.00";

    }

    @Test
    public void parse_emptyArguments_failure() {
        String noArgumentsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.NO_ARGUMENTS_USAGE);
        // Empty Line
        assertParseFailure(parser, "", noArgumentsMessage);

        // Empty Line with Spaces
        assertParseFailure(parser, "      ", noArgumentsMessage);

        // Empty Line with tons of Spaces
        assertParseFailure(parser, "                          ", noArgumentsMessage);
    }

    @Test
    public void poarse_wrongArguments_failure() {
        String wrongArgumentsMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.WRONG_ARGUMENT_NUMBER);

        assertParseFailure(parser, FIELD_NAME_CATEGORY, wrongArgumentsMessage);
        assertParseFailure(parser, FIELD_NAME_CATEGORY + " " + QUANTIFY_LESS_THAN, wrongArgumentsMessage);
        assertParseFailure(parser, FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN, wrongArgumentsMessage);
    }

    @Test
    public void parse_invalidField_failure() {
        String invalidFieldMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.INVALID_FIELD_USAGE);

        assertParseFailure(parser, "OOPS " + QUANTIFY_LESS_THAN + " 321", invalidFieldMessage);
        assertParseFailure(parser, "zasbd " + QUANTIFY_LESS_THAN + " 321", invalidFieldMessage);
        assertParseFailure(parser, "111 " + QUANTIFY_LESS_THAN + " 321", invalidFieldMessage);
    }

    @Test
    public void parse_invalidQuantifiers_failure() {
        String invalidQuantifiersMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.INVALID_QUANTIFIER_USAGE
        );

        assertParseFailure(parser, FIELD_NAME_PRICE + " less 321", invalidQuantifiersMessage);
        assertParseFailure(parser, FIELD_NAME_PRICE + " equals 4.00", invalidQuantifiersMessage);
        assertParseFailure(parser, FIELD_NAME_CATEGORY + " less 321", invalidQuantifiersMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        String invalidValuesMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.INVALID_VALUE_USAGE
        );

        assertParseFailure(parser, FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " eheh",
            invalidValuesMessage);
        assertParseFailure(parser, FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " 4.23847",
            invalidValuesMessage);
    }

    @Test
    public void parse_duplicateFields_failure() {
        String invalidValuesMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.DUPLICATE_FIELD_USAGE
        );

        assertParseFailure(parser, FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " 4.20 "
            + FIELD_NAME_PRICE + " " + QUANTIFY_LESS_THAN + " 4.00",
            invalidValuesMessage);
    }

    @Test
    public void check_validValues() {
        assertTrue(parser.isValidValue(FIELD_NAME_PRICE, "4.00"));
        assertTrue(parser.isValidValue(FIELD_NAME_PRICE, "4"));
        assertFalse(parser.isValidValue(FIELD_NAME_PRICE, "eheh"));
        assertFalse(parser.isValidValue(FIELD_NAME_PRICE, "oeoeiie"));
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
        assertTrue(parser.isValidField(FIELD_NAME_CATEGORY));
        assertFalse(parser.isValidField("4.20"));
    }

    @Test
    public void test_duplicate_fields() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.00"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, QUANTIFY_LESS_THAN, "400"};
        assertTrue(parser.areFieldsDuplicate(nonLegitFields));
        assertFalse(parser.areFieldsDuplicate(legitFields));
    }

    @Test
    public void test_values_validity() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, QUANTIFY_LESS_THAN, "4.00"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, QUANTIFY_LESS_THAN, "400"};
        assertTrue(parser.areValuesInvalid(nonLegitFields));
        assertFalse(parser.areValuesInvalid(legitFields));
    }

    @Test
    public void test_quantifiers_validity() {
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, "LOL", "400"};
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, QUANTIFY_LESS_THAN, "400"};
        assertTrue(parser.areQuantifiersInvalid(nonLegitFields));
        assertFalse(parser.areQuantifiersInvalid(legitFields));
    }

    @Test
    public void test_fields_validity() {
        String[] legitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            FIELD_NAME_CATEGORY, "QUANTIFY_LESS_THAN", "400"};
        String[] nonLegitFields = {FIELD_NAME_PRICE, QUANTIFY_LESS_THAN, "4.20",
            "PIKACHU", QUANTIFY_LESS_THAN, "400"};
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
