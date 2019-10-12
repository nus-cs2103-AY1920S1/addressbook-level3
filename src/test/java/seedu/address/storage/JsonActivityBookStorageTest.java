package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalActivities.BREAKFAST;
import static seedu.address.testutil.TypicalActivities.LUNCH;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ActivityBook;

public class JsonActivityBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonActivityBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readActivityBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readActivityBook(null));
    }

    private java.util.Optional<ActivityBook> readActivityBook(String filePath) throws Exception {
        return new JsonActivityBookStorage(Paths.get(filePath)).readActivityBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readActivityBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readActivityBook("notJsonFormatActivityBook.json"));
    }

    @Test
    public void readActivityBook_invalidActivityBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readActivityBook("invalidActivityBook.json"));
    }

    @Test
    public void readActivityBook_invalidAndValidActivityActivityBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readActivityBook("invalidAndValidActivityBook.json"));
    }

    @Test
    public void readAndSaveActivityBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempActivityBook.json");
        ActivityBook original = getTypicalActivityBook();
        JsonActivityBookStorage jsonActivityBookStorage = new JsonActivityBookStorage(filePath);

        // Save in new file and read back
        jsonActivityBookStorage.saveActivityBook(original, filePath);
        ActivityBook readBack = jsonActivityBookStorage.readActivityBook(filePath).get();
        assertEquals(original, new ActivityBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addActivity(LUNCH);
        original.removeActivity(BREAKFAST);
        jsonActivityBookStorage.saveActivityBook(original, filePath);
        readBack = jsonActivityBookStorage.readActivityBook(filePath).get();
        assertEquals(original, new ActivityBook(readBack));

        // Save and read without specifying file path
        original.addActivity(LUNCH);
        jsonActivityBookStorage.saveActivityBook(original); // file path not specified
        readBack = jsonActivityBookStorage.readActivityBook().get(); // file path not specified
        assertEquals(original, new ActivityBook(readBack));

    }

    @Test
    public void saveActivityBook_nullActivityBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveActivityBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code activityBook} at the specified {@code filePath}.
     */
    private void saveActivityBook(ActivityBook activityBook, String filePath) {
        try {
            new JsonActivityBookStorage(Paths.get(filePath))
                    .saveActivityBook(activityBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveActivityBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveActivityBook(new ActivityBook(), null));
    }
}
