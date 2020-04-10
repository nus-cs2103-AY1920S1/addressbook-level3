package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Athletick;
import seedu.address.model.ReadOnlyAthletick;

public class JsonAthletickStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAthletickStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAthletick_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAthletick(null));
    }

    private java.util.Optional<ReadOnlyAthletick> readAthletick(String filePath) throws Exception {
        return new JsonAthletickStorage(Paths.get(filePath)).readAthletick(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAthletick("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAthletick("notJsonFormatAthletick.json"));
    }

    @Test
    public void readAthletick_invalidPersonAthletick_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAthletick("invalidPersonAthletick.json"));
    }

    @Test
    public void readAthletick_invalidAndValidPersonAthletick_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAthletick("invalidAndValidPersonAthletick.json"));
    }

    @Test
    public void readAndSaveAthletick_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAthletick.json");
        Athletick original = getTypicalAthletick();
        JsonAthletickStorage jsonAthletickStorage = new JsonAthletickStorage(filePath);

        // Save in new file and read back
        jsonAthletickStorage.saveAthletick(original, filePath);
        ReadOnlyAthletick readBack = jsonAthletickStorage.readAthletick(filePath).get();
        assertEquals(original, new Athletick(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAthletickStorage.saveAthletick(original, filePath);
        readBack = jsonAthletickStorage.readAthletick(filePath).get();
        assertEquals(original, new Athletick(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAthletickStorage.saveAthletick(original); // file path not specified
        readBack = jsonAthletickStorage.readAthletick().get(); // file path not specified
        assertEquals(original, new Athletick(readBack));

    }

    @Test
    public void saveAthletick_nullAthletick_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAthletick(null, "SomeFile.json"));
    }

    /**
     * Saves {@code Athletick} at the specified {@code filePath}.
     */
    private void saveAthletick(ReadOnlyAthletick athletick, String filePath) {
        try {
            new JsonAthletickStorage(Paths.get(filePath))
                    .saveAthletick(athletick, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAthletick_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAthletick(new Athletick(), null));
    }
}
