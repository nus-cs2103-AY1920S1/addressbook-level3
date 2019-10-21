package seedu.address.storage.globalstatistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.globalstatistics.GlobalStatistics;

/**
 * Represents a storage for {@link GlobalStatistics}.
 */
public interface GlobalStatisticsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGlobalStatisticsFilePath();

    /**
     * Returns word bank statistics data as a {@link GlobalStatistics}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<GlobalStatistics> readGlobalStatistics() throws DataConversionException, IOException;

    /**
     * @see #getGlobalStatisticsFilePath()
     */
    Optional<GlobalStatistics> readGlobalStatistics(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link GlobalStatistics} to the storage.
     * @param statistics The statistics to be saved, cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGlobalStatistics(GlobalStatistics statistics) throws IOException;

    void saveGlobalStatistics(GlobalStatistics statistics, Path filePath) throws IOException;
}
