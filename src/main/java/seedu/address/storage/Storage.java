package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeMap;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyGroceryList;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteMonth;
import seedu.address.storage.shoppinglist.BoughtListStorage;
import seedu.address.storage.shoppinglist.ShoppingListStorage;
import seedu.address.storage.wastelist.WasteListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends GroceryListStorage, UserPrefsStorage, TemplateListStorage, WasteListStorage,
        ShoppingListStorage, BoughtListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getGroceryListFilePath();

    @Override
    Optional<ReadOnlyGroceryList> readGroceryList() throws DataConversionException, IOException;

    @Override
    void saveGroceryList(ReadOnlyGroceryList addressBook) throws IOException;

    @Override
    Path getTemplateListFilePath();

    @Override
    Optional<ReadOnlyTemplateList> readTemplateList() throws DataConversionException, IOException;

    @Override
    void saveTemplateList(ReadOnlyTemplateList templateList) throws IOException;

    @Override
    Path getWasteListFilePath();

    @Override
    Optional<TreeMap<WasteMonth, WasteList>> readWasteList() throws DataConversionException, IOException;

    @Override
    void saveWasteList(TreeMap<WasteMonth, WasteList> wasteList) throws IOException;

    @Override
    Path getShoppingListFilePath();

    @Override
    Optional<ReadOnlyShoppingList> readShoppingList() throws DataConversionException, IOException;

    @Override
    void saveShoppingList(ReadOnlyShoppingList shoppingList) throws IOException;

    @Override
    Path getBoughtListFilePath();

    @Override
    Optional<ReadOnlyGroceryList> readBoughtList() throws DataConversionException, IOException;

    @Override
    void saveBoughtList(ReadOnlyGroceryList boughtList) throws IOException;
}
