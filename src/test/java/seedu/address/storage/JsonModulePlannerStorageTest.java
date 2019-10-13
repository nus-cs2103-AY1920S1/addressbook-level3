package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

//import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;

//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.model.ModulePlanner;
//import seedu.address.model.ReadOnlyModulePlanner;

public class JsonModulePlannerStorageTest {
    //TODO modify tests

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModulePlannerStorageTest");

    @TempDir
    public Path testFolder;

    /*
    @Test
    public void readModulePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModulePlanner(null));
    }

    private java.util.Optional<ReadOnlyModulePlanner> readModulePlanner(String filePath) throws Exception {
        return new JsonModulePlannerStorage(Paths.get(filePath)).
                readModulePlanner(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModulePlanner("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModulePlanner("notJsonFormatModulePlanner.json"));
    }

    @Test
    public void readModulePlanner_invalidStudyPlanModulePlanner_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModulePlanner("invalidStudyPlanModulePlanner.json"));
    }

    @Test
    public void readModulePlanner_invalidAndValidStudyPlanModulePlanner_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readModulePlanner("invalidAndValidStudyPlanModulePlanner.json"));
    }

    @Test
    public void readAndSaveModulePlanner_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModulePlanner.json");
        ModulePlanner original = getTypicalModulePlanner();
        JsonModulePlannerStorage jsonModulePlannerStorage = new JsonModulePlannerStorage(filePath);

        // Save in new file and read back
        jsonModulePlannerStorage.saveModulePlanner(original, filePath);
        ReadOnlyModulePlanner readBack = jsonModulePlannerStorage.readModulePlanner(filePath).get();
        assertEquals(original, new ModulePlanner(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudyPlan(HOON);
        original.removeStudyPlan(ALICE);
        jsonModulePlannerStorage.saveModulePlanner(original, filePath);
        readBack = jsonModulePlannerStorage.readModulePlanner(filePath).get();
        assertEquals(original, new ModulePlanner(readBack));

        // Save and read without specifying file path
        original.addStudyPlan(IDA);
        jsonModulePlannerStorage.saveModulePlanner(original); // file path not specified
        readBack = jsonModulePlannerStorage.readModulePlanner().get(); // file path not specified
        assertEquals(original, new ModulePlanner(readBack));

    }

    @Test
    public void saveModulePlanner_nullModulePlanner_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulePlanner(null, "SomeFile.json"));
    }
     */

    /**
     * Saves {@code modulePlanner} at the specified {@code filePath}.
     */
    /*
    private void saveModulePlanner(ReadOnlyModulePlanner modulePlanner, String filePath) {
        try {
            new JsonModulePlannerStorage(Paths.get(filePath))
                    .saveModulePlanner(modulePlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModulePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulePlanner(new ModulePlanner(), null));
    }
     */
}
