package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;

public class JsonMemeBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMemeBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMemeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMemeBook(null));
    }

    private java.util.Optional<ReadOnlyMemeBook> readMemeBook(String filePath) throws Exception {
        return new JsonMemeBookStorage(Paths.get(filePath)).readMemeBook(addToTestDataPathIfNotNull(filePath));
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
        assertThrows(DataConversionException.class, () -> readMemeBook("notJsonFormatMemeBook.json"));
    }

    @Test
    public void readMemeBook_invalidMemeMemeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMemeBook("invalidMemeMemeBook.json"));
    }

    @Test
    public void readMemeBook_invalidAndValidMemeMemeBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMemeBook("invalidAndValidMemeMemeBook.json"));
    }

    @Test
    public void readAndSaveMemeBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMemeBook.json");
        MemeBook original = getTypicalMemeBook();
        JsonMemeBookStorage jsonMemeBookStorage = new JsonMemeBookStorage(filePath);

        // Save in new file and read back
        jsonMemeBookStorage.saveMemeBook(original, filePath);
        ReadOnlyMemeBook readBack = jsonMemeBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.removeMeme(DOGE);
        jsonMemeBookStorage.saveMemeBook(original, filePath);
        readBack = jsonMemeBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Save and read without specifying file path
        original.addMeme(DOGE);
        jsonMemeBookStorage.saveMemeBook(original); // file path not specified
        readBack = jsonMemeBookStorage.readMemeBook().get(); // file path not specified
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
            new JsonMemeBookStorage(Paths.get(filePath))
                    .saveMemeBook(memeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMemeBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMemeBook(new MemeBook(), null));
    }
}
