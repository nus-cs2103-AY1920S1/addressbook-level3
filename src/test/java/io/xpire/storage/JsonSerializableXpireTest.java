package io.xpire.storage;

import static io.xpire.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.commons.util.JsonUtil;
import io.xpire.model.Xpire;
import io.xpire.testutil.TypicalItems;

public class JsonSerializableXpireTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableXpireTest");
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsXpire.json");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemXpire.json");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemXpire.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableXpire dataFromFile = JsonUtil.readJsonFile(TYPICAL_ITEMS_FILE,
                JsonSerializableXpire.class).get();
        Xpire xpireFromFile = dataFromFile.toModelType();
        Xpire typicalItemsXpire = TypicalItems.getTypicalExpiryDateTracker();
        assertEquals(xpireFromFile, typicalItemsXpire);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableXpire dataFromFile = JsonUtil.readJsonFile(INVALID_ITEM_FILE,
                JsonSerializableXpire.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableXpire dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ITEM_FILE,
                JsonSerializableXpire.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableXpire.MESSAGE_DUPLICATE_ITEM,
                dataFromFile::toModelType);
    }

}
