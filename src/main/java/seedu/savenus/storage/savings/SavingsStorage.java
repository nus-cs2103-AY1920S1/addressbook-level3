package seedu.savenus.storage.savings;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.savings.ReadOnlySavingsHistory;
import seedu.savenus.model.savings.SavingsHistory;

/**
 * Represents a storage for {@link SavingsHistory}.
 */
public interface SavingsStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSavingsHistoryFilePath();

    /**
     * Returns SavingsHistory data as a {@link ReadOnlySavingsHistory}.
     * If storage file is not found, returns {@code Optional.empty()}.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySavingsHistory> readSavingsHistory() throws DataConversionException, IOException;

    /**
     * Implement another read method to read from filePath.
     */
    Optional<ReadOnlySavingsHistory> readSavingsHistory(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySavingsHistory} to the storage.
     * @param savingsHistory cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSavingsHistory(ReadOnlySavingsHistory savingsHistory) throws IOException;

    /**
     * @see #saveSavingsHistory(ReadOnlySavingsHistory)
     */
    void saveSavingsHistory(ReadOnlySavingsHistory savingsAccount, Path filePath) throws IOException;
}
