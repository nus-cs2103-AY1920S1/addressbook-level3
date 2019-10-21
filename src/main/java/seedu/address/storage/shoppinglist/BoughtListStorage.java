package seedu.address.storage.shoppinglist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface BoughtListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBoughtListFilePath();

    /**
     * Returns ShoppingList data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readBoughtList() throws DataConversionException, IOException;

    /**
     * @see #getShoppingListFilePath()
     */
    Optional<ReadOnlyAddressBook> readBoughtList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param boughtList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBoughtList(ReadOnlyAddressBook boughtList) throws IOException;

    /**
     * @see #saveShoppingList(ReadOnlyShoppingList, Path)
     */
    void saveBoughtList(ReadOnlyAddressBook boughtList, Path filePath) throws IOException;

}
