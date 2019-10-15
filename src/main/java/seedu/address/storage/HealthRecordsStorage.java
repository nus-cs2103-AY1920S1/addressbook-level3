package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.profile.HealthRecords;
import seedu.address.profile.ReadOnlyHealthRecords;

/**
 * Represents a storage for {@link HealthRecords}.
 */
public interface HealthRecordsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHealthRecordsFilePath();

    /**
     * Returns HealthRecords data as a {@link ReadOnlyHealthRecords}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHealthRecords> readHealthRecords() throws DataConversionException, IOException;

    /**
     * @see #getHealthRecordsFilePath()
     */
    Optional<ReadOnlyHealthRecords> readHealthRecords(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHealthRecords} to the storage.
     * @param healthRecords cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHealthRecords(ReadOnlyHealthRecords healthRecords) throws IOException;

    /**
     * @see #saveHealthRecords(ReadOnlyHealthRecords)
     */
    void saveHealthRecords(ReadOnlyHealthRecords healthRecords, Path filePath) throws IOException;

}
