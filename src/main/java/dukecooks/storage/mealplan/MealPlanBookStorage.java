package dukecooks.storage.mealplan;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;

/**
 * Represents a storage for {@link MealPlanBook}.
 */
public interface MealPlanBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMealPlansFilePath();

    /**
     * Returns MealPlanBook data as a {@link ReadOnlyMealPlanBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMealPlanBook> readMealPlanBook() throws DataConversionException, IOException;

    /**
     * @see #getMealPlansFilePath()
     */
    Optional<ReadOnlyMealPlanBook> readMealPlanBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMealPlanBook} to the storage.
     * @param mealPlanBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMealPlanBook(ReadOnlyMealPlanBook mealPlanBook) throws IOException;

    /**
     * @see #saveMealPlanBook(ReadOnlyMealPlanBook)
     */
    void saveMealPlanBook(ReadOnlyMealPlanBook mealPlanBook, Path filePath) throws IOException;

}
