package seedu.address.storage.finance;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.finance.ReadOnlyFinanceLog;


/**
 * A class to access FinanceLog data stored as a json file on the hard disk.
 */
public class JsonFinanceStorage implements FinanceStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFinanceStorage.class);

    private Path filePath;

    public JsonFinanceStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFinanceLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFinanceLog> readFinanceLog() throws DataConversionException {
        return readFinanceLog(filePath);
    }

    /**
     * Similar to {@link #readFinanceLog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFinanceLog> readFinanceLog(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFinanceLog> jsonFinanceLog = JsonUtil.readJsonFile(
                filePath, JsonSerializableFinanceLog.class);
        if (!jsonFinanceLog.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFinanceLog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFinanceLog(ReadOnlyFinanceLog financeLog) throws IOException {
        saveFinanceLog(financeLog, filePath);
    }

    /**
     * Similar to {@link #saveFinanceLog(ReadOnlyFinanceLog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFinanceLog(ReadOnlyFinanceLog financeLog, Path filePath) throws IOException {
        requireNonNull(financeLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFinanceLog(financeLog), filePath);
    }

}
