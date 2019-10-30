package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.END_DATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;

class JsonSerializablePropertyBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePropertyBookTest");

    private static final Path TYPICAL_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("typicalPropertyBook.json");
    private static final Path INVALID_PREFIX_PROPERTY_FILE = TEST_DATA_FOLDER.resolve("invalidPrefixPropertyBook.json");
    private static final Path INVALID_PARAMETER_TYPE_PROPERTY_FILE =
        TEST_DATA_FOLDER.resolve("invalidParameterTypePropertyBook.json");
    private static final Path INVALID_FULL_NAME_PROPERTY_FILE =
        TEST_DATA_FOLDER.resolve("invalidFullNamePropertyBook.json");

    @Test
    public void toModelManager_typicalPropertyFile_success() throws Exception {
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        PropertyBook propertyBook = dataFromFile.toModelManager();
        Set<CustomProperty> expectedSet = Set.of(RATING, REMARK, END_DATE);
        PropertyBook expectedBook = new PropertyBook(expectedSet);
        assertEquals(propertyBook, expectedBook);
    }

    @Test
    public void toModelManager_invalidCustomProperty_throwsInvalidValueException() throws Exception {
        // Invalid parameter type in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_PARAMETER_TYPE_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelManager);
    }

    @Test
    public void toModelManager_invalidPrefix_throwsInvalidValueException() throws Exception {
        // Invalid prefix in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_PREFIX_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelManager);
    }

    @Test
    public void toModelManager_invalidFullName_throwsInvalidValueException() throws Exception {
        // Invalid full name in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_FULL_NAME_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelManager);

    }

}
