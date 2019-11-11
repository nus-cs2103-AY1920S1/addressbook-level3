package seedu.savenus.storage.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalSavingsHistory.getTypicalSavingsHistory;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.savings.ReadOnlySavingsHistory;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.SavingsHistory;

//@@author fatclarence
public class JsonSavingsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSavingsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSavingsHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSavingsHistory(null));
    }

    private java.util.Optional<ReadOnlySavingsHistory> readSavingsHistory(String filePath) throws Exception {
        return new JsonSavingsStorage(Paths.get(filePath))
                .readSavingsHistory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, ()
            -> readSavingsHistory("notJsonFormatSavingsHistory.json"));
    }

    @Test
    public void readSavingsHistory_invalidsavingsHistory_throwDataConversionException() {
        assertThrows(DataConversionException.class, ()
            -> readSavingsHistory("invalidSavingsHistory.json"));
    }

    @Test
    public void readAndSaveSavingsHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSavingsHistory.json");
        SavingsHistory original = getTypicalSavingsHistory();
        JsonSavingsStorage jsonSavingsStorage = new JsonSavingsStorage(filePath);

        // Save in new file and read back
        jsonSavingsStorage.saveSavingsHistory(original, filePath);
        ReadOnlySavingsHistory readBack = jsonSavingsStorage.readSavingsHistory(filePath).get();
        assertEquals(original, new SavingsHistory(readBack));

        // Modify data, overwrite existing file, and read back
        original.addToHistory(new Savings("336.79", "1573407939182", false));
        jsonSavingsStorage.saveSavingsHistory(original, filePath);
        readBack = jsonSavingsStorage.readSavingsHistory(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.addToHistory(new Savings("22.00", "1573407960323", true));
        jsonSavingsStorage.saveSavingsHistory(original); // file path not specified
        readBack = jsonSavingsStorage.readSavingsHistory().get();
        assertEquals(original, readBack);
    }
}
