package seedu.deliverymans.storage.deliveryman;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;

/**
 * Represents a storage for {@link seedu.deliverymans.model.database.DeliverymenDatabase}.
 */
public interface DeliverymenDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDeliverymenDatabaseFilePath();

    /**
     * Returns DeliverymenDatabase data as a {@link ReadOnlyDeliverymenDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase() throws DataConversionException, IOException;

    /**
     * @see #getDeliverymenDatabaseFilePath()
     */
    Optional<ReadOnlyDeliverymenDatabase> readDeliverymenDatabase(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDeliverymenDatabase} to the storage.
     * @param deliverymenDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase) throws IOException;

    /**
     * @see #saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase)
     */
    void saveDeliverymenDatabase(ReadOnlyDeliverymenDatabase deliverymenDatabase, Path filePath) throws IOException;

}
