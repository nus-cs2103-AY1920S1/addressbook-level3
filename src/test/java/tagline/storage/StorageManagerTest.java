package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;
import static tagline.testutil.TypicalNotes.getTypicalNoteBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tagline.commons.core.GuiSettings;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.GroupBook;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.NoteBook;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.storage.contact.JsonAddressBookStorage;
import tagline.storage.group.JsonGroupBookStorage;
import tagline.storage.note.JsonNoteBookStorage;
import tagline.storage.tag.JsonTagBookStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonNoteBookStorage noteBookStorage = new JsonNoteBookStorage(getTempFilePath("nb"));
        JsonGroupBookStorage groupBookStorage = new JsonGroupBookStorage(getTempFilePath("gb"));
        JsonTagBookStorage tagBookStorage = new JsonTagBookStorage(getTempFilePath("tb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, noteBookStorage,
            groupBookStorage, tagBookStorage, userPrefsStorage);
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
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void noteBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonNoteBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonNoteBookStorageTest} class.
         */
        NoteBook original = getTypicalNoteBook();
        storageManager.saveNoteBook(original);
        ReadOnlyNoteBook retrieved = storageManager.readNoteBook().get();
        assertEquals(original, new NoteBook(retrieved));
    }

    @Test
    public void getNoteBookFilePath() {
        assertNotNull(storageManager.getNoteBookFilePath());
    }


    @Test
    public void groupBookReadSave() throws Exception {
        /*
         * Group: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonGroupBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonGroupBookStorageTest} class.
         */
        GroupBook original = getTypicalGroupBook();
        storageManager.saveGroupBook(original);
        ReadOnlyGroupBook retrieved = storageManager.readGroupBook().get();
        assertEquals(original, new GroupBook(retrieved));
    }

    @Test
    public void getGroupBookFilePath() {
        assertNotNull(storageManager.getGroupBookFilePath());
    }

}
