package io.xpire.storage;

import static io.xpire.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.commons.util.JsonUtil;
import io.xpire.model.ExpiryDateTracker;
import io.xpire.testutil.TypicalItems;

public class JsonSerializableExpiryDateTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsExpiryDateTracker.json");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemExpiryDateTracker.json");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemExpiryDateTracker.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableExpiryDateTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_ITEMS_FILE,
                JsonSerializableExpiryDateTracker.class).get();
        ExpiryDateTracker expiryDateTrackerFromFile = dataFromFile.toModelType();
        ExpiryDateTracker typicalItemsExpiryDateTracker = TypicalItems.getTypicalExpiryDateTracker();
        assertEquals(expiryDateTrackerFromFile, typicalItemsExpiryDateTracker);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExpiryDateTracker dataFromFile = JsonUtil.readJsonFile(INVALID_ITEM_FILE,
                JsonSerializableExpiryDateTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableExpiryDateTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ITEM_FILE,
                JsonSerializableExpiryDateTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExpiryDateTracker.MESSAGE_DUPLICATE_ITEM,
                dataFromFile::toModelType);
    }

}
