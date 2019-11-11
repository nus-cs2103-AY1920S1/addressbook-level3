package seedu.deliverymans.storage.order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;

/**
 * Represents a storage for {@link OrderDatabase}.
 */
public interface OrderDatabaseStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getOrderBookFilePath();

    /**
     * Returns OrderBook data as a {@link ReadOnlyOrderDatabase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrderDatabase> readOrderBook() throws DataConversionException, IOException;

    /**
     * @see #getOrderBookFilePath()
     */
    Optional<ReadOnlyOrderDatabase> readOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrderDatabase} to the storage.
     *
     * @param orderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrderDatabase(ReadOnlyOrderDatabase orderBook) throws IOException;

    /**
     * @see #saveOrderDatabase(ReadOnlyOrderDatabase)
     */
    void saveOrderDatabase(ReadOnlyOrderDatabase orderBook, Path filePath) throws IOException;
}
