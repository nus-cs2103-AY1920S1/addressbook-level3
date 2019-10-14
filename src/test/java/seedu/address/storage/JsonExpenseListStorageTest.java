package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.FURNITURE;
import static seedu.address.testutil.TypicalExpenses.SNACKS;
import static seedu.address.testutil.TypicalExpenses.TRANSPORT;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;

public class JsonExpenseListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonExpenseListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExpenseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExpenseList(null));
    }

    private java.util.Optional<ReadOnlyExpenseList> readExpenseList(String filePath) throws Exception {
        return new JsonExpenseListStorage(Paths.get(filePath)).readExpenseList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExpenseList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readExpenseList("notJsonFormatExpenseList.json"));
    }

    @Test
    public void readExpenseList_invalidExpenseExpenseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExpenseList("invalidExpenseExpenseList.json"));
    }

    @Test
    public void readExpenseList_invalidAndValidExpenseExpenseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExpenseList("invalidAndValidExpenseExpenseList.json"));
    }

    @Test
    public void readAndSaveExpenseList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExpenseList.json");
        ExpenseList original = getTypicalExpenseList();
        JsonExpenseListStorage jsonExpenseListStorage = new JsonExpenseListStorage(filePath);

        // Save in new file and read back
        jsonExpenseListStorage.saveExpenseList(original, filePath);
        ReadOnlyExpenseList readBack = jsonExpenseListStorage.readExpenseList(filePath).get();
        assertEquals(original, new ExpenseList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExpense(FURNITURE);
        original.removeExpense(TRANSPORT);
        jsonExpenseListStorage.saveExpenseList(original, filePath);
        readBack = jsonExpenseListStorage.readExpenseList(filePath).get();
        assertEquals(original, new ExpenseList(readBack));

        // Save and read without specifying file path
        original.addExpense(SNACKS);
        jsonExpenseListStorage.saveExpenseList(original); // file path not specified
        readBack = jsonExpenseListStorage.readExpenseList().get(); // file path not specified
        assertEquals(original, new ExpenseList(readBack));

    }

    @Test
    public void saveExpenseList_nullExpenseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpenseList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code expenseList} at the specified {@code filePath}.
     */
    private void saveExpenseList(ReadOnlyExpenseList expenseList, String filePath) {
        try {
            new JsonExpenseListStorage(Paths.get(filePath))
                    .saveExpenseList(expenseList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExpenseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExpenseList(new ExpenseList(), null));
    }
}
