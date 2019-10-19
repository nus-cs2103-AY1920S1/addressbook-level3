package seedu.savenus.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.FileUtil;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.ReadOnlyPurchaseHistory;

/**
 * A class to access Purchase History data stored as a json file on the hard disk.
 */
public class JsonPurchaseHistoryStorage implements PurchaseHistoryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMenuStorage.class);

    private Path filePath;

    public JsonPurchaseHistoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPurchaseHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory() throws DataConversionException {
        return readPurchaseHistory(filePath);
    }

    /**
     * Similar to {@link #readPurchaseHistory()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPurchaseHistory> readPurchaseHistory(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePurchaseHistory> jsonPurchaseHistory = JsonUtil.readJsonFile(
                filePath, JsonSerializablePurchaseHistory.class);
        if (!jsonPurchaseHistory.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPurchaseHistory.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) throws IOException {
        savePurchaseHistory(purchaseHistory, filePath);
    }

    /**
     * Similar to {@link #savePurchaseHistory(ReadOnlyPurchaseHistory)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory, Path filePath) throws IOException {
        requireNonNull(purchaseHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePurchaseHistory(purchaseHistory), filePath);
    }

}
