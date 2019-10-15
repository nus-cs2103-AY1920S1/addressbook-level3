package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.HOON;
import static seedu.address.testutil.TypicalContacts.IDA;
import static seedu.address.testutil.TypicalContacts.getTypicalPlanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Planner;
import seedu.address.model.ReadOnlyPlanner;

public class JsonPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPlannerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPlanner(null));
    }

    private java.util.Optional<ReadOnlyPlanner> readPlanner(String filePath) throws Exception {
        return new JsonPlannerStorage(Paths.get(filePath)).readPlanner(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPlanner("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPlanner("notJsonFormatPlanner.json"));
    }

    @Test
    public void readPlanner_invalidContactPlanner_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPlanner("invalidContactPlanner.json"));
    }

    @Test
    public void readPlanner_invalidAndValidContactPlanner_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPlanner("invalidAndValidContactPlanner.json"));
    }

    @Test
    public void readAndSavePlanner_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPlanner.json");
        Planner original = getTypicalPlanner();
        JsonPlannerStorage jsonPlannerStorage = new JsonPlannerStorage(filePath);

        // Save in new file and read back
        jsonPlannerStorage.savePlanner(original, filePath);
        ReadOnlyPlanner readBack = jsonPlannerStorage.readPlanner(filePath).get();
        assertEquals(original, new Planner(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HOON);
        original.removeContact(ALICE);
        jsonPlannerStorage.savePlanner(original, filePath);
        readBack = jsonPlannerStorage.readPlanner(filePath).get();
        assertEquals(original, new Planner(readBack));

        // Save and read without specifying file path
        original.addContact(IDA);
        jsonPlannerStorage.savePlanner(original); // file path not specified
        readBack = jsonPlannerStorage.readPlanner().get(); // file path not specified
        assertEquals(original, new Planner(readBack));

    }

    @Test
    public void savePlanner_nullPlanner_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePlanner(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void savePlanner(ReadOnlyPlanner planner, String filePath) {
        try {
            new JsonPlannerStorage(Paths.get(filePath))
                    .savePlanner(planner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePlanner_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePlanner(new Planner(), null));
    }
}
