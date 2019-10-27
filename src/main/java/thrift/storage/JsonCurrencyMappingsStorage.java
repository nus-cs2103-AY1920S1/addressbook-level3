package thrift.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import thrift.commons.exceptions.DataConversionException;
import thrift.commons.util.JsonUtil;

/**
 * A class to access Currency Mappings stored in the hard disk as a json file
 */
public class JsonCurrencyMappingsStorage implements CurrencyMappingsStorage {

    private Path filePath;

    public JsonCurrencyMappingsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getCurrencyMappingsFilePath() {
        return filePath;
    }

    @Override
    public Optional<HashMap<String, Double>> readCurrencyMappings() throws DataConversionException {
        return readCurrencyMappings(filePath);
    }

    /**
     * Similar to {@link #readCurrencyMappings()}
     *
     * @param mappingsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<HashMap<String, Double>> readCurrencyMappings(Path mappingsFilePath)
            throws DataConversionException, NoSuchElementException {
        @SuppressWarnings("unchecked")
        HashMap<String, Double> hashmap =
                (HashMap<String, Double>) JsonUtil.readJsonFile(mappingsFilePath, HashMap.class).get();
        return Optional.of(hashmap);
    }

    @Override
    public void saveCurrencyMappings(Map<String, Double> currencyMappings) throws IOException {
        JsonUtil.saveJsonFile(currencyMappings, filePath);
    }



}
