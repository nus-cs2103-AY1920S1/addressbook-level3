package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG1;
import static seedu.address.testutil.TypicalLogEntries.LOG2;
import static seedu.address.testutil.TypicalLogEntries.LOG3;
import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;

public class JsonFinanceStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFinanceStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFinanceLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFinanceLog(null));
    }

    private java.util.Optional<ReadOnlyFinanceLog> readFinanceLog(String filePath) throws Exception {
        return new JsonFinanceStorage(Paths.get(filePath)).readFinanceLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFinanceLog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFinanceLog("notJsonFormatFinanceLog.json"));
    }

    @Test
    public void readFinanceLog_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFinanceLog("invalidLogEntryFinanceLog.json"));
    }

    @Test
    public void readFinanceLog_invalidAndValidLogEntryFinanceLog_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFinanceLog("invalidAndValidLogEntryFinanceLog.json"));
    }

    @Test
    public void readAndSaveFinanceLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        FinanceLog original = getTypicalFinanceLog();
        JsonFinanceStorage jsonFinanceLogStorage = new JsonFinanceStorage(filePath);

        // Save in new file and read back
        jsonFinanceLogStorage.saveFinanceLog(original, filePath);
        ReadOnlyFinanceLog readBack = jsonFinanceLogStorage.readFinanceLog(filePath).get();
        assertEquals(original, new FinanceLog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addLogEntry(LOG1);
        original.removeLogEntry(LOG2);
        jsonFinanceLogStorage.saveFinanceLog(original, filePath);
        readBack = jsonFinanceLogStorage.readFinanceLog(filePath).get();
        assertEquals(original, new FinanceLog(readBack));

        // Save and read without specifying file path
        original.addLogEntry(LOG3);
        jsonFinanceLogStorage.saveFinanceLog(original); // file path not specified
        readBack = jsonFinanceLogStorage.readFinanceLog().get(); // file path not specified
        assertEquals(original, new FinanceLog(readBack));
    }

    @Test
    public void saveFinanceLog_nullFinanceLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFinanceLog(null, "SomeFile.json"));
    }

    /**
     * Saves {@code financeLog} at the specified {@code filePath}.
     */
    private void saveFinanceLog(ReadOnlyFinanceLog financeLog, String filePath) {
        try {
            new JsonFinanceStorage(Paths.get(filePath))
                    .saveFinanceLog(financeLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFinanceLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFinanceLog(new FinanceLog(), null));
    }
}
