package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import thrift.commons.core.GuiSettings;
import thrift.model.ReadOnlyThrift;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.testutil.TypicalTransactions;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;
    private JsonThriftStorage thriftStorage;
    private JsonUserPrefsStorage userPrefsStorage;
    private JsonCurrencyMappingsStorage currencyMappingsStorage;

    @BeforeEach
    public void setUp() {
        thriftStorage = new JsonThriftStorage(getTempFilePath("ab"));
        userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        currencyMappingsStorage = new JsonCurrencyMappingsStorage((getTempFilePath("currMap")));
        storageManager = new StorageManager(thriftStorage, userPrefsStorage, currencyMappingsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void getUserPrefsFilePath_correctResult() {
        Path expectedPath = Paths.get(getTempFilePath("prefs").toString());
        assertEquals(userPrefsStorage.getUserPrefsFilePath(), expectedPath);
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
    public void thriftReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonThriftStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonThriftStorageTest} class.
         */
        Thrift original = TypicalTransactions.getTypicalThrift();
        storageManager.saveThrift(original);
        ReadOnlyThrift retrieved = storageManager.readThrift().get();
        assertEquals(original, new Thrift(retrieved));
    }

    @Test
    public void getThriftFilePath() {
        assertNotNull(storageManager.getThriftFilePath());
    }

    @Test
    public void getCurrencyMappingsFilePath_correctResult() {
        Path expectedPath = Paths.get(getTempFilePath("currMap").toString());
        assertEquals(currencyMappingsStorage.getCurrencyMappingsFilePath(), expectedPath);
    }

    @Test
    public void currencyMappingsReadWrite_sameObject() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCurrencyMappingsStorage} class.
         */
        Map<String, Double> original = Map.of("USD", 0.7262790545, "CNY", 5.1733061171);
        storageManager.saveCurrencyMappings(original);
        Map<String, Double> retrieved = storageManager.readCurrencyMappings().get();
        assertEquals(original, retrieved);
    }

}
