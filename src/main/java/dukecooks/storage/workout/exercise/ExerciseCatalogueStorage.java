package dukecooks.storage.workout.exercise;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;

/**
 * Represents a storage for {@link ExerciseCatalogue}.
 */
public interface ExerciseCatalogueStorage {

    /**
     * Returns the file path of the exercises data file.
     */
    Path getExerciseFilePath();

    /**
     * Returns DukeCooks data as a {@link ReadOnlyExerciseCatalogue}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue() throws DataConversionException, IOException;

    /**
     * @see #getExerciseFilePath()
     */
    Optional<ReadOnlyExerciseCatalogue> readExerciseCatalogue(Path exerciseFilePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyExerciseCatalogue} to the storage.
     * @param dukeCooks cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExerciseCatalogue(ReadOnlyExerciseCatalogue dukeCooks) throws IOException;

    /**
     * @see #saveExerciseCatalogue(ReadOnlyExerciseCatalogue)
     */
    void saveExerciseCatalogue(ReadOnlyExerciseCatalogue dukeCooks, Path filePath) throws IOException;

}
