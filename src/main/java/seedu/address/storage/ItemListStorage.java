package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for ELISA.
 */
public interface ItemListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getItemListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readItemList() throws DataConversionException, IOException;

    /**
     * @see #getItemListFilePath()
     */
    Optional<ReadOnlyAddressBook> readItemList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveItemList(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveItemList(ReadOnlyAddressBook)
     */
    void saveItemList(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
