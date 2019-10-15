package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.FAREWELL_PARTY;
import static seedu.address.testutil.TypicalTasks.FIND_VP;
import static seedu.address.testutil.TypicalTasks.ORDER_SHIRTS;
import static seedu.address.testutil.TypicalTasks.getTypicalProjectDashboard;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;

public class JsonProjectDashboardStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonProjectDashboardStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readProjectDashboard_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readProjectDashboard(null));
    }

    private java.util.Optional<ReadOnlyProjectDashboard> readProjectDashboard(String filePath) throws Exception {
        return new JsonProjectDashboardStorage(Paths.get(filePath))
                .readProjectDashBoard(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProjectDashboard("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
            readProjectDashboard("notJsonFormatProjectDashboard.json"));
    }

    @Test
    public void readProjectDashboard_invalidTaskProjectDashboard_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readProjectDashboard("invalidTaskProjectDashboard.json"));
    }

    @Test
    public void readProjectDashboard_invalidAndValidTaskProjectDashboard_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readProjectDashboard("invalidAndValidTaskProjectDashboard.json"));
    }

    @Test
    public void readAndSaveProjectDashboard_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ProjectDashboard original = getTypicalProjectDashboard();
        JsonProjectDashboardStorage jsonProjectDashboardStorage = new JsonProjectDashboardStorage(filePath);

        // Save in new file and read back
        jsonProjectDashboardStorage.saveProjectDashboard(original, filePath);
        ReadOnlyProjectDashboard readBack = jsonProjectDashboardStorage.readProjectDashBoard(filePath).get();
        assertEquals(original, new ProjectDashboard(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(FIND_VP);
        original.removeTask(ORDER_SHIRTS);
        jsonProjectDashboardStorage.saveProjectDashboard(original, filePath);
        readBack = jsonProjectDashboardStorage.readProjectDashBoard(filePath).get();
        assertEquals(original, new ProjectDashboard(readBack));

        // Save and read without specifying file path
        original.addTask(FAREWELL_PARTY);
        jsonProjectDashboardStorage.saveProjectDashboard(original); // file path not specified
        readBack = jsonProjectDashboardStorage.readProjectDashBoard().get(); // file path not specified
        assertEquals(original, new ProjectDashboard(readBack));

    }

    @Test
    public void saveProjectDashboard_nullProjectDashboard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProjectDashboard(null, "SomeFile.json"));
    }

    /**
     * Saves {@code projectDashboard} at the specified {@code filePath}.
     */
    private void saveProjectDashboard(ReadOnlyProjectDashboard projectDashboard, String filePath) {
        try {
            new JsonProjectDashboardStorage(Paths.get(filePath))
                    .saveProjectDashboard(projectDashboard, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProjectDashboard_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveProjectDashboard(new ProjectDashboard(), null));
    }
}
