package seedu.address.storage.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ReadOnlyQuizBook;
import seedu.address.model.quiz.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageQuizManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonQuizAddressBookStorage addressBookStorage = new JsonQuizAddressBookStorage(getTempFilePath("ab"));
        JsonQuizUserPrefsStorage userPrefsStorage = new JsonQuizUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageQuizManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageQuizManager is properly wired to the
         * {@link JsonQuizUserPrefsStorage} class.
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
         * Note: This is an integration test that verifies the StorageQuizManager is properly wired to the
         * {@link JsonQuizAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressQuizBook original = new AddressQuizBook();
        storageManager.saveAddressBook(original);
        ReadOnlyQuizBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressQuizBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

}
