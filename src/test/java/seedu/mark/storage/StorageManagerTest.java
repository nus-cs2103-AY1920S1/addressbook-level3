package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.mark.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonBookmarkManagerStorage addressBookStorage = new JsonBookmarkManagerStorage(getTempFilePath("ab"));
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
         * {@link JsonBookmarkManagerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonBookmarkManagerStorageTest} class.
         */
        BookmarkManager original = getTypicalAddressBook();
        storageManager.saveBookmarkManager(original);
        ReadOnlyBookmarkManager retrieved = storageManager.readBookmarkManager().get();
        assertEquals(original, new BookmarkManager(retrieved));
    }

    @Test
    public void getBookmarkManagerFilePath() {
        assertNotNull(storageManager.getBookmarkManagerFilePath());
    }

}
