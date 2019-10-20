package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;
import static seedu.savenus.testutil.TypicalPurchaseHistory.getTypicalPurchaseHistory;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.model.Menu;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyPurchaseHistory;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.sorter.CustomSorter;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonMenuStorage addressBookStorage = new JsonMenuStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonRecsStorage userRecsStorage = new JsonRecsStorage(getTempFilePath("recs"));
        JsonPurchaseHistoryStorage purchaseHistoryStorage = new JsonPurchaseHistoryStorage(
                getTempFilePath("purchases"));
        JsonCustomSortStorage customSortStorage = new JsonCustomSortStorage(getTempFilePath("sort"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage, userRecsStorage,
                purchaseHistoryStorage, customSortStorage);
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
    public void menuReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMenuStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMenuStorageTest} class.
         */
        Menu original = getTypicalMenu();
        storageManager.saveMenu(original);
        ReadOnlyMenu retrieved = storageManager.readMenu().get();
        assertEquals(original, new Menu(retrieved));
    }

    @Test
    public void purchaseHistoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPurchaseHistoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPurchaseHistoryStorage} class.
         */
        PurchaseHistory original = getTypicalPurchaseHistory();
        storageManager.savePurchaseHistory(original);
        ReadOnlyPurchaseHistory retrieved = storageManager.readPurchaseHistory().get();
        assertEquals(original, new PurchaseHistory(retrieved));
    }

    @Test
    public void newUserRecommendationsSave() throws Exception {
        UserRecommendations recommendations = new UserRecommendations();
        storageManager.saveRecs(recommendations);
        UserRecommendations retrieved = storageManager.readRecs().get();
        assertEquals(recommendations, retrieved);
    }


    @Test
    public void newCustomSortSave() throws Exception {
        CustomSorter sorter = new CustomSorter();
        storageManager.saveFields(sorter);
        CustomSorter theSorter = storageManager.readFields().get();
        assertEquals(theSorter, sorter);
    }

    @Test
    public void getMenuFilePath() {
        assertNotNull(storageManager.getMenuFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getRecsFilePath() {
        assertNotNull(storageManager.getRecsFilePath());
    }

    @Test
    public void getSortFilePath() {
        assertNotNull(storageManager.getSortFilePath());
    }
}
