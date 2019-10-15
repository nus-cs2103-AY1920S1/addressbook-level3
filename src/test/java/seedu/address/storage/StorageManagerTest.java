package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalProfiles.getTypicalProfiles;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.profile.ReadOnlyUserProfile;
import seedu.address.profile.UserPrefs;
import seedu.address.profile.UserProfile;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserProfileStorage dukeCooksStorage = new JsonUserProfileStorage(getTempFilePath("ab"));
        JsonHealthRecordsStorage healthRecordsStorage = new JsonHealthRecordsStorage(getTempFilePath("hr"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(dukeCooksStorage, healthRecordsStorage, userPrefsStorage);
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
    public void dukeCooksReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserProfileStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserProfileStorageTest} class.
         */
        UserProfile original = getTypicalProfiles();
        storageManager.saveUserProfile(original);
        ReadOnlyUserProfile retrieved = storageManager.readUserProfile().get();
        assertEquals(original, new UserProfile(retrieved));
    }

    @Test
    public void getDukeCooksFilePath() {
        assertNotNull(storageManager.getUserProfileFilePath());
    }

}
