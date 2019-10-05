package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class CommandParserTest {

    @Test
    void parse_validCommand_success() {
        String test = "add_event \"Celebrate Rori’s Birthday\" \"18/08/2019 16:00\" "
            + "--end \"18/08/2019 20:00\"";
        assertDoesNotThrow(() -> new CommandParser().parse(test));
    }
}
