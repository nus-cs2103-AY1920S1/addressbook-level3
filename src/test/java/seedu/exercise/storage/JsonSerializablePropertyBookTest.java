package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.property.PropertyBook;

class JsonSerializablePropertyBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePropertyBookTest");

    private static final Path TYPICAL_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("typicalPropertyBook.json");
    private static final Path INVALID_PREFIX_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("invalidPrefixPropertyBook.json");
    private static final Path INVALID_FULL_NAME_PROPERTY_FILE =
            TEST_DATA_FOLDER.resolve("invalidFullNamePropertyBook.json");

    @Test
    public void toModelManager_typicalPropertyFile_success() throws Exception {
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        PropertyBook propertyBook = dataFromFile.toModelManager();
        PropertyBook typicalPropertyBook = getDefaultPropertyBook();
        assertEquals(propertyBook, typicalPropertyBook);
    }

    @Test
    public void toModelManager_invalidPrefix_throwsInvalidValueExceptoion() throws Exception {
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_PREFIX_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelManager());
    }

    @Test
    public void toModelManager_invalidFullName_throwsInvalidValueExceptoion() throws Exception {
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_FULL_NAME_PROPERTY_FILE,
                JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelManager());
    }

}
