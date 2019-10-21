package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;

/**
 * Represents a storage for {@link Customer} {@link seedu.address.model.DataBook}.
 */
public interface CustomerBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCustomerBookFilePath();

    /**
     * Returns customer DataBook data as a {@link ReadOnlyDataBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDataBook<Customer>> readCustomerBook() throws DataConversionException, IOException;

    /**
     * @see #getCustomerBookFilePath()
     */
    Optional<ReadOnlyDataBook<Customer>> readCustomerBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDataBook} to the storage.
     * @param customerBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook) throws IOException;

    /**
     * @see #saveCustomerBook(ReadOnlyDataBook)
     */
    void saveCustomerBook(ReadOnlyDataBook<Customer> customerBook, Path filePath) throws IOException;

}
