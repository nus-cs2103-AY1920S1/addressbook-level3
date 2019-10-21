package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.ALICE;
import static seedu.address.testutil.TypicalAppData.HOON;
import static seedu.address.testutil.TypicalAppData.IDA;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;

public class JsonAppDataStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAppDataStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAppData_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAppData(null));
    }

    private java.util.Optional<ReadOnlyAppData> readAppData(String filePath) throws Exception {
        return new JsonAppDataStorage(Paths.get(filePath)).readAppData(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAppData("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAppData("notJsonFormatAppData.json"));
    }

    @Test
    public void readAppData_invalidAppData_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAppData("invalidAppData.json"));
    }

    @Test
    public void readAppData_invalidAndValidAppData_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAppData("invalidAndValidAppData.json"));
    }

    @Test
    public void readAndSaveAppData_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAppData.json");
        AppData original = getTypicalAppData();
        JsonAppDataStorage jsonAppDataStorage = new JsonAppDataStorage(filePath);

        // Save in new file and read back
        jsonAppDataStorage.saveAppData(original, filePath);
        ReadOnlyAppData readBack = jsonAppDataStorage.readAppData(filePath).get();
        assertEquals(original, new AppData(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addNote(HOON);
        original.removeNote(ALICE);
        jsonAppDataStorage.saveAppData(original, filePath);
        readBack = jsonAppDataStorage.readAppData(filePath).get();
        assertEquals(original, new AppData(readBack));

        // Save and read without specifying file path
        original.addNote(IDA);
        jsonAppDataStorage.saveAppData(original); // file path not specified
        readBack = jsonAppDataStorage.readAppData().get(); // file path not specified
        assertEquals(original, new AppData(readBack));

    }

    @Test
    public void saveAppData_nullAppData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppData(null, "SomeFile.json"));
    }

    /**
     * Saves {@code appData} at the specified {@code filePath}.
     */
    private void saveAppData(ReadOnlyAppData appData, String filePath) {
        try {
            new JsonAppDataStorage(Paths.get(filePath))
                    .saveAppData(appData, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAppData_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppData(new AppData(), null));
    }
}
