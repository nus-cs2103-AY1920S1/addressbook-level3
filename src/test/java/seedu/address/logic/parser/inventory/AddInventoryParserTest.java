package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.model.inventory.Name;

class AddInventoryParserTest {

    @Test
    void parse_inputNull_throwsNullPointer() {
        AddInventoryParser createDiaryEntryParser = new AddInventoryParser();
        assertThrows(NullPointerException.class, () -> createDiaryEntryParser.parse(null));
    }

    @Test
    void parse_whiteSpace_throwsParseException() {
        assertParseFailure(new AddInventoryParser(), " ", Name.MESSAGE_CONSTRAINTS);
    }


}
