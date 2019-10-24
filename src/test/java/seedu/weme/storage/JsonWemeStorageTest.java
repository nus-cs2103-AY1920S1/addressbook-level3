package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemeBook.getTypicalMemeBook;
import static seedu.weme.testutil.TypicalMemes.DOGE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;

public class JsonWemeStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonWemeStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMemeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMemeBook(null));
    }

    private java.util.Optional<ReadOnlyMemeBook> readMemeBook(String filePath) throws Exception {
        return new JsonWemeStorage(Paths.get(filePath)).readWeme(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMemeBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMemeBook("notJsonFormatWeme.json"));
    }

    @Test
    public void readMemeBook_invalidMemeMemeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMemeBook("invalidMemeWeme.json"));
    }

    @Test
    public void readMemeBook_invalidAndValidMemeMemeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMemeBook("invalidAndValidMemeWeme.json"));
    }

    @Test
    public void readAndSaveMemeBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWeme.json");
        MemeBook original = getTypicalMemeBook();
        JsonWemeStorage jsonMemeBookStorage = new JsonWemeStorage(filePath);

        // Save in new file and read back
        jsonMemeBookStorage.saveWeme(original, filePath);
        ReadOnlyMemeBook readBack = jsonMemeBookStorage.readWeme(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.removeMeme(DOGE);
        jsonMemeBookStorage.saveWeme(original, filePath);
        readBack = jsonMemeBookStorage.readWeme(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Save and read without specifying file path
        original.addMeme(DOGE);
        jsonMemeBookStorage.saveWeme(original); // file path not specified
        readBack = jsonMemeBookStorage.readWeme().get(); // file path not specified
        assertEquals(original, new MemeBook(readBack));

    }

    @Test
    public void saveMemeBook_nullMemeBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMemeBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code memeBook} at the specified {@code filePath}.
     */
    private void saveMemeBook(ReadOnlyMemeBook memeBook, String filePath) {
        try {
            new JsonWemeStorage(Paths.get(filePath))
                    .saveWeme(memeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMemeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMemeBook(new MemeBook(), null));
    }
}
