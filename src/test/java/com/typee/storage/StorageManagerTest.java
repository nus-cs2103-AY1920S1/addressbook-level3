package com.typee.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.typee.commons.core.GuiSettings;
import com.typee.model.UserPrefs;

//import com.typee.model.EngagementList;
//import com.typee.model.ReadOnlyEngagementList;
//import com.typee.testutil.TypicalPersons;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonEngagementListStorage addressBookStorage = new JsonEngagementListStorage(getTempFilePath("ab"));
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

    //    @Test
    ////    public void addressBookReadSave() throws Exception {
    ////        /*
    ////         * Note: This is an integration test that verifies the StorageManager is properly wired to the
    ////         * {@link JsonEngagementListStorage} class.
    ////         * More extensive testing of UserPref saving/reading done in {@link JsonAddressBookStorageTest} class.
    ////         */
    ////        EngagementList original = TypicalPersons.getTypicalAddressBook();
    ////        storageManager.saveAddressBook(original);
    ////        ReadOnlyEngagementList retrieved = storageManager.readAddressBook().get();
    ////        assertEquals(original, new EngagementList(retrieved));
    ////    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getEngagementListFilePath());
    }

}
