package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.statistics.StatsEngine;

/**
 * An interface for statistics data storage access.
 */
public interface StatsDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStatsDataPath();

    /**
     * Returns statistical data as a {@link StatsEngine}.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<StatsEngine> readStatsData() throws DataConversionException, IOException;

    /**
     * @see #getStatsDataPath()
     */
    Optional<StatsEngine> readStatsData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link StatsEngine} to the storage.
     * @param statsEngine cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStatsData(StatsEngine statsEngine) throws IOException;

    /**
     * @see #saveStatsData(StatsEngine)
     */
    void saveStatsData(StatsEngine statsEngine, Path filePath) throws IOException;

}
