package seedu.address.storage.cap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModule.CS3226;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;

public class JsonCapLogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCapLogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCapLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCapLog(null));
    }

    private java.util.Optional<ReadOnlyCapLog> readCapLog(String filePath) throws Exception {
        return new JsonCapStorage(Paths.get(filePath)).readCapLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCapLog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCapLog("notJsonFormatCapLog.json"));
    }

    @Test
    public void readCapLog_invalidModuleCapLog_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCapLog("invalidModuleCapLog.json"));
    }

    @Test
    public void readCapLog_invalidAndValidModuleCapLog_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCapLog("invalidAndValidModuleCapLog.json"));
    }

    @Test
    public void readAndSaveCapLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCapLog.json");
        CapLog original = getTypicalCapLog();
        JsonCapStorage jsonCapLogStorage = new JsonCapStorage(filePath);

        // Save in new file and read back
        jsonCapLogStorage.saveCapLog(original, filePath);
        ReadOnlyCapLog readBack = jsonCapLogStorage.readCapLog(filePath).get();
        assertEquals(original, new CapLog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.removeModule(CS3226);
        jsonCapLogStorage.saveCapLog(original, filePath);
        readBack = jsonCapLogStorage.readCapLog(filePath).get();
        assertEquals(original, new CapLog(readBack));

        // Save and read without specifying file path
        jsonCapLogStorage.saveCapLog(original); // file path not specified
        readBack = jsonCapLogStorage.readCapLog().get(); // file path not specified
        assertEquals(original, new CapLog(readBack));

    }

    @Test
    public void saveCapLog_nullCapLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCapLog(null, "SomeFile.json"));
    }

    /**
     * Saves {@code capLog} at the specified {@code filePath}.
     */
    private void saveCapLog(ReadOnlyCapLog capLog, String filePath) {
        try {
            new JsonCapStorage(Paths.get(filePath))
                    .saveCapLog(capLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCapLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCapLog(new CapLog(), null));
    }
}
