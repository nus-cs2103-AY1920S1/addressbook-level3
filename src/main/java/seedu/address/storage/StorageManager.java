package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyGroceryList;
import seedu.address.model.ReadOnlyShoppingList;
import seedu.address.model.ReadOnlyTemplateList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteMonth;
import seedu.address.storage.shoppinglist.ShoppingListStorage;
import seedu.address.storage.wastelist.WasteListStorage;


/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GroceryListStorage groceryListStorage;
    private TemplateListStorage templateListStorage;
    private WasteListStorage wasteListStorage;
    private ShoppingListStorage shoppingListStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(GroceryListStorage groceryListStorage, UserPrefsStorage userPrefsStorage,
                          TemplateListStorage templateListStorage, WasteListStorage wasteListStorage,
                          ShoppingListStorage shoppingListStorage) {
        super();
        this.groceryListStorage = groceryListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.templateListStorage = templateListStorage;
        this.wasteListStorage = wasteListStorage;
        this.shoppingListStorage = shoppingListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getGroceryListFilePath() {
        return groceryListStorage.getGroceryListFilePath();
    }

    @Override
    public Optional<ReadOnlyGroceryList> readGroceryList() throws DataConversionException, IOException {
        return readGroceryList(groceryListStorage.getGroceryListFilePath());
    }

    @Override
    public Optional<ReadOnlyGroceryList> readGroceryList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return groceryListStorage.readGroceryList(filePath);
    }

    @Override
    public void saveGroceryList(ReadOnlyGroceryList groceryList) throws IOException {
        saveGroceryList(groceryList, groceryListStorage.getGroceryListFilePath());
    }

    @Override
    public void saveGroceryList(ReadOnlyGroceryList groceryList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        groceryListStorage.saveGroceryList(groceryList, filePath);
    }

    // ================ TemplateList methods ==============================

    @Override
    public Path getTemplateListFilePath() {
        return templateListStorage.getTemplateListFilePath();
    }

    @Override
    public Optional<ReadOnlyTemplateList> readTemplateList() throws DataConversionException, IOException {
        return readTemplateList(templateListStorage.getTemplateListFilePath());
    }

    @Override
    public Optional<ReadOnlyTemplateList> readTemplateList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return templateListStorage.readTemplateList(filePath);
    }

    @Override
    public void saveTemplateList(ReadOnlyTemplateList templateList) throws IOException {
        saveTemplateList(templateList, templateListStorage.getTemplateListFilePath());
    }

    @Override
    public void saveTemplateList(ReadOnlyTemplateList templateList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        templateListStorage.saveTemplateList(templateList, filePath);
    }

    // ================ WasteList methods ==============================

    @Override
    public Path getWasteListFilePath() {
        return wasteListStorage.getWasteListFilePath();
    }

    @Override
    public Optional<TreeMap<WasteMonth, WasteList>> readWasteList() throws DataConversionException, IOException {
        return readWasteList(wasteListStorage.getWasteListFilePath());
    }

    @Override
    public Optional<TreeMap<WasteMonth, WasteList>> readWasteList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return wasteListStorage.readWasteList(filePath);
    }

    @Override
    public void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive) throws IOException {
        saveWasteList(wasteArchive, wasteListStorage.getWasteListFilePath());
    }

    @Override
    public void saveWasteList(TreeMap<WasteMonth, WasteList> wasteArchive, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        wasteListStorage.saveWasteList(wasteArchive, filePath);
    }

    // ================ ShoppingList methods ==============================

    @Override
    public Path getShoppingListFilePath() {
        return shoppingListStorage.getShoppingListFilePath();
    }

    @Override
    public Optional<ReadOnlyShoppingList> readShoppingList() throws DataConversionException, IOException {
        return readShoppingList(shoppingListStorage.getShoppingListFilePath());
    }

    @Override
    public Optional<ReadOnlyShoppingList> readShoppingList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return shoppingListStorage.readShoppingList(filePath);
    }

    @Override
    public void saveShoppingList(ReadOnlyShoppingList shoppingList) throws IOException {
        saveShoppingList(shoppingList, shoppingListStorage.getShoppingListFilePath());
    }

    @Override
    public void saveShoppingList(ReadOnlyShoppingList shoppingList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        shoppingListStorage.saveShoppingList(shoppingList, filePath);
    }


}
