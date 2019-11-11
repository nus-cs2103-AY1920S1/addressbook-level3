package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.TutorAid;

public class JsonTutorAidStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTutorAidStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTutorAid_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTutorAid(null));
    }

    private java.util.Optional<ReadOnlyTutorAid> readTutorAid(String filePath) throws Exception {
        return new JsonTutorAidStorage(Paths.get(filePath)).readTutorAid(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTutorAid("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTutorAid("notJsonFormatTutorAid.json"));
    }

    @Test
    public void readTutorAid_invalidPersonTutorAid_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTutorAid("invalidPersonTutorAid.json"));
    }

    @Test
    public void readTutorAid_invalidAndValidPersonTutorAid_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTutorAid("invalidAndValidPersonTutorAid.json"));
    }

    @Test
    public void saveTutorAid_nullTutorAid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorAid(null, "SomeFile.json"));
    }

    /**
     * Saves {@code tutorAid} at the specified {@code filePath}.
     */
    private void saveTutorAid(ReadOnlyTutorAid tutorAid, String filePath) {
        try {
            new JsonTutorAidStorage(Paths.get(filePath))
                    .saveTutorAid(tutorAid, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTutorAid_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorAid(new TutorAid(), null));
    }
}
