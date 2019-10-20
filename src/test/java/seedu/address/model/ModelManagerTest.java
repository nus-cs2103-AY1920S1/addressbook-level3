package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.ALICE;
import static seedu.address.testutil.TypicalAppData.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.testutil.AppDataBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AppData(), new AppData(modelManager.getAppData()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAppDataFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAppDataFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAppDataFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAppDataFilePath(null));
    }

    @Test
    public void setAppDataFilePath_validPath_setsAppDataFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAppDataFilePath(path);
        assertEquals(path, modelManager.getAppDataFilePath());
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasNote(null));
    }

    @Test
    public void hasNote_noteNotInAppData_returnsFalse() {
        assertFalse(modelManager.hasNote(ALICE));
    }

    @Test
    public void hasNote_noteInAppData_returnsTrue() {
        modelManager.addNote(ALICE);
        assertTrue(modelManager.hasNote(ALICE));
    }

    @Test
    public void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredNoteList().remove(0));
    }

    @Test
    public void equals() {
        AppData appData = new AppDataBuilder().withNote(ALICE).withNote(BENSON).build();
        AppData differentAppData = new AppData();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(appData, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(appData, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different appData -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAppData, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getTitle().title.split("\\s+");
        modelManager.updateFilteredNoteList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(appData, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAppDataFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(appData, differentUserPrefs)));
    }
}
