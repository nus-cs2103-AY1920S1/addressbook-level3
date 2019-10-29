package budgetbuddy.storage.accounts;

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
import budgetbuddy.model.AccountsManager;

/**
 * A class to access AccountsManager data stored as a json file on the hard disk.
 */
public class JsonAccountsStorage implements AccountsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAccountsStorage.class);

    private Path filePath;

    public JsonAccountsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccountsFilePath() {
        return filePath;
    }

    @Override
    public Optional<AccountsManager> readAccounts() throws DataConversionException {
        return readAccounts(filePath);
    }

    /**
     * Similar to {@link #readAccounts()}.
     * @param filePath Location of the data. Cannot be null.
     * @throws DataConversionException If the data file is not in the correct format.
     */
    public Optional<AccountsManager> readAccounts(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAccountsManager> jsonAccountsManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccountsManager.class);
        if (jsonAccountsManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAccountsManager.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveAccounts(AccountsManager accountsManager) throws IOException {
        saveAccounts(accountsManager, filePath);
    }

    /**
     * Similar to {@link #saveAccounts(AccountsManager)}
     *
     * @param filePath Location of the data. Cannot be null.
     */
    public void saveAccounts(AccountsManager accountsManager, Path filePath) throws IOException {
        requireAllNonNull(accountsManager, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAccountsManager(accountsManager), filePath);
    }
}
