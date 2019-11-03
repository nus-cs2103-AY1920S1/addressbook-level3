package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.END_DATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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
    private static final Path DUPLICATE_PREFIX_FILE = TEST_DATA_FOLDER.resolve("duplicatePrefixPropertyBook.json");
    private static final Path DUPLICATE_FULL_NAMES_FILE =
        TEST_DATA_FOLDER.resolve("duplicateFullNamePropertyBook.json");

    private PropertyBook propertyBook = PropertyBook.getInstance();

    @BeforeEach
    public void reset() {
        propertyBook.clearCustomProperties();
    }

    @Test
    public void toModelBook_typicalPropertyFile_success() throws Exception {
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        PropertyBook propertyBook = dataFromFile.toModelBook();
        Set<CustomProperty> expectedSet = Set.of(RATING, REMARK, END_DATE);
        assertEquals(propertyBook.getCustomProperties(), expectedSet);
    }

    @Test
    public void toModelBook_invalidCustomProperty_throwsIllegalValueException() throws Exception {
        // Invalid parameter type in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_PARAMETER_TYPE_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelBook);
    }

    @Test
    public void toModelBook_invalidPrefix_throwsIllegalValueException() throws Exception {
        // Invalid prefix in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_PREFIX_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelBook);
    }

    @Test
    public void toModelBook_invalidFullName_throwsIllegalValueException() throws Exception {
        // Invalid full name in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(INVALID_FULL_NAME_PROPERTY_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelBook);

    }

    @Test
    public void toModelBook_duplicatePrefix_throwsIllegalValueException() throws Exception {
        // Duplicate prefixes in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PREFIX_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelBook);
    }

    @Test
    public void toModelBook_duplicateFullName_throwsIllegalValueException() throws Exception {
        // Duplicate full names in file
        JsonSerializablePropertyBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FULL_NAMES_FILE,
            JsonSerializablePropertyBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelBook);
    }
}
