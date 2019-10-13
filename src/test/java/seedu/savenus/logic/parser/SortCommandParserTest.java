package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.commands.CommandTestUtil.ASC_DIRECTION;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.DESC_DIRECTION;
import static seedu.savenus.logic.commands.CommandTestUtil.INVALID_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_FIELD;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.SortCommand;

public class SortCommandParserTest {
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

        assertParseFailure(parser, INVALID_FIELD + " " + ASC_DIRECTION,
                invalidFieldsMessage);

        assertParseFailure(parser, INVALID_FIELD + ASC_DIRECTION + INVALID_FIELD + ASC_DIRECTION,
                invalidFieldsMessage);

        assertParseFailure(parser, NAME_FIELD + " " + ASC_DIRECTION + " " + INVALID_FIELD,
                invalidFieldsMessage);
    }

    @Test
    public void parse_duplicateFields_failure() {
        String duplicateFieldsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.DUPLICATE_FIELD_USAGE);

        assertParseFailure(parser, NAME_FIELD + " " + ASC_DIRECTION + " " + NAME_FIELD,
                duplicateFieldsMessage);

        assertParseFailure(parser, NAME_FIELD + " " + ASC_DIRECTION + " " + NAME_FIELD
                + " " + ASC_DIRECTION,
                duplicateFieldsMessage);

    }

    @Test
    public void parse_missingDirections_failure() {
        String missingDirectionsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MISSING_DIRECTION_USAGE);

        assertParseFailure(parser, NAME_FIELD,
                missingDirectionsMessage);

        assertParseFailure(parser, NAME_FIELD + " " + DESC_DIRECTION + " " + PRICE_FIELD,
                missingDirectionsMessage);
    }

    @Test
    public void parse_invalidDirections_failure() {
        String invalidDirectionsMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.INVALID_DIRECTION_USAGE);

        assertParseFailure(parser, NAME_FIELD + " " + PRICE_FIELD,
                invalidDirectionsMessage);

        assertParseFailure(parser, NAME_FIELD + " " + ASC_DIRECTION + " " + PRICE_FIELD + " " + INVALID_FIELD,
                invalidDirectionsMessage);
    }

    @Test
    public void parse_validField() {
        assertTrue(parser.isValidField(NAME_FIELD));
        assertTrue(parser.isValidField(PRICE_FIELD));
        assertTrue(parser.isValidField(DESCRIPTION_FIELD));
        assertTrue(parser.isValidField(CATEGORY_FIELD));

        assertFalse(parser.isValidField(INVALID_FIELD));
    }

    @Test
    public void parse_validDirection() {
        assertTrue(parser.isAscOrDesc(ASC_DIRECTION));
        assertTrue(parser.isAscOrDesc(DESC_DIRECTION));

        assertFalse(parser.isAscOrDesc(INVALID_FIELD));
        assertFalse(parser.isAscOrDesc(NAME_FIELD));
    }

}
