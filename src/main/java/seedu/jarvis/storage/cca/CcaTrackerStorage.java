package seedu.jarvis.storage.cca;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.cca.CcaTracker;

/**
 * Represents a storage for {@link CcaTracker}.
 */
public interface CcaTrackerStorage {

    /**
     * Gets the file path of the data file for {@code CcaTracker}.
     *
     * @return File path of the data file for {@code CcaTracker}.
     */
    Path getCcaTrackerFilePath();

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CcaTracker> readCcaTracker() throws DataConversionException, IOException;

    /**
     * Returns {@code CcaTracker} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code CcaTracker} data.
     * @return {@code CcaTracker} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CcaTracker> readCcaTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveCcaTracker(CcaTracker ccaTracker) throws IOException;

    /**
     * Saves the given {@link CcaTracker} to the storage.
     *
     * @param ccaTracker {@code CcaTracker} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code CcaTracker} data.
     * @throws IOException If there was any problem writing to the file.
     */
    void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException;

}
