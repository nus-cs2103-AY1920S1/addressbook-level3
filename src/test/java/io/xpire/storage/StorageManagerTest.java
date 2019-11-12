package io.xpire.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.xpire.commons.core.GuiSettings;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;
import io.xpire.testutil.TypicalItems;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonListStorage addressBookStorage = new JsonListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
    public void expiryDateTrackerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonXpireStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonXpireStorageTest} class.
         */
        ReadOnlyListView[] original = TypicalItems.getTypicalLists();
        storageManager.saveList(original);
        ReadOnlyListView retrieved = storageManager.readList().getKey().get();
        Xpire retrievedXpire = (Xpire) retrieved;
        assertEquals(retrieved.getItemList(), new Xpire(retrieved).getItemList());
    }

    @Test
    public void getExpiryDateTrackerFilePath() {
        assertNotNull(storageManager.getListFilePath());
    }

}
