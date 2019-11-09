package seedu.ifridge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.storage.shoppinglist.JsonBoughtListStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingListStorage;
import seedu.ifridge.storage.unitdictionary.JsonUnitDictionaryStorage;
import seedu.ifridge.storage.wastelist.JsonWasteListStorage;
import seedu.ifridge.testutil.TypicalGroceryItems;
import seedu.ifridge.testutil.TypicalShoppingList;
import seedu.ifridge.testutil.TypicalTemplateList;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonGroceryListStorage groceryListStorage = new JsonGroceryListStorage(getTempFilePath("glist"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonTemplateListStorage templateListStorage = new JsonTemplateListStorage(getTempFilePath("tlist"));
        JsonWasteListStorage wasteListStorage = new JsonWasteListStorage(getTempFilePath("wlist"));
        JsonShoppingListStorage shoppingListStorage = new JsonShoppingListStorage(getTempFilePath("slist"));
        JsonBoughtListStorage boughtListStorage = new JsonBoughtListStorage(getTempFilePath("blist"));
        JsonUnitDictionaryStorage unitDictionaryStorage = new JsonUnitDictionaryStorage(getTempFilePath("ud"));
        storageManager = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage,
                wasteListStorage, shoppingListStorage, boughtListStorage, unitDictionaryStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void groceryListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonGroceryListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonGroceryListStorageTest} class.
        */
        GroceryList original = TypicalGroceryItems.getTypicalGroceryList();
        storageManager.saveGroceryList(original);
        ReadOnlyGroceryList retrieved = storageManager.readGroceryList().get();
        assertEquals(original, new GroceryList(retrieved));
    }

    @Test
    public void templateListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTemplateListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTemplateListStorageTest} class.
         */
        TemplateList original = TypicalTemplateList.getTypicalTemplateList();
        storageManager.saveTemplateList(original);
        ReadOnlyTemplateList retrieved = storageManager.readTemplateList().get();
        assertEquals(original, new TemplateList(retrieved));
    }

    @Test
    public void shoppingListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonShoppingListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonShoppingListStorageTest} class.
         */
        ShoppingList original = TypicalShoppingList.getTypicalShoppingList();
        storageManager.saveShoppingList(original);
        ReadOnlyShoppingList retrieved = storageManager.readShoppingList().get();
        assertEquals(original, new ShoppingList(retrieved));
    }

    @Test
    public void getGroceryListFilePath() {
        assertNotNull(storageManager.getGroceryListFilePath());
    }

    @Test
    public void getTemplateListFilePath() {
        assertNotNull(storageManager.getTemplateListFilePath());
    }

    @Test
    public void getShoppingListFilePath() {
        assertNotNull(storageManager.getShoppingListFilePath());
    }

    @Test
    public void getWasteListFilePath() {
        assertNotNull(storageManager.getWasteListFilePath());
    }
}
