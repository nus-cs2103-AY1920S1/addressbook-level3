package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalIncidentManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.IncidentManager;
import seedu.address.model.ReadOnlyIncidentManager;

public class JsonIncidentManagerStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonIncidentManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readIncidentManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readIncidentManager(null));
    }

    private java.util.Optional<ReadOnlyIncidentManager> readIncidentManager(String filePath) throws Exception {
        return new JsonIncidentManagerStorage(Paths.get(filePath))
                .readIncidentManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readIncidentManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readIncidentManager("notJsonFormatIncidentManager.json"));
    }

    @Test
    public void readIncidentManager_invalidPersonIncidentManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readIncidentManager("invalidPersonIncidentManager.json"));
    }

    @Test
    public void readIncidentManager_invalidAndValidPersonIncidentManager_throwDataConversionException() {
        assertThrows(
                DataConversionException.class, () -> readIncidentManager("invalidAndValidPersonIncidentManager.json"));
    }

    @Test
    public void readAndSaveIncidentManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempIncidentManager.json");
        IncidentManager original = getTypicalIncidentManager();
        JsonIncidentManagerStorage jsonIncidentManagerStorage = new JsonIncidentManagerStorage(filePath);

        // Save in new file and read back
        jsonIncidentManagerStorage.saveIncidentManager(original, filePath);
        ReadOnlyIncidentManager readBack = jsonIncidentManagerStorage.readIncidentManager(filePath).get();
        assertEquals(original, new IncidentManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonIncidentManagerStorage.saveIncidentManager(original, filePath);
        readBack = jsonIncidentManagerStorage.readIncidentManager(filePath).get();
        assertEquals(original, new IncidentManager(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonIncidentManagerStorage.saveIncidentManager(original); // file path not specified
        readBack = jsonIncidentManagerStorage.readIncidentManager().get(); // file path not specified
        assertEquals(original, new IncidentManager(readBack));

    }

    @Test
    public void saveIncidentManager_nullIncidentManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveIncidentManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code incidentManager} at the specified {@code filePath}.
     */
    private void saveIncidentManager(ReadOnlyIncidentManager incidentManager, String filePath) {
        try {
            new JsonIncidentManagerStorage(Paths.get(filePath))
                    .saveIncidentManager(incidentManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveIncidentManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveIncidentManager(new IncidentManager(), null));
    }
}
