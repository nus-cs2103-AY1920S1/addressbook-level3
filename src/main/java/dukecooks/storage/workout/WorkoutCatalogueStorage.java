package dukecooks.storage.workout;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link dukecooks.model.workout.WorkoutCatalogue}.
 */
public interface WorkoutCatalogueStorage {

    /**
     * Returns the file path of the workouts data file.
     */
    Path getWorkoutFilePath();

    /**
     * Returns DukeCooks data as a {@link ReadOnlyWorkoutCatalogue}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue() throws DataConversionException, IOException;

    /**
     * @see #getWorkoutFilePath()
     */
    Optional<ReadOnlyWorkoutCatalogue> readWorkoutCatalogue(Path workoutFilePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyWorkoutCatalogue} to the storage.
     * @param workoutCatalogue cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue) throws IOException;

    /**
     * @see #saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue)
     */
    void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue workoutCatalogue, Path filePath) throws IOException;

}
