package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.END_DATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

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

    private void readPropertyBook(String filePath) throws Exception {
        new JsonPropertyBookStorage(Paths.get(filePath))
            .readPropertyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String fileInTestDataFolder) {
        return fileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(fileInTestDataFolder)
            : null;
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
        PropertyBook propertyBook = PropertyBook.getInstance();

        Set<CustomProperty> originalSet = Set.of(RATING);
        propertyBook.addCustomProperties(originalSet);

        // Save in new file and read back
        jsonPropertyBookStorage.savePropertyBook(filePath);
        propertyBook.clearCustomProperties();
        jsonPropertyBookStorage.readPropertyBook(filePath);
        Set<CustomProperty> retrievedSet = PropertyBook.getInstance().getCustomProperties();
        assertEquals(originalSet, retrievedSet);

        // Resets the PropertyBook
        propertyBook.clearCustomProperties();

        // Modify data, overwrite exiting file, and read back
        originalSet = Set.of(REMARK);
        PropertyBook.getInstance().addCustomProperties(originalSet);
        jsonPropertyBookStorage.savePropertyBook(filePath);
        PropertyBook.getInstance().clearCustomProperties();
        jsonPropertyBookStorage.readPropertyBook(filePath);
        retrievedSet = PropertyBook.getInstance().getCustomProperties();
        assertEquals(originalSet, retrievedSet);

        // Resets the PropertyBook
        propertyBook.clearCustomProperties();

        // Save and read without specifying file path
        originalSet = Set.of(END_DATE);
        PropertyBook.getInstance().addCustomProperties(originalSet);
        jsonPropertyBookStorage.savePropertyBook(); // file path not specified
        propertyBook.clearCustomProperties();
        jsonPropertyBookStorage.readPropertyBook(); // file path not specified
        retrievedSet = propertyBook.getCustomProperties();
        assertEquals(originalSet, retrievedSet);
    }

    @Test
    public void savePropertyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePropertyBook(null));
    }

    /**
     * Saves {@code propertyBook} at the specified {@code filePath}.
     */
    private void savePropertyBook(String filePath) {
        try {
            new JsonPropertyBookStorage(Paths.get(filePath))
                .savePropertyBook(addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

}
