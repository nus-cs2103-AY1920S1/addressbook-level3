package seedu.address.storage.globalstatistics;

import java.io.IOException;
import java.nio.file.Path;

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
     * Saves the given {@link GlobalStatistics} to the storage.
     * @param statistics The statistics to be saved, cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGlobalStatistics(GlobalStatistics statistics) throws IOException;

    void saveGlobalStatistics(GlobalStatistics statistics, Path filePath) throws IOException;

    GlobalStatistics getGlobalStatistics();
}
