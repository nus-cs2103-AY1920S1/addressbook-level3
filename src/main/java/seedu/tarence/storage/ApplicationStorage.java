package seedu.tarence.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;

/**
 * Represents a storage for {@link Application}.
 */
public interface ApplicationStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getApplicationFilePath();

    /**
     * Returns application data as a {@link ReadOnlyApplication}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyApplication> readApplication() throws DataConversionException, IOException;

    /**
     * @see #getApplicationFilePath()
     */
    Optional<ReadOnlyApplication> readApplication(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyApplication} to the storage.
     * @param application cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveApplication(ReadOnlyApplication application) throws IOException;

    /**
     * @see #saveApplication(ReadOnlyApplication)
     */
    void saveApplication(ReadOnlyApplication application, Path filePath) throws IOException;

}
