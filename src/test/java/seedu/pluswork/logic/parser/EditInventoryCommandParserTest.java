package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_NAME_DESC_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_SPORTS;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.ParserUtil.MESSAGE_INVALID_PRICE;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.EditInventoryCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.inventory.InvName;

public class EditInventoryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditInventoryCommand.MESSAGE_USAGE);

    private EditInventoryCommandParser parser = new EditInventoryCommandParser();

    @Test
    public void parse_missingParts_failure() throws CommandException {
        // no index specified
        assertParseFailure(parser, "edit-inv " + VALID_INVENTORY_NAME_SPORTS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "edit-inv ii/1 ", EditInventoryCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "edit-task ii/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws CommandException {
        // negative index
        assertParseFailure(parser, "edit-task ii/-5" + INVENTORY_NAME_DESC_SPORTS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "edit-inv ii/0" + INVENTORY_NAME_DESC_SPORTS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "edit-inv ii/1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "edit-inv ii/1 t/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException {
        assertParseFailure(parser, "edit-inv ii/1 "
                + "i/&12toys34&", InvName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "edit-inv ii/1 "
                + "p/hello", MESSAGE_INVALID_PRICE); // invalid price
    }
}
