package seedu.tarence.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.tarence.testutil.TypicalPersons.getTypicalStudentBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.model.ReadOnlyStudentBook;
import seedu.tarence.model.StudentBook;
import seedu.tarence.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonStudentBookStorage studentBookStorage = new JsonStudentBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(studentBookStorage, userPrefsStorage);
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
    public void studentBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonStudentBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonStudentBookStorageTest} class.
         */
        StudentBook original = getTypicalStudentBook();
        storageManager.saveStudentBook(original);
        ReadOnlyStudentBook retrieved = storageManager.readStudentBook().get();
        assertEquals(original, new StudentBook(retrieved));
    }

    @Test
    public void getStudentBookFilePath() {
        assertNotNull(storageManager.getStudentBookFilePath());
    }

}
