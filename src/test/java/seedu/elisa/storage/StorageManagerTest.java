package seedu.elisa.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.model.ItemStorage;
import seedu.elisa.model.UserPrefs;
import seedu.elisa.testutil.TypicalItems;

public class StorageManagerTest {


    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonItemStorage itemStorage = new JsonItemStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(itemStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }



    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    // test the itemstorage methods too
    public void itemStorageReadSave() throws Exception {
        ItemStorage original = new ItemStorage();
        original.add(TypicalItems.ITEM_WITH_ALL);
        original.add(TypicalItems.ITEM_WITH_EVENT);
        storageManager.saveItemStorage(original);
        ItemStorage retrieved = storageManager.toModelType();
        assertEquals(original, retrieved);
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getItemListFilePath());
    }


}
