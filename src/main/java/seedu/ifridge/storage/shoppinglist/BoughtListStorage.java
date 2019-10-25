package seedu.ifridge.storage.shoppinglist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;

/**
 * Represents a storage for {@link seedu.ifridge.model.GroceryList}.
 */
public interface BoughtListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBoughtListFilePath();

    /**
     * Returns ShoppingList data as a {@link ReadOnlyGroceryList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGroceryList> readBoughtList() throws DataConversionException, IOException;

    /**
     * @see #getShoppingListFilePath()
     */
    Optional<ReadOnlyGroceryList> readBoughtList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGroceryList} to the storage.
     * @param boughtList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBoughtList(ReadOnlyGroceryList boughtList) throws IOException;

    /**
     * @see #saveShoppingList(ReadOnlyShoppingList, Path)
     */
    void saveBoughtList(ReadOnlyGroceryList boughtList, Path filePath) throws IOException;

}
