package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.DataInconsistencyException;
import seedu.address.model.MooLah;
import seedu.address.model.ReadOnlyMooLah;

/**
 * Represents a storage for {@link MooLah}.
 */
public interface MooLahStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMooLahFilePath();

    /**
     * Returns MooLah data as a {@link ReadOnlyMooLah}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMooLah> readMooLah() throws DataConversionException, IOException, DataInconsistencyException;

    /**
     * @see #getMooLahFilePath()
     */
    Optional<ReadOnlyMooLah> readMooLah(Path filePath) throws DataConversionException, IOException,
            DataInconsistencyException;

    /**
     * Saves the given {@link ReadOnlyMooLah} to the storage.
     * @param mooLah cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMooLah(ReadOnlyMooLah mooLah) throws IOException;

    /**
     * @see #saveMooLah(ReadOnlyMooLah)
     */
    void saveMooLah(ReadOnlyMooLah mooLah, Path filePath) throws IOException;

}
