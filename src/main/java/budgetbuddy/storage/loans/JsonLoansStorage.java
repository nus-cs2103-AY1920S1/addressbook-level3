package budgetbuddy.storage.loans;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.commons.util.FileUtil;
import budgetbuddy.commons.util.JsonUtil;
import budgetbuddy.model.LoansManager;

/**
 * A class to access LoansManager data stored as a json file on the hard disk.
 */
public class JsonLoansStorage implements LoansStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLoansStorage.class);

    private Path filePath;

    public JsonLoansStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLoansFilePath() {
        return filePath;
    }

    @Override
    public Optional<LoansManager> readLoans() throws DataConversionException {
        return readLoans(filePath);
    }

    /**
     * Similar to {@link #readLoans()}.
     * @param filePath Location of the data. Cannot be null.
     * @throws DataConversionException If the data file is not in the correct format.
     */
    public Optional<LoansManager> readLoans(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableLoansManager> jsonLoansManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableLoansManager.class);
        if (jsonLoansManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLoansManager.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveLoans(LoansManager loansManager) throws IOException {
        saveLoans(loansManager, filePath);
    }

    /**
     * Similar to {@link #saveLoans(LoansManager)}
     *
     * @param filePath Location of the data. Cannot be null.
     */
    public void saveLoans(LoansManager loansManager, Path filePath) throws IOException {
        requireAllNonNull(loansManager, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLoansManager(loansManager), filePath);
    }
}
