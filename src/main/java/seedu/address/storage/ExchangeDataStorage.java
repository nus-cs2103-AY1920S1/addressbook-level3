package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.exchangedata.ExchangeData;

/**
 * Represents a storage for {@link ExchangeData}.
 */
public interface ExchangeDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExchangeDataFilePath();

    /**
     * Returns ExchangeData data as a {@link ExchangeData}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ExchangeData> readExchangeData() throws DataConversionException, IOException;

    /**
     * @see #getExchangeDataFilePath()
     */
    Optional<ExchangeData> readExchangeData(Path filePath) throws DataConversionException, IOException;

}

