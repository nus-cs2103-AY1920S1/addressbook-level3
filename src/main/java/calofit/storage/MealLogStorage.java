package calofit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;

/**
 * Represents a storage for {@link MealLog}.
 */
public interface MealLogStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMealLogFilePath();

    /**
     * Returns MealLog data as a {@link ReadOnlyMealLog}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMealLog> readMealLog() throws DataConversionException, IOException;

    /**
     * @see #getMealLogFilePath() ()
     */
    Optional<ReadOnlyMealLog> readMealLog(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMealLog} to the storage.
     * @param mealLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMealLog(ReadOnlyMealLog mealLog) throws IOException;

    /**
     * @see #saveMealLog(ReadOnlyMealLog)
     */
    void saveMealLog(ReadOnlyMealLog mealLog, Path filePath) throws IOException;
}
