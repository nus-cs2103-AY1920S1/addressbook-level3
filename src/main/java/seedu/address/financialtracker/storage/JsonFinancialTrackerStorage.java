package seedu.address.financialtracker.storage;

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
import seedu.address.financialtracker.model.FinancialTracker;

/**
 * A class to access FinancialTracker data stored as a json file on the hard disk.
 */
public class JsonFinancialTrackerStorage implements FinancialTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFinancialTrackerStorage.class);

    private Path filePath;

    public JsonFinancialTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFinancialTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<FinancialTracker> readFinancialTracker() throws DataConversionException {
        return readFinancialTracker(filePath);
    }

    /**
     * Similar to {@link #readFinancialTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<FinancialTracker> readFinancialTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFinancialTracker> jsonFinancialTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableFinancialTracker.class);
        if (jsonFinancialTracker.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFinancialTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFinancialTracker(FinancialTracker financialTracker) throws IOException {
        saveFinancialTracker(financialTracker, filePath);
    }

    /**
     * Similar to {@link #saveFinancialTracker(FinancialTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFinancialTracker(FinancialTracker financialTracker, Path filePath) throws IOException {
        requireNonNull(financialTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFinancialTracker(financialTracker), filePath);
    }

}
