package seedu.address.logic.parser.inventory;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.EnterInventoryCommand;

class EnterInventoryParserTest {

    @Test
    void parse_inputNull_returnsEnterInventoryCommand() {
        assertParseSuccess(new EnterInventoryParser(), null, new EnterInventoryCommand());
    }

    @Test
    void parse_inputNotNull_returnsEnterInventoryCommand() {
        assertParseSuccess(new EnterInventoryParser(), "abcd", new EnterInventoryCommand());
    }
}
