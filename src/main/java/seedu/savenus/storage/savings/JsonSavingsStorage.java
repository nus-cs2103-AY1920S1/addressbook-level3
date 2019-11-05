package seedu.savenus.storage.savings;

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
import seedu.savenus.model.savings.ReadOnlySavingsHistory;

/**
 * A class to access Savings Account data stored as a json file on the hard disk.
 */
public class JsonSavingsStorage implements SavingsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSavingsStorage.class);

    private Path filePath;

    public JsonSavingsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getSavingsHistoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySavingsHistory> readSavingsHistory() throws DataConversionException, IOException {
        return readSavingsHistory(filePath);
    }

    @Override
    public Optional<ReadOnlySavingsHistory> readSavingsHistory(Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableSavingsHistory> jsonSavingsHistory = JsonUtil.readJsonFile(filePath,
                JsonSerializableSavingsHistory.class);

        if (jsonSavingsHistory.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSavingsHistory.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info(("Illegal values found in " + filePath + e.getMessage()));
            throw new DataConversionException(e);
        }
    }

    /**
     * Write into the user's savings history from the unmodifiable savings history.
     *
     * @param savingsHistory unmodifiable savings history of the user.
     * @throws IOException from {@link #saveSavingsHistory(ReadOnlySavingsHistory, Path)}
     */
    @Override
    public void saveSavingsHistory(ReadOnlySavingsHistory savingsHistory) throws IOException {
        saveSavingsHistory(savingsHistory, filePath);
    }

    /**
     * Similar to {@link #saveSavingsHistory(ReadOnlySavingsHistory)}.
     *
     * @param savingsHistory savingsAccount of user must be provided.
     * @param filePath location of the savings account data cannot be null.
     * @throws IOException when writing to the file fails.
     */
    @Override
    public void saveSavingsHistory(ReadOnlySavingsHistory savingsHistory, Path filePath) throws IOException {
        requireNonNull(savingsHistory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSavingsHistory(savingsHistory), filePath);
    }
}
