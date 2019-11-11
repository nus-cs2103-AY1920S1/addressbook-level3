package seedu.flashcard.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFlashcardListStorage flashcardListStorage = new JsonFlashcardListStorage(getTempFilePath("fl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(flashcardListStorage, userPrefsStorage);
    }

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void flashcardListReadSave() throws Exception {
        FlashcardList original = getTypicalFlashcardList();
        storageManager.saveFlashcardList(original);
        ReadOnlyFlashcardList retrieved = storageManager.readFlashcardList().get();
        assertEquals(original, new FlashcardList(retrieved));
    }

    @Test
    public void getFlashcardListFilePath() {
        assertNotNull(storageManager.getFlashcardListFilePath());
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }
}
