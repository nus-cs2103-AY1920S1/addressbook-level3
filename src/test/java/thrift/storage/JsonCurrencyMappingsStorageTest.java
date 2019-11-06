package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.testutil.Assert.assertThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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

    @Test
    public void currencyMappingsSaveRead_success() throws Exception {
        File testFile = File.createTempFile("testCurrencies", ".json");
        Path testPath = Path.of(testFile.getPath());
        CurrencyMappingsStorage testMappingsStorage = new JsonCurrencyMappingsStorage(testPath);
        Map<String, Double> currencyMappings = Map.of("ABC", 123.45);

        testMappingsStorage.saveCurrencyMappings(currencyMappings);
        Map<String, Double> readbackMappings = testMappingsStorage.readCurrencyMappings().get();

        assertEquals(currencyMappings, readbackMappings);
        testFile.delete();
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
