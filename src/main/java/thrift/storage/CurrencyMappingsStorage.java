package thrift.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import thrift.commons.exceptions.DataConversionException;

/**
 * Represents a storage for a HashMapof currency mappings.
 */
public interface CurrencyMappingsStorage {

    /**
     * Returns the file path of the Currency Mappings data file.
     */
    Path getCurrencyMappingsFilePath();

    /**
     * Returns Currency Mappings data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HashMap<String, Double>> readCurrencyMappings() throws DataConversionException, IOException;

    /**
     * Saves the given currency mappings to the storage.
     * @param currencyMappings cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCurrencyMappings(Map<String, Double> currencyMappings) throws IOException;
}
