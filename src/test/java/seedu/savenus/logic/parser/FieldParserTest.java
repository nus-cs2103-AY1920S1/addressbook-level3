package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.DESCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.parser.exceptions.ParseException;

public class FieldParserTest {
    private String invalidField;
    private String invalidDirection;
    private FieldParser fieldParser;

    @BeforeEach
    public void setUp() {
        invalidField = "@@@";
        invalidDirection = "@@@";
        fieldParser = new FieldParser();
    }

    @Test
    public void check_keywords_test() {
        String invalidFieldsMessage = FieldParser.INVALID_FIELD_USAGE;

        List<String> keywords = new ArrayList<>();
        keywords.add(invalidField);
        keywords.add(invalidDirection);
        assertThrows(ParseException.class,
                invalidFieldsMessage, () -> fieldParser.checkKeywords(keywords));
    }

    @Test
    public void parse_invalidFields_failure() {
        String invalidFieldsMessage = FieldParser.INVALID_FIELD_USAGE;
        String wrongCaseFieldsMessage = FieldParser.WRONG_CASE_FIELD_USAGE;

        assertThrows(ParseException.class,
                invalidFieldsMessage, () -> fieldParser.parse(invalidField + " " + ASCENDING_DIRECTION));

        assertThrows(ParseException.class,
                wrongCaseFieldsMessage, () -> fieldParser.parse(
                        FIELD_NAME_OPENING_HOURS + " " + DESCENDING_DIRECTION
                                + " pRicE " + ASCENDING_DIRECTION
                ));
    }

    @Test
    public void parse_duplicateFields_failure() {
        String duplicateFieldsMessage = FieldParser.DUPLICATE_FIELD_USAGE;

        assertThrows(ParseException.class,
                duplicateFieldsMessage, () -> fieldParser.parse(
                        FIELD_NAME_PRICE + " " + DESCENDING_DIRECTION
                                + " " + FIELD_NAME_PRICE + " " + ASCENDING_DIRECTION
                ));

        assertThrows(ParseException.class,
                duplicateFieldsMessage, () -> fieldParser.parse(
                        FIELD_NAME_RESTRICTIONS + " " + ASCENDING_DIRECTION
                                + " " + FIELD_NAME_RESTRICTIONS + " " + DESCENDING_DIRECTION
                ));
    }

    @Test
    public void parse_invalidDirections_failure() {
        String invalidDirectionMessage = FieldParser.INVALID_DIRECTION_USAGE;
        String wrongCaseDirectionMessage = fieldParser.WRONG_CASE_DIRECTION_USAGE;
        assertThrows(ParseException.class,
                wrongCaseDirectionMessage, () -> fieldParser.parse(
                        FIELD_NAME_NAME + " " + DESCENDING_DIRECTION
                                + " " + FIELD_NAME_LOCATION + " " + "deSc"
                ));

        assertThrows(ParseException.class,
                invalidDirectionMessage, () -> fieldParser.parse(
                        FIELD_NAME_DESCRIPTION + " " + invalidDirection
                ));
    }

    @Test
    public void parse_missingDirections_failure() {
        String missingDirectionMessage = FieldParser.MISSING_DIRECTION_USAGE;
        assertThrows(ParseException.class,
                missingDirectionMessage, () -> fieldParser.parse(
                        FIELD_NAME_NAME + " " + DESCENDING_DIRECTION
                                + " " + FIELD_NAME_CATEGORY
                ));

        assertThrows(ParseException.class,
                missingDirectionMessage, () -> fieldParser.parse(
                        FIELD_NAME_DESCRIPTION
                ));
    }

}
