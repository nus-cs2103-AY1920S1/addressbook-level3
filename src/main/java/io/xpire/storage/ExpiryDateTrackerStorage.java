package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ExpiryDateTracker;
import io.xpire.model.ReadOnlyExpiryDateTracker;

/**
 * Represents a storage for {@link ExpiryDateTracker}.
 */
public interface ExpiryDateTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExpiryDateTrackerFilePath();

    /**
     * Returns ExpiryDateTracker data as a {@link ReadOnlyExpiryDateTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker() throws DataConversionException, IOException;

    /**
     * @see #getExpiryDateTrackerFilePath()
     */
    Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyExpiryDateTracker} to the storage.
     * @param expiryDateTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker) throws IOException;

    /**
     * @see #saveExpiryDateTracker(ReadOnlyExpiryDateTracker)
     */
    void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker, Path filePath) throws IOException;

}
