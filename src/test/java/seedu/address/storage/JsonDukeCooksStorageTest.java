package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.ALICE;
import static seedu.address.testutil.TypicalDiaries.HOON;
import static seedu.address.testutil.TypicalDiaries.IDA;
import static seedu.address.testutil.TypicalDiaries.getTypicalDukeCooks;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;

public class JsonDukeCooksStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDukeCooksStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDukeCooks_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readDukeCooks(null));
    }

    private java.util.Optional<ReadOnlyDukeCooks> readDukeCooks(String filePath) throws Exception {
        return new JsonDukeCooksStorage(Paths.get(filePath)).readDukeCooks(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDukeCooks("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("notJsonFormatDukeCooks.json"));
    }

    @Test
    public void readDukeCooks_invalidDiaryDukeCooks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("invalidDiaryDukeCooks.json"));
    }

    @Test
    public void readDukeCooks_invalidAndValidDiaryDukeCooks_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readDukeCooks("invalidAndValidDiaryDukeCooks.json"));
    }

    @Test
    public void readAndSaveDukeCooks_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDukeCooks.json");
        DukeCooks original = getTypicalDukeCooks();
        JsonDukeCooksStorage jsonDukeCooksStorage = new JsonDukeCooksStorage(filePath);

        // Save in new file and read back
        jsonDukeCooksStorage.saveDukeCooks(original, filePath);
        ReadOnlyDukeCooks readBack = jsonDukeCooksStorage.readDukeCooks(filePath).get();
        assertEquals(original, new DukeCooks(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDiary(HOON);
        original.removeDiary(ALICE);
        jsonDukeCooksStorage.saveDukeCooks(original, filePath);
        readBack = jsonDukeCooksStorage.readDukeCooks(filePath).get();
        assertEquals(original, new DukeCooks(readBack));

        // Save and read without specifying file path
        original.addDiary(IDA);
        jsonDukeCooksStorage.saveDukeCooks(original); // file path not specified
        readBack = jsonDukeCooksStorage.readDukeCooks().get(); // file path not specified
        assertEquals(original, new DukeCooks(readBack));

    }

    @Test
    public void saveDukeCooks_nullDukeCooks_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDukeCooks(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dukeCooks} at the specified {@code filePath}.
     */
    private void saveDukeCooks(ReadOnlyDukeCooks dukeCooks, String filePath) {
        try {
            new JsonDukeCooksStorage(Paths.get(filePath))
                    .saveDukeCooks(dukeCooks, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDukeCooks_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveDukeCooks(new DukeCooks(), null));
    }
}
