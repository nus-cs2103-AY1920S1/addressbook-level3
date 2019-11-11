package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AccountBook;
import seedu.address.model.account.Account;

/**
 * Represents a storage for {@link seedu.address.model.account.Account}.
 */
public interface AccountStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccountBookFilePath();

    /**
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AccountBook> getAccountsList() throws DataConversionException, IOException;

    /**
     * @see #getAccountsList()
     */
    Optional<AccountBook> getAccountsList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given account to the storage.
     * @param account cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccount(Account account) throws IOException;

    /**
     * @see #saveAccount(Account)
     */
    void saveAccount(Account account, Path filePath) throws IOException;

}
