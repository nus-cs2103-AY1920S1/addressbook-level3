package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class InventoryViewParserTest {

    private static final String INVALID_DIARY_COMMAND = "ABC";

    @Test
    void parse_invalidInventoryCommand_throwsParseException() {
        InventoryViewParser inventoryViewParser = new InventoryViewParser();
        try {
            inventoryViewParser.parse(INVALID_DIARY_COMMAND, "");
        } catch (ParseException ex) {
            assertEquals(
                    String.format(MESSAGE_INVALID_COMMAND_TYPE, InventoryViewParser.MESSAGE_COMMAND_TYPES),
                    ex.getMessage());
        }
    }
}
