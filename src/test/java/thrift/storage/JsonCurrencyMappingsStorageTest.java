package thrift.storage;

import static thrift.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import thrift.commons.exceptions.DataConversionException;

class JsonCurrencyMappingsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonCurrencyMappingsStorageTest");

    @Test
    public void readCurrencyMappings_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCurrencyMappings(null));
    }

    @Test
    public void readCurrencyMappings_missingFile_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () ->
                readCurrencyMappings("NonExistentFile.json"));
    }

    private Optional<HashMap<String, Double>> readCurrencyMappings(String currencyFileInTestDataFolder)
            throws DataConversionException {
        Path currencyFilePath = addToTestDataPathIfNotNull(currencyFileInTestDataFolder);
        return new JsonCurrencyMappingsStorage(currencyFilePath)
                .readCurrencyMappings(currencyFilePath);
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
                : null;
    }

}
