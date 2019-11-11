package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonWatchListStorage watchListStorage = new JsonWatchListStorage(getTempFilePath("ab"));
        JsonDatabaseStorage databaseStorage = new JsonDatabaseStorage(getTempFilePath("database"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(watchListStorage, databaseStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }
    /*
    @Test
    public void prefsReadSave() throws Exception {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        //assertEquals(original, retrieved);
    }
*/
    /*
    @Test
    public void watchListReadSave() throws Exception {

        WatchList original = getTypicalWatchList();
        storageManager.saveWatchList(original);
        ReadOnlyWatchList retrieved = storageManager.readWatchList().get();
        //assertEquals(original, new WatchList(retrieved));
    }
*/
    @Test
    public void getWatchListFilePath() {
        assertNotNull(storageManager.getWatchListFilePath());
    }

}
