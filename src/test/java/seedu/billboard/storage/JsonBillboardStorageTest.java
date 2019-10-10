package seedu.billboard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.CLOTHES;
import static seedu.billboard.testutil.TypicalExpenses.NEW_LAPTOP;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;

public class JsonBillboardStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBillboardStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBillboard_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBillboard(null));
    }

    private Optional<ReadOnlyBillboard> readBillboard(String filePath) throws Exception {
        return new JsonBillboardStorage(Paths.get(filePath)).readBillboard(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBillboard("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readBillboard("notJsonFormatBillboard.json"));
    }

    @Test
    public void readBillboard_invalidExpenseBillboard_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBillboard("invalidExpenseBillboard.json"));
    }

    @Test
    public void readBillboard_invalidAndValidExpenseBillboard_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBillboard("invalidAndValidExpenseBillboard.json"));
    }

    @Test
    public void readAndSaveBillboard_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBillboard.json");
        Billboard original = getTypicalBillboard();
        JsonBillboardStorage jsonBillboardStorage = new JsonBillboardStorage(filePath);

        // Save in new file and read back
        jsonBillboardStorage.saveBillboard(original, filePath);
        ReadOnlyBillboard readBack = jsonBillboardStorage.readBillboard(filePath).get();
        assertEquals(original, new Billboard(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExpense(CLOTHES);
        original.removeExpense(BILLS);
        jsonBillboardStorage.saveBillboard(original, filePath);
        readBack = jsonBillboardStorage.readBillboard(filePath).get();
        assertEquals(original, new Billboard(readBack));

        // Save and read without specifying file path
        original.addExpense(NEW_LAPTOP);
        jsonBillboardStorage.saveBillboard(original); // file path not specified
        readBack = jsonBillboardStorage.readBillboard().get(); // file path not specified
        assertEquals(original, new Billboard(readBack));

    }

    @Test
    public void saveBillboard_nullBillboard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBillboard(null, "SomeFile.json"));
    }

    /**
     * Saves {@code billboard} at the specified {@code filePath}.
     */
    private void saveBillboard(ReadOnlyBillboard billboard, String filePath) {
        try {
            new JsonBillboardStorage(Paths.get(filePath))
                    .saveBillboard(billboard, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBillboard_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBillboard(new Billboard(), null));
    }
}
