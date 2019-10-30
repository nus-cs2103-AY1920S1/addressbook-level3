package seedu.system.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.system.testutil.TypicalPersons.getTypicalPersonData;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.system.commons.core.GuiSettings;
import seedu.system.model.Data;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.UserPrefs;
import seedu.system.model.person.Person;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonSystemStorage addressBookStorage =
            new JsonSystemStorage(
                getTempFilePath("tempPersons"),
                getTempFilePath("tempCompetitions"),
                getTempFilePath("tempParticipations")
            );
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSystemStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonSystemStorageTest} class.
         */
        Data<Person> original = getTypicalPersonData();
        storageManager.savePersonData(original);
        ReadOnlyData<Person> retrieved = storageManager.readPersonData().get();
        assertEquals(original, new Data(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getPersonDataFilePath());
    }

}
