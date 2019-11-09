package seedu.ifridge.storage.wastelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

public class JsonWasteListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonWasteListStorageTest");

    @TempDir
    public Path testFolder;

    /**
     * Reads the waste list from the specified filepath.
     *
     * @return The waste archive in stored in a TreeMap for a successful read.
     */
    private java.util.Optional<TreeMap<WasteMonth, WasteList>> readWasteList(String filePath) throws Exception {
        return new JsonWasteListStorage(Paths.get(filePath)).readWasteList(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Appends the filename to the filepath of test data folder.
     *
     * @return the Path object to the file, if successfully appended.
     */
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    /**
     * Saves the waste archive at the given filepath.
     */
    private void saveWasteArchive(TreeMap<WasteMonth, WasteList> wasteArchive, String filePath) {
        try {
            new JsonWasteListStorage(Paths.get(filePath))
                    .saveWasteList(wasteArchive, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void readWasteList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWasteList(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWasteList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWasteList("notJsonFormatWasteList.json"));
    }

    @Test
    public void readWasteList_invalidGroceryItemInWasteList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWasteList("invalidGroceryItemInWasteList.json"));
    }

    @Test
    public void readWasteList_invalidWasteMonth_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWasteList("invalidWasteMonth.json"));
    }

    @Test
    public void readWasteList_invalidAndValidItemsInWasteList_throwsDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWasteList("invalidAndValidItemsInWasteList.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWasteArchive.json");
        TreeMap<WasteMonth, WasteList> original = getTypicalWasteArchive();
        JsonWasteListStorage jsonWasteListStorage = new JsonWasteListStorage(filePath);

        // Save in new file and read back
        jsonWasteListStorage.saveWasteList(original, filePath);
        TreeMap<WasteMonth, WasteList> readBack = jsonWasteListStorage.readWasteList(filePath).get();
        assertEquals(original, readBack);

        jsonWasteListStorage.saveWasteList(original);
        TreeMap<WasteMonth, WasteList> readBack2 = jsonWasteListStorage.readWasteList(filePath).get();
        assertEquals(original, readBack2);

        jsonWasteListStorage.saveWasteList(original, filePath);
        TreeMap<WasteMonth, WasteList> readBack3 = jsonWasteListStorage.readWasteList().get();
        assertEquals(original, readBack);
    }

    @Test
    public void saveWasteArchive_nullWasteArchive_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWasteArchive(null, "SomeFile.json"));
    }

    @Test
    public void saveWasteArchive_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWasteArchive(new TreeMap<WasteMonth, WasteList>(), null));
    }

    @Test
    public void getWasteListFilePathTrivialTest() {
        Path filePath = testFolder.resolve("TempWasteArchive.json");
        JsonWasteListStorage jsonWasteListStorage = new JsonWasteListStorage(filePath);
        assertEquals(filePath, jsonWasteListStorage.getWasteListFilePath());
    }
}
