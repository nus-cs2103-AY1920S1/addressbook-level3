package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.LikeDataImpl;

/**
 * Represents a storage for {@link LikeDataImpl}.
 */
public interface LikeDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLikeDataFilePath();

    /**
     * Returns MemeLikes data as a {@link LikeDataImpl}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<LikeData> readLikeData() throws DataConversionException, IOException;

    /**
     * @see #getLikeDataFilePath()
     */
    Optional<LikeData> readLikeData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link LikeDataImpl} to the storage.
     * @param likeData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLikeData(LikeDataImpl likeData) throws IOException;

    /**
     * @see #saveLikeData(LikeDataImpl)
     */
    void saveLikeData(LikeDataImpl likeData, Path filePath) throws IOException;

}
