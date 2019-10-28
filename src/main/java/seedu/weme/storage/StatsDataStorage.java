package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.statistics.Stats;

/**
 * An interface for statistics data storage access.
 */
public interface StatsDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStatsDataPath();

    /**
     * Returns statistical data as a {@link Stats}.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Stats> readStatsData() throws DataConversionException, IOException;

    /**
     * @see #getStatsDataPath()
     */
    Optional<Stats> readStatsData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Stats} to the storage.
     * @param stats cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStatsData(Stats stats) throws IOException;

    /**
     * @see #saveStatsData(Stats)
     */
    void saveStatsData(Stats stats, Path filePath) throws IOException;

}
