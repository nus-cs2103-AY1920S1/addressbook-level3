package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.ReadOnlyUserState;

/**
 * Represents a storage for {@link seedu.address.model.BankAccount}.
 */
public interface BankAccountStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBankAccountFilePath();

    /**
     * Returns BankAccount data as a {@link ReadOnlyBankAccount}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserState> readAccount() throws DataConversionException, IOException;

    /**
     * @see #getBankAccountFilePath()
     */
    Optional<ReadOnlyUserState> readAccount(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBankAccount} to the storage.
     *
     * @param bankAccount cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccount(ReadOnlyUserState bankAccount) throws IOException;

    /**
     * @see #saveAccount(ReadOnlyUserState)
     */
    void saveAccount(ReadOnlyUserState bankAccount, Path filePath) throws IOException;

}
