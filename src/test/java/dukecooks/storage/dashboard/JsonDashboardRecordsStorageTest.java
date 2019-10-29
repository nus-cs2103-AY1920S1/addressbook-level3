package dukecooks.storage.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.model.dashboard.components.TaskStatus;
import dukecooks.testutil.Assert;

public class JsonDashboardRecordsStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonDashboardRecordsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readDashboardRecords_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readDashboardRecords(null));
    }

    private java.util.Optional<ReadOnlyDashboard> readDashboardRecords(String filePath) throws Exception {
        return new JsonDashboardStorage(Paths.get(filePath)).readDashboard(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDashboardRecords("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readDashboardRecords("nonJsonFormatDashboardRecords.json"));
    }

    @Test
    public void readDashboardRecords_invalidDashboardDashboardRecords_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readDashboardRecords("invalidDashboardDashboardRecords.json"));
    }

    @Test
    public void readDashboardRecords_invalidAndValidDashboardDashboardRecords_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, ()
            -> readDashboardRecords("invalidAndValidDashboardDashboardRecords.json"));
    }

    @Test
    public void readAndSaveDashboardRecords_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempDashboardRecords.json");
        DashboardRecords original = getTypicalDashboardRecords();
        JsonDashboardStorage jsonDashboardStorage = new JsonDashboardStorage(filePath);

        // Save in new file and read back
        jsonDashboardStorage.saveDashboard(original, filePath);
        ReadOnlyDashboard readBack = jsonDashboardStorage.readDashboard(filePath).get();
        assertEquals(original, original);

        // Modify data, overwrite exiting file, and read back
        original.addDashboard(new Dashboard(
            new DashboardName("Bake pastries"), new TaskDate("2/10/2019"), new TaskStatus("NOT COMPLETE")));
        jsonDashboardStorage.saveDashboard(original, filePath);
        readBack = jsonDashboardStorage.readDashboard(filePath).get();
        assertEquals(original, original);

        // Save and read without specifying file path
        original.addDashboard(new Dashboard(
            new DashboardName("Bake curry puffs"), new TaskDate("2/10/2019"), new TaskStatus("NOT COMPLETE")));
        jsonDashboardStorage.saveDashboard(original); // file path not specified
        readBack = jsonDashboardStorage.readDashboard().get(); // file path not specified
        assertEquals(original, original);

    }

    @Test
    public void saveDashboardRecords_nullDashboardRecords_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> saveDashboardRecords(null, "SomeFile.json"));
    }

    /**
     * Saves {@code dashboardRecords} at the specified {@code filePath}.
     */
    private void saveDashboardRecords(ReadOnlyDashboard dashboardRecord, String filePath) {
        try {
            new JsonDashboardStorage(Paths.get(filePath))
                    .saveDashboard(dashboardRecord, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDashboardRecords_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> saveDashboardRecords(new DashboardRecords(), null));
    }
}
