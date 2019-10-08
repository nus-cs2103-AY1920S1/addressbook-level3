package calofit.storage;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link DishDatabase}.
 */
public interface DishDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDishDatabaseFilePath();

    /**
     * Returns DishDatabase data as a {@link ReadOnlyDishDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDishDatabase> readDishDatabase() throws DataConversionException, IOException;

    /**
     * @see #getDishDatabaseFilePath()
     */
    Optional<ReadOnlyDishDatabase> readDishDatabase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDishDatabase} to the storage.
     * @param dishDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDishDatabase(ReadOnlyDishDatabase dishDatabase) throws IOException;

    /**
     * @see #saveDishDatabase(ReadOnlyDishDatabase)
     */
    void saveDishDatabase(ReadOnlyDishDatabase dishDatabase, Path filePath) throws IOException;

}
