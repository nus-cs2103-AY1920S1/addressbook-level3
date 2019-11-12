package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonStudyBuddyProStorage studyBuddyProStorage = new JsonStudyBuddyProStorage(getTempFilePath("fc"),
                getTempFilePath("notes"), getTempFilePath("cs"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(studyBuddyProStorage, userPrefsStorage);
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

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonStudyBuddyProStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonStudyBuddyProStorageTest} class.
    */
    @Test
    public void studyBuddyProReadSave() throws Exception {
        StudyBuddyPro original = getTypicalStudyBuddyPro();
        storageManager.saveStudyBuddyPro(original);
        ReadOnlyStudyBuddyProFlashcards retrievedFlashcards = storageManager.readStudyBuddyProFlashcards().get();
        ReadOnlyStudyBuddyProNotes retrievedNotes = storageManager.readStudyBuddyProNotes().get();
        ReadOnlyStudyBuddyProCheatSheets retrievedCheatSheets = storageManager.readStudyBuddyProCheatSheets().get();

        assertEquals(original,
                new StudyBuddyPro(retrievedFlashcards, retrievedNotes, retrievedCheatSheets));
    }

    @Test
    public void getFlashcardFilePath() {
        assertNotNull(storageManager.getFlashcardFilePath());
    }

    @Test
    public void getNoteFilePath() {
        assertNotNull(storageManager.getNoteFilePath());
    }

    @Test
    public void getCheatSheetFilePath() {
        assertNotNull(storageManager.getCheatSheetFilePath());
    }

}
