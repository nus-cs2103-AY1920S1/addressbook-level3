package seedu.address.storage.cap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.cap.CapUserPrefs;

public class JsonCapUserPrefsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
        "data", "JsonCapUserPrefsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCapUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCapUserPrefs(null));
    }

    private Optional<CapUserPrefs> readCapUserPrefs(String userPrefsFileInTestDataFolder)
            throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonCapUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readCapUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readCapUserPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readCapUserPrefs_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCapUserPrefs("NotJsonFormatUserPrefs.json"));
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readCapUserPrefs_fileInOrder_successfullyRead() throws DataConversionException {
        CapUserPrefs expected = getTypicalCapUserPrefs();
        CapUserPrefs actual = readCapUserPrefs("TypicalUserPref.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readCapUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        CapUserPrefs actual = readCapUserPrefs("EmptyUserPrefs.json").get();
        assertEquals(new CapUserPrefs(), actual);
    }

    @Test
    public void readCapUserPrefs_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        CapUserPrefs expected = getTypicalCapUserPrefs();
        CapUserPrefs actual = readCapUserPrefs("ExtraValuesUserPref.json").get();

        assertEquals(expected, actual);
    }

    private CapUserPrefs getTypicalCapUserPrefs() {
        CapUserPrefs userPrefs = new CapUserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1250, 800, 580, 146));
        userPrefs.setCapLogFilePath(Paths.get("capmodulelog.json"));
        return userPrefs;
    }

    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCapUserPrefs(null, "SomeFile.json"));
    }

    @Test
    public void saveCapUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCapUserPrefs(new CapUserPrefs(), null));
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveCapUserPrefs(CapUserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonCapUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveCapUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        CapUserPrefs original = new CapUserPrefs();
        original.setGuiSettings(new GuiSettings(1200, 200, 0, 2));

        Path pefsFilePath = testFolder.resolve("TempPrefs.json");
        JsonCapUserPrefsStorage jsonCapUserPrefsStorage = new JsonCapUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonCapUserPrefsStorage.saveUserPrefs(original);
        CapUserPrefs readBack = jsonCapUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(new GuiSettings(5, 5, 5, 5));
        jsonCapUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonCapUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

}
