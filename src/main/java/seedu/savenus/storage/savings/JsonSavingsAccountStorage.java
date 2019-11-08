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
import seedu.savenus.model.savings.ReadOnlySavingsAccount;


/**
 * A class to access SavingsAccount data stored as a json file on the hard disk.
 */
public class JsonSavingsAccountStorage implements SavingsAccountStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSavingsAccountStorage.class);

    private Path filePath;

    public JsonSavingsAccountStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSavingsAccountFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException {
        return readSavingsAccount(filePath);
    }

    /**
     * Similar to {@link #readSavingsAccount()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySavingsAccount> readSavingsAccount(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSavingsAccount> jsonSavingsAccount = JsonUtil.readJsonFile(
                filePath, JsonSerializableSavingsAccount.class);
        if (jsonSavingsAccount.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSavingsAccount.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException {
        saveSavingsAccount(savingsAccount, filePath);
    }

    /**
     * Similar to {@link #saveSavingsAccount(ReadOnlySavingsAccount)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException {
        requireNonNull(savingsAccount);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSavingsAccount(savingsAccount), filePath);
    }

}
