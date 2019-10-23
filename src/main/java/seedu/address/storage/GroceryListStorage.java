package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.GroceryList;
import seedu.address.model.ReadOnlyGroceryList;

/**
 * Represents a storage for {@link GroceryList}.
 */
public interface GroceryListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGroceryListFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyGroceryList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGroceryList> readGroceryList() throws DataConversionException, IOException;

    /**
     * @see #getGroceryListFilePath()
     */
    Optional<ReadOnlyGroceryList> readGroceryList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGroceryList} to the storage.
     * @param groceryList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGroceryList(ReadOnlyGroceryList groceryList) throws IOException;

    /**
     * @see #saveGroceryList(ReadOnlyGroceryList)
     */
    void saveGroceryList(ReadOnlyGroceryList groceryList, Path filePath) throws IOException;

}
