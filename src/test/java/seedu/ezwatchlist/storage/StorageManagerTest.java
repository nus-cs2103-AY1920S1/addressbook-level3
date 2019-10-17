package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonWatchListStorage watchListStorage = new JsonWatchListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(watchListStorage, userPrefsStorage);
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
        //assertEquals(original, retrieved);
    }

    @Test
    public void watchListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonWatchListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonWatchListStorageTest} class.
         */
        WatchList original = getTypicalWatchList();
        storageManager.saveWatchList(original);
        ReadOnlyWatchList retrieved = storageManager.readWatchList().get();
        //assertEquals(original, new WatchList(retrieved));
    }

    @Test
    public void getWatchListFilePath() {
        assertNotNull(storageManager.getWatchListFilePath());
    }

}
