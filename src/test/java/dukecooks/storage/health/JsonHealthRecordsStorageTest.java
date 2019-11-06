package dukecooks.storage.health;

import static dukecooks.testutil.health.TypicalRecords.CALORIES_SECOND;
import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_FIRST;
import static dukecooks.testutil.health.TypicalRecords.GLUCOSE_SECOND;
import static dukecooks.testutil.health.TypicalRecords.getTypicalHealthRecords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.testutil.Assert;

public class JsonHealthRecordsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonHealthRecordsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHealthRecords_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readHealthRecords(null));
    }

    private java.util.Optional<ReadOnlyHealthRecords> readHealthRecords(String filePath) throws Exception {
        return new JsonHealthRecordsStorage(Paths.get(filePath))
                .readHealthRecords(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHealthRecords("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readHealthRecords("notJsonFormatHealthRecords.json"));
    }

    @Test
    public void readHealthRecords_invalidRecord_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readHealthRecords("invalidRecord.json"));
    }

    @Test
    public void readHealthRecords_invalidAndValidHealthRecords_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readHealthRecords("invalidAndValidHealthRecords.json"));
    }

    @Test
    public void readAndSaveHealthRecords_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHealthRecords.json");
        HealthRecords original = getTypicalHealthRecords();
        JsonHealthRecordsStorage jsonHealthRecordsStorage = new JsonHealthRecordsStorage(filePath);

        // Save in new file and read back
        jsonHealthRecordsStorage.saveHealthRecords(original, filePath);
        ReadOnlyHealthRecords readBack = jsonHealthRecordsStorage.readHealthRecords(filePath).get();
        assertEquals(original, new HealthRecords(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addRecord(GLUCOSE_SECOND);
        original.removeRecord(GLUCOSE_FIRST);
        jsonHealthRecordsStorage.saveHealthRecords(original, filePath);
        readBack = jsonHealthRecordsStorage.readHealthRecords(filePath).get();
        assertEquals(original, new HealthRecords(readBack));

        // Save and read without specifying file path
        original.addRecord(CALORIES_SECOND);
        jsonHealthRecordsStorage.saveHealthRecords(original); // file path not specified
        readBack = jsonHealthRecordsStorage.readHealthRecords().get(); // file path not specified
        assertEquals(original, new HealthRecords(readBack));

    }

    @Test
    public void saveHealthRecords_nullHealthRecords_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveHealthRecords(null, "SomeFile.json"));
    }

    /**
     * Saves {@code healthRecords} at the specified {@code filePath}.
     */
    private void saveHealthRecords(ReadOnlyHealthRecords healthRecords, String filePath) {
        try {
            new JsonHealthRecordsStorage(Paths.get(filePath))
                    .saveHealthRecords(healthRecords, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHealthRecords_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveHealthRecords(new HealthRecords(), null));
    }
}
