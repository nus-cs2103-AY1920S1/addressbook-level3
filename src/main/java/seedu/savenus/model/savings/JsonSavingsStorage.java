package seedu.savenus.model.savings;

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
    public Path getSavingsAccountFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException, IOException {
        return readSavingsAccount(filePath);
    }

    @Override
    public Optional<ReadOnlySavingsAccount> readSavingsAccount(Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableSavingsAccount> jsonSavingsAccount = JsonUtil.readJsonFile(filePath,
                JsonSerializableSavingsAccount.class);

        if (jsonSavingsAccount.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSavingsAccount.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info(("Illegal values found in " + filePath + e.getMessage()));
            throw new DataConversionException(e);
        }
    }

    /**
     * Write into the user's savings account from the unmodifiable savings account.
     *
     * @param savingsAccount unmodifiable savings account of the user.
     * @throws IOException from {@link #saveSavingsAccount(ReadOnlySavingsAccount, Path)}
     */
    @Override
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException {
        saveSavingsAccount(savingsAccount, filePath);
    }

    /**
     * Similar to {@link #saveSavingsAccount(ReadOnlySavingsAccount)}.
     *
     * @param savingsAccount savingsAccount of user must be provided.
     * @param filePath location of the savings account data cannot be null.
     * @throws IOException when writing to the file fails.
     */
    @Override
    public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException {
        requireNonNull(savingsAccount);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSavingsAccount(savingsAccount), filePath);
    }
}
