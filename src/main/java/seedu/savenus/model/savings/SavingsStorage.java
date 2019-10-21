package seedu.savenus.model.savings;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@link SavingsAccount}.
 */
public interface SavingsStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSavingsAccountFilePath();

    /**
     * Returns SavingsAccount data as a {@link ReadOnlySavingsAccount}.
     * If storage file is not found, returns {@code Optional.empty()}.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySavingsAccount> readSavingsAccount() throws DataConversionException, IOException;

    /**
     * Implement another read method to read from filePath.
     */
    Optional<ReadOnlySavingsAccount> readSavingsAccount(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySavingsAccount} to the storage.
     * @param savingsAccount cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount) throws IOException;

    /**
     * @see #saveSavingsAccount(ReadOnlySavingsAccount)
     */
    void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException;
}
