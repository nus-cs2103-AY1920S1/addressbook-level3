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
public interface WorkoutPlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWorkoutPlannerFilePath();

    /**
     * Returns DukeCooks data as a {@link ReadOnlyWorkoutPlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner() throws DataConversionException, IOException;

    /**
     * @see #getWorkoutPlannerFilePath()
     */
    Optional<ReadOnlyWorkoutPlanner> readWorkoutPlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWorkoutPlanner} to the storage.
     * @param dukeCooks cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks) throws IOException;

    /**
     * @see #saveWorkoutPlanner(ReadOnlyWorkoutPlanner)
     */
    void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks, Path filePath) throws IOException;

}
