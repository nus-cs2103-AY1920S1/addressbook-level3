package seedu.deliverymans.storage.restaurant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;

/**
 * Represents a storage for {@link seedu.deliverymans.model.database.RestaurantDatabase}.
 */
public interface RestaurantDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRestaurantDatabaseFilePath();

    /**
     * Returns RestaurantDatabase data as a {@link ReadOnlyRestaurantDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase() throws DataConversionException, IOException;

    /**
     * @see #getRestaurantDatabaseFilePath()
     */
    Optional<ReadOnlyRestaurantDatabase> readRestaurantDatabase(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestaurantDatabase} to the storage.
     * @param restaurantDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase) throws IOException;

    /**
     * @see #saveRestaurantDatabase(ReadOnlyRestaurantDatabase)
     */
    void saveRestaurantDatabase(ReadOnlyRestaurantDatabase restaurantDatabase, Path filePath) throws IOException;

}
