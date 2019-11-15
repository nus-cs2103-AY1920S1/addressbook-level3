package seedu.guilttrip.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;

/**
 * Represents a storage for {@link GuiltTrip}.
 */
public interface GuiltTripStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGuiltTripFilePath();

    /**
     * Returns GuiltTrip data as a {@link ReadOnlyGuiltTrip}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGuiltTrip> readGuiltTrip() throws DataConversionException, IOException;

    /**
     * @see #getGuiltTripFilePath()
     */
    Optional<ReadOnlyGuiltTrip> readGuiltTrip(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGuiltTrip} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGuiltTrip(ReadOnlyGuiltTrip addressBook) throws IOException;

    /**
     * @see #saveGuiltTrip(ReadOnlyGuiltTrip)
     */
    void saveGuiltTrip(ReadOnlyGuiltTrip addressBook, Path filePath) throws IOException;

}
