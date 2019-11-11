package seedu.jarvis.storage.finance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.finance.FinanceTracker;

/**
 * Represents a storage for {@link FinanceTracker}.
 */
public interface FinanceTrackerStorage {
    /**
     * Gets the file path of the data file for {@code FinanceTracker}.
     *
     * @return File path of the data file for {@code FinanceTracker}.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<FinanceTracker> readFinanceTracker() throws DataConversionException, IOException;

    /**
     * Returns {@code FinanceTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code FinanceTracker} data.
     * @return {@code FinanceTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<FinanceTracker> readFinanceTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveFinanceTracker(FinanceTracker financeTracker) throws IOException;

    /**
     * Saves the given {@link FinanceTracker} to the storage.
     *
     * @param financeTracker {@code FinanceTracker} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code FinanceTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveFinanceTracker(FinanceTracker financeTracker, Path filePath) throws IOException;
}

