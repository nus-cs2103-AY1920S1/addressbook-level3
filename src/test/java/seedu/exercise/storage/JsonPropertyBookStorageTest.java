package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.ENDDATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;

class JsonPropertyBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPropertyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPropertyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPropertyBook(null));
    }

    private java.util.Optional<PropertyBook> readPropertyBook(String filePath) throws Exception {
        return new JsonPropertyBookStorage(Paths.get(filePath))
                .readPropertyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String fileInTestDataFolder) {
        return fileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(fileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPropertyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPropertyBook("notJsonFormatPropertyBook.json"));
    }

    @Test
    public void readPropertyBook_invalidPropertyBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPropertyBook("invalidPropertyBook.json"));
    }

    @Test
    public void readAndSavePropertyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPropertyBook.json");
        JsonPropertyBookStorage jsonPropertyBookStorage = new JsonPropertyBookStorage(filePath);

        List<CustomProperty> customProperties = new ArrayList<>();
        customProperties.add(RATING);
        PropertyBook original = new PropertyBook(new HashSet<>(), new HashSet<>(), new ArrayList<>());
        original.setCustomProperties(customProperties);

        // Save in new file and read back
        jsonPropertyBookStorage.savePropertyBook(original, filePath);
        PropertyBook retrieved = jsonPropertyBookStorage.readPropertyBook(filePath).get();
        assertEquals(original, retrieved);

        // Modify data, overwrite exiting file, and read back
        customProperties.remove(RATING);
        customProperties.add(REMARK);
        original.setCustomProperties(customProperties);
        jsonPropertyBookStorage.savePropertyBook(original, filePath);
        retrieved = jsonPropertyBookStorage.readPropertyBook(filePath).get();
        assertEquals(original, retrieved);

        // Save and read without specifying file path
        customProperties.add(ENDDATE);
        original.setCustomProperties(customProperties);
        jsonPropertyBookStorage.savePropertyBook(original); // file path not specified
        retrieved = jsonPropertyBookStorage.readPropertyBook().get(); // file path not specified
        assertEquals(original, retrieved);

    }

    @Test
    public void savePropertyBook_nullPropertyBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePropertyBook(null, "SomeFile.json"));
    }

    @Test
    public void savePropertyBook_nullFilePath_throwsNullPointerException() {
        PropertyBook propertyBook = new PropertyBook(new HashSet<>(), new HashSet<>(), new ArrayList<>());
        assertThrows(NullPointerException.class, () -> savePropertyBook(propertyBook, null));
    }

    /**
     * Saves {@code propertyBook} at the specified {@code filePath}.
     */
    private void savePropertyBook(PropertyBook propertyBook, String filePath) {
        try {
            new JsonPropertyBookStorage(Paths.get(filePath))
                    .savePropertyBook(propertyBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

}
