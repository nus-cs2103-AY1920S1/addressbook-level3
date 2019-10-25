package seedu.ifridge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.storage.shoppinglist.JsonBoughtItemStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingItemStorage;
import seedu.ifridge.storage.wastelist.JsonWasteListStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonGroceryListStorage groceryListStorage = new JsonGroceryListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonTemplateListStorage templateListStorage = new JsonTemplateListStorage(getTempFilePath("ac"));
        JsonWasteListStorage wasteListStorage = new JsonWasteListStorage(getTempFilePath("ad"));
        JsonShoppingItemStorage shoppingListStorage = new JsonShoppingItemStorage(getTempFilePath("ae"));
        JsonBoughtItemStorage boughtListStorage = new JsonBoughtItemStorage(getTempFilePath("af"));
        storageManager = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage,
                wasteListStorage, shoppingListStorage, boughtListStorage);
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

    /*@Test
    public void addressBookReadSave() throws Exception {*/
    /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonGroceryListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonGroceryListStorageTest} class.
    */
    /*   GroceryList original = getTypicalGroceryList();
        storageManager.saveAddressBook(original);
        ReadOnlyGroceryList retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new GroceryList(retrieved));
    }*/

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getGroceryListFilePath());
    }

}
