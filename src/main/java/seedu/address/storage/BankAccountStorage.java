package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBankAccount;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBankAccount> readBankAccount() throws DataConversionException, IOException;

    /**
     * @see #getBankAccountFilePath()
     */
    Optional<ReadOnlyBankAccount> readBankAccount(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBankAccount} to the storage.
     * @param bankAccount cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBankAccount(ReadOnlyBankAccount bankAccount) throws IOException;

    /**
     * @see #saveBankAccount(ReadOnlyBankAccount)
     */
    void saveBankAccount(ReadOnlyBankAccount bankAccount, Path filePath) throws IOException;

}
