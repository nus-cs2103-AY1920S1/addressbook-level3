package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.storage.accommodation.JsonAccommodationStorage;
import seedu.address.storage.activity.JsonActivityStorage;
import seedu.address.storage.contact.JsonContactStorage;
import seedu.address.storage.day.JsonItineraryStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAccommodationStorage accommodationStorage = new JsonAccommodationStorage(getTempFilePath("acc"));
        JsonActivityStorage activityStorage = new JsonActivityStorage(getTempFilePath("act"));
        JsonContactStorage contactStorage = new JsonContactStorage(getTempFilePath("con"));
        JsonItineraryStorage itineraryStorage = new JsonItineraryStorage(getTempFilePath("iti"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(accommodationStorage, activityStorage, contactStorage, itineraryStorage,
                userPrefsStorage);
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
        UserPrefs retrieved = storageManager.readUserPrefs().isPresent()
                ? storageManager.readUserPrefs().get()
                : new UserPrefs();
        assertEquals(original, retrieved);
    }

    /*
    @Test
    public void contactReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPlannerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPlannerStorageTest} class.
         *
        ContactManager original = TypicalContacts.getTypicalContactManager();
        storageManager.saveContact(original);
        ReadOnlyContact retrieved = storageManager.readContact().isPresent()
                ? storageManager.readContact().get()
                : new ContactManager();
        assertEquals(original, retrieved);
    }
    */

    @Test
    public void getAccommodationFilePath() {
        assertNotNull(storageManager.getAccommodationFilePath());
    }

}
