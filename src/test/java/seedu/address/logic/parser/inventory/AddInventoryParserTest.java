package seedu.address.logic.parser.inventory;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import seedu.address.model.inventory.Name;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

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