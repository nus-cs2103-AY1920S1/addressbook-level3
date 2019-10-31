package mams.storage;

import static mams.testutil.Assert.assertThrows;
import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_1;
import static mams.testutil.TypicalCommandHistory.SUCCESSFUL_IO_2;

import static mams.testutil.TypicalCommandHistory.getTypicalCommandHistory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import mams.commons.exceptions.DataConversionException;
import mams.logic.CommandHistory;
import mams.logic.ReadOnlyCommandHistory;

public class JsonCommandHistoryStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonCommandHistoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCommandHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCommandHistory(null));
    }

    private java.util.Optional<ReadOnlyCommandHistory> readCommandHistory(String filePath) throws Exception {
        return new JsonCommandHistoryStorage(Paths.get(filePath))
                .readCommandHistory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCommandHistory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCommandHistory(
                "notJsonFormatCommandHistory.json"));
    }

    @Test
    public void readCommandHistory_invalidCommandHistory_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCommandHistory("invalidCommandHistory.json"));
    }

    @Test
    public void readCommandHistory_invalidAndValidCommandHistory_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCommandHistory(
                "invalidAndValidCommandHistory.json"));
    }

    @Test
    public void readAndSaveCommandHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        CommandHistory original = getTypicalCommandHistory();
        JsonCommandHistoryStorage jsonCommandHistoryStorage = new JsonCommandHistoryStorage(filePath);

        // Save in new file and read back
        jsonCommandHistoryStorage.saveCommandHistory(original, filePath);
        ReadOnlyCommandHistory readBack = jsonCommandHistoryStorage.readCommandHistory(filePath).get();
        assertEquals(original, new CommandHistory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.add(SUCCESSFUL_IO_1.getInput(), SUCCESSFUL_IO_1.getOutput(),
                SUCCESSFUL_IO_1.checkSuccessful(), SUCCESSFUL_IO_1.getTimeStamp());
        jsonCommandHistoryStorage.saveCommandHistory(original, filePath);
        readBack = jsonCommandHistoryStorage.readCommandHistory(filePath).get();
        assertEquals(original, new CommandHistory(readBack));

        // Save and read without specifying file path
        original.add(SUCCESSFUL_IO_2.getInput(), SUCCESSFUL_IO_2.getOutput(),
                SUCCESSFUL_IO_2.checkSuccessful(), SUCCESSFUL_IO_2.getTimeStamp());
        jsonCommandHistoryStorage.saveCommandHistory(original); // file path not specified
        readBack = jsonCommandHistoryStorage.readCommandHistory().get(); // file path not specified
        assertEquals(original, new CommandHistory(readBack));

    }

    @Test
    public void saveCommandHistory_nullCommandHistory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCommandHistory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code commandHistory} at the specified {@code filePath}.
     */
    private void saveCommandHistory(ReadOnlyCommandHistory commandHistory, String filePath) {
        try {
            new JsonCommandHistoryStorage(Paths.get(filePath))
                    .saveCommandHistory(commandHistory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCommandHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCommandHistory(new CommandHistory(), null));
    }
}
