package seedu.ifridge.storage.shoppinglist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;

/**
<<<<<<< HEAD
 * Represents a storage for {@link seedu.ifridge.model.GroceryList}.
=======
 * Represents a storage for {@link GroceryList}.
>>>>>>> master
 */
public interface ShoppingListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getShoppingListFilePath();

    /**
     * Returns ShoppingList data as a {@link ReadOnlyShoppingList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyShoppingList> readShoppingList() throws DataConversionException, IOException;

    /**
     * @see #getShoppingListFilePath()
     */
    Optional<ReadOnlyShoppingList> readShoppingList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyShoppingList} to the storage.
     * @param shoppingList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveShoppingList(ReadOnlyShoppingList shoppingList) throws IOException;

    /**
     * @see #saveShoppingList(ReadOnlyShoppingList, Path)
     */
    void saveShoppingList(ReadOnlyShoppingList shoppingList, Path filePath) throws IOException;

}
