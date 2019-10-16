package seedu.address.storage.finance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.finance.ReadOnlyFinanceLog;


/**
 * Represents a storage for {@link seedu.address.model.finance}.
 */
public interface FinanceStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyFinanceLog}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinanceLog> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyFinanceLog> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinanceLog} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyFinanceLog addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyFinanceLog)
     */
    void saveAddressBook(ReadOnlyFinanceLog addressBook, Path filePath) throws IOException;

}
