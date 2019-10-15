package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.WorkoutPlanner;

/**
 * Represents a storage for {@link WorkoutPlanner}.
 */
public interface DukeCooksStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDukeCooksFilePath();

    /**
     * Returns DukeCooks data as a {@link ReadOnlyWorkoutPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWorkoutPlanner> readDukeCooks() throws DataConversionException, IOException;

    /**
     * @see #getDukeCooksFilePath()
     */
    Optional<ReadOnlyWorkoutPlanner> readDukeCooks(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWorkoutPlanner} to the storage.
     * @param dukeCooks cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks) throws IOException;

    /**
     * @see #saveDukeCooks(ReadOnlyWorkoutPlanner)
     */
    void saveDukeCooks(ReadOnlyWorkoutPlanner dukeCooks, Path filePath) throws IOException;

}
