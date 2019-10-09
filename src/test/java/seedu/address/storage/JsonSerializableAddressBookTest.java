package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalFood;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_foodS_FILE = TEST_DATA_FOLDER.resolve("typicalFoodAddressBook.json");
    private static final Path INVALID_food_FILE = TEST_DATA_FOLDER.resolve("invalidFoodAddressBook.json");
    private static final Path DUPLICATE_food_FILE = TEST_DATA_FOLDER.resolve("duplicateFoodAddressBook.json");

    @Test
    public void toModelType_invalidfoodFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_food_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalfoodsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_foodS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalfoodsAddressBook = TypicalFood.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalfoodsAddressBook);
    }

    @Test
    public void toModelType_duplicatefoods_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_food_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_FOOD,
                dataFromFile::toModelType);
    }

}
