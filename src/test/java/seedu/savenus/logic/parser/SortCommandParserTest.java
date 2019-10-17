package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.DESCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;

import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.SortCommand;

public class SortCommandParserTest {
    private String invalidField = "2323";
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArguments_failure() {
        String noArgumentsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.NO_ARGUMENTS_USAGE);

        // Empty Line
        assertParseFailure(parser, "",
                 noArgumentsMessage);

        // Empty Line with Spaces
        assertParseFailure(parser, "      ",
                noArgumentsMessage);

        // Empty Line with tons of Spaces
        assertParseFailure(parser, "                          ",
                noArgumentsMessage);
    }

    @Test
    public void parse_invalidFields_failure() {
        String invalidFieldsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.INVALID_FIELD_USAGE);

        assertParseFailure(parser, invalidField + " " + ASCENDING_DIRECTION,
                invalidFieldsMessage);

        assertParseFailure(parser, invalidField + ASCENDING_DIRECTION + invalidField + ASCENDING_DIRECTION,
                invalidFieldsMessage);

        assertParseFailure(parser, FIELD_NAME_NAME + " " + ASCENDING_DIRECTION + " " + invalidField,
                invalidFieldsMessage);
    }

    @Test
    public void parse_duplicateFields_failure() {
        String duplicateFieldsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.DUPLICATE_FIELD_USAGE);

        assertParseFailure(parser, FIELD_NAME_NAME + " " + ASCENDING_DIRECTION + " " + FIELD_NAME_NAME,
                duplicateFieldsMessage);

        assertParseFailure(parser, FIELD_NAME_NAME + " " + ASCENDING_DIRECTION + " "
                        + FIELD_NAME_NAME + " " + DESCENDING_DIRECTION,
                duplicateFieldsMessage);

    }

    @Test
    public void parse_missingDirections_failure() {
        String missingDirectionsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MISSING_DIRECTION_USAGE);

        assertParseFailure(parser, FIELD_NAME_OPENING_HOURS,
                missingDirectionsMessage);

        assertParseFailure(parser, FIELD_NAME_LOCATION + " " + DESCENDING_DIRECTION + " " + FIELD_NAME_PRICE,
                missingDirectionsMessage);
    }

    @Test
    public void parse_invalidDirections_failure() {
        String invalidDirectionsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.INVALID_DIRECTION_USAGE);

        assertParseFailure(parser, FIELD_NAME_DESCRIPTION + " " + FIELD_NAME_RESTRICTIONS,
                invalidDirectionsMessage);

        assertParseFailure(parser, FIELD_NAME_RESTRICTIONS + " " + ASCENDING_DIRECTION + " "
                + FIELD_NAME_CATEGORY + " " + invalidField, invalidDirectionsMessage);
    }

    @Test
    public void parse_validField() {
        assertTrue(parser.isValidField(FIELD_NAME_CATEGORY));
        assertTrue(parser.isValidField(FIELD_NAME_DESCRIPTION));
        assertTrue(parser.isValidField(FIELD_NAME_NAME));
        assertTrue(parser.isValidField(FIELD_NAME_RESTRICTIONS));

        assertFalse(parser.isValidField(invalidField));
    }

    @Test
    public void parse_validDirection() {
        assertTrue(parser.isAscOrDesc(ASCENDING_DIRECTION));
        assertTrue(parser.isAscOrDesc(DESCENDING_DIRECTION));

        assertFalse(parser.isAscOrDesc(invalidField));
        assertFalse(parser.isAscOrDesc(FIELD_NAME_RESTRICTIONS));
    }

}
