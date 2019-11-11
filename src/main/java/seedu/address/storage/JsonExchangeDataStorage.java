package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import java.text.ParseException;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.exchangedata.ExchangeData;


/**
 * A class to access ExchangeData stored as a json file on the hard disk.
 */
public class JsonExchangeDataStorage implements ExchangeDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExchangeDataStorage.class);

    private Path filePath;

    public JsonExchangeDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getExchangeDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<ExchangeData> readExchangeData() throws DataConversionException, IOException {
        return readExchangeData(filePath);
    }

    /**
     * Similar to {@link #readExchangeData()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ExchangeData> readExchangeData(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonAdaptedExchangeData> jsonExchangeData = JsonUtil.readJsonFile(
            filePath, JsonAdaptedExchangeData.class);
        if (jsonExchangeData.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExchangeData.get().toModelType());
        } catch (IllegalValueException | ParseException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

}
