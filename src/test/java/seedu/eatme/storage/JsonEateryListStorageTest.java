package seedu.eatme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.BURGERKING;
import static seedu.eatme.testutil.TypicalEateries.MAC;
import static seedu.eatme.testutil.TypicalEateries.PIZZAHUT;
import static seedu.eatme.testutil.TypicalEateries.getTypicalOpenEateryList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.ReadOnlyEateryList;

public class JsonEateryListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEateryListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEateryList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEateryList(null));
    }

    private java.util.Optional<ReadOnlyEateryList> readEateryList(String filePath) throws Exception {
        return new JsonEateryListStorage(Paths.get(filePath)).readEateryList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEateryList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readEateryList("notJsonFormatEateryList.json"));
    }

    @Test
    public void readEateryList_invalidEateryEateryList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readEateryList("invalidEateryEateryList.json"));
    }

    @Test
    public void readAndSaveEateryList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEateryList.json");
        EateryList original = getTypicalOpenEateryList();
        JsonEateryListStorage jsonEateryListStorage = new JsonEateryListStorage(filePath);

        // Save in new file and read back
        jsonEateryListStorage.saveEateryList(original, filePath);
        ReadOnlyEateryList readBack = jsonEateryListStorage.readEateryList(filePath).get();
        assertEquals(original, new EateryList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEatery(PIZZAHUT);
        original.removeEatery(MAC);
        jsonEateryListStorage.saveEateryList(original, filePath);
        readBack = jsonEateryListStorage.readEateryList(filePath).get();
        assertEquals(original, new EateryList(readBack));

        // Save and read without specifying file path
        original.addEatery(BURGERKING);
        jsonEateryListStorage.saveEateryList(original); // file path not specified
        readBack = jsonEateryListStorage.readEateryList().get(); // file path not specified
        assertEquals(original, new EateryList(readBack));

    }

    @Test
    public void saveEateryList_nullEateryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEateryList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eateryList} at the specified {@code filePath}.
     */
    private void saveEateryList(ReadOnlyEateryList eateryList, String filePath) {
        try {
            new JsonEateryListStorage(Paths.get(filePath))
                    .saveEateryList(eateryList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEateryList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEateryList(new EateryList(), null));
    }
}
