package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static thrift.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import thrift.commons.exceptions.DataConversionException;
import thrift.model.ReadOnlyThrift;
import thrift.model.Thrift;
import thrift.testutil.TypicalTransactions;

public class JsonThriftStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonThriftStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readThrift_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readThrift(null));
    }

    private java.util.Optional<ReadOnlyThrift> readThrift(String filePath) throws Exception {
        return new JsonThriftStorage(Paths.get(filePath)).readThrift(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readThrift("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readThrift("notJsonFormatTransactionThrift.json"));
    }

    @Test
    public void readThrift_invalidTransactionThrift_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readThrift("invalidTransactionThrift.json"));
    }

    @Test
    public void readThrift_invalidAndValidTransactionThrift_throwDataConversionException() {
        assertThrows(DataConversionException.class, ()
            -> readThrift("invalidAndValidTransactionThrift.json"));
    }

    @Test
    public void readAndSaveThrift_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempThrift.json");
        Thrift original = TypicalTransactions.getTypicalThrift();
        JsonThriftStorage jsonThriftStorage = new JsonThriftStorage(filePath);

        // Save in new file and read back
        jsonThriftStorage.saveThrift(original, filePath);
        ReadOnlyThrift readBack = jsonThriftStorage.readThrift(filePath).get();
        assertEquals(original, new Thrift(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTransaction(TypicalTransactions.LAKSA);
        original.removeTransaction(TypicalTransactions.PENANG_LAKSA);
        jsonThriftStorage.saveThrift(original, filePath);
        readBack = jsonThriftStorage.readThrift(filePath).get();
        assertEquals(original, new Thrift(readBack));

        // Save and read without specifying file path
        original.addTransaction(TypicalTransactions.PENANG_LAKSA);
        jsonThriftStorage.saveThrift(original); // file path not specified
        readBack = jsonThriftStorage.readThrift().get(); // file path not specified
        assertEquals(original, new Thrift(readBack));

    }

    @Test
    public void saveThrift_nullThrift_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveThrift(null, "SomeFile.json"));
    }

    /**
     * Saves {@code thrift} at the specified {@code filePath}.
     */
    private void saveThrift(ReadOnlyThrift thrift, String filePath) {
        try {
            new JsonThriftStorage(Paths.get(filePath))
                    .saveThrift(thrift, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveThrift_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveThrift(new Thrift(), null));
    }

}
