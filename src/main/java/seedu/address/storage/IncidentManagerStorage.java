package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.IncidentManager;
import seedu.address.model.ReadOnlyIncidentManager;

/**
 * Represents a storage for {@link IncidentManager}.
 */
public interface IncidentManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getIncidentManagerFilePath();

    /**
     * Returns IncidentManager data as a {@link ReadOnlyIncidentManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyIncidentManager> readIncidentManager() throws DataConversionException, IOException;

    /**
     * @see #getIncidentManagerFilePath()
     */
    Optional<ReadOnlyIncidentManager> readIncidentManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyIncidentManager} to the storage.
     * @param incidentManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveIncidentManager(ReadOnlyIncidentManager incidentManager) throws IOException;

    /**
     * @see #saveIncidentManager(ReadOnlyIncidentManager)
     */
    void saveIncidentManager(ReadOnlyIncidentManager incidentManager, Path filePath) throws IOException;

}
