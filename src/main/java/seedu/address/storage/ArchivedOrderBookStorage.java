package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

/**
 * Represents a storage for {@link Order} {@link seedu.address.model.DataBook}.
 */
public interface ArchivedOrderBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchivedOrderBookFilePath();

    /**
     * Returns ArchivedOrder DataBook data as a {@link ReadOnlyDataBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDataBook<Order>> readArchivedOrderBook() throws DataConversionException, IOException;

    /**
     * @see #getArchivedOrderBookFilePath()
     */
    Optional<ReadOnlyDataBook<Order>> readArchivedOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDataBook} to the storage.
     * @param archivedOrderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook) throws IOException;

    /**
     * @see #saveArchivedOrderBook(ReadOnlyDataBook)
     */
    void saveArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook, Path filePath) throws IOException;

}
