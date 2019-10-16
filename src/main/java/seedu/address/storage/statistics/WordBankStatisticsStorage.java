package seedu.address.storage.statistics;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.statistics.WordBankStatistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link WordBankStatistics}.
 */
public interface WordBankStatisticsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWordBankStatisticsFilePath();

    /**
     * Returns word bank statistics data as a {@link WordBankStatistics}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<WordBankStatistics> readWordBankStatistics() throws DataConversionException, IOException;

    /**
     * @see #getWordBankStatisticsFilePath()
     */
    Optional<WordBankStatistics> readWordBankStatistics(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.statistics.GameStatistics} to the storage.
     * @param statistics The statistics to be saved, cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBankStatistics(WordBankStatistics statistics) throws IOException;

    void saveWordBankStatistics(WordBankStatistics statistics, Path filePath) throws IOException;
}
