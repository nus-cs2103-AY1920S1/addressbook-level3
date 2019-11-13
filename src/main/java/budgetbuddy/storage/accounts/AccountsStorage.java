package budgetbuddy.storage.accounts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.AccountsManager;

/**
 * Represents a storage for {@link AccountsManager}.
 */
public interface AccountsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccountsFilePath();

    /**
     * Returns AccountsManager data as a {@link AccountsManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException If the data in storage is not in the expected format.
     * @throws IOException If there was any problem when reading from the storage.
     */
    Optional<AccountsManager> readAccounts() throws DataConversionException, IOException;

    /**
     * @see #readAccounts()
     */
    Optional<AccountsManager> readAccounts(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link AccountsManager} to the storage.
     * @param accountsManager Cannot be null.
     * @throws IOException If any problem occurs when writing to the file.
     */
    void saveAccounts(AccountsManager accountsManager) throws IOException;

    /**
     * @param filePath The path to save the data file to.
     * @see #saveAccounts(AccountsManager)
     */
    void saveAccounts(AccountsManager accountsManager, Path filePath) throws IOException;
}

