package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalNotes.INCIDENT;
import static tagline.testutil.TypicalNotes.ULTRON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.commons.core.GuiSettings;
import tagline.model.UserPrefs;
import tagline.testutil.NoteBookBuilder;

public class NoteModelManagerTest {

    private NoteModelManager noteModelManager = new NoteModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), noteModelManager.getUserPrefs());
        assertEquals(new GuiSettings(), noteModelManager.getGuiSettings());
        assertEquals(new NoteBook(), new NoteBook(noteModelManager.getNoteBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteModelManager.setUserPrefs(null));
    }

    // TODO renable after userprefs is edited for Notes
    //@Test
    //public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
    //    UserPrefs userPrefs = new UserPrefs();
    //    userPrefs.setNoteBookFilePath(Paths.get("address/book/file/path"));
    //    userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
    //    noteModelManager.setUserPrefs(userPrefs);
    //    assertEquals(userPrefs, noteModelManager.getUserPrefs());

    //    // Modifying userPrefs should not modify noteModelManager's userPrefs
    //    UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
    //    userPrefs.setNoteBookFilePath(Paths.get("new/address/book/file/path"));
    //    assertEquals(oldUserPrefs, noteModelManager.getUserPrefs());
    //}

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteModelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        noteModelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, noteModelManager.getGuiSettings());
    }

    @Test
    public void setNoteBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteModelManager.setNoteBookFilePath(null));
    }

    //TODO after implementing user prefs
    @Test
    public void setNoteBookFilePath_validPath_setsNoteBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        noteModelManager.setNoteBookFilePath(path);
        //assertEquals(path, noteModelManager.getNoteBookFilePath());
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteModelManager.hasNote(null));
    }

    @Test
    public void hasNote_personNotInNoteBook_returnsFalse() {
        assertFalse(noteModelManager.hasNote(INCIDENT));
    }

    @Test
    public void hasNote_personInNoteBook_returnsTrue() {
        noteModelManager.addNote(INCIDENT);
        assertTrue(noteModelManager.hasNote(INCIDENT));
    }

    @Test
    public void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> noteModelManager.getFilteredNoteList().remove(0));
    }

    @Test
    public void equals() {
        NoteBook addressBook = new NoteBookBuilder().withNote(INCIDENT).withNote(ULTRON).build();
        NoteBook differentNoteBook = new NoteBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        noteModelManager = new NoteModelManager(addressBook, userPrefs);
        NoteModelManager noteModelManagerCopy = new NoteModelManager(addressBook, userPrefs);
        assertTrue(noteModelManager.equals(noteModelManagerCopy));

        // same object -> returns true
        assertTrue(noteModelManager.equals(noteModelManager));

        // null -> returns false
        assertFalse(noteModelManager.equals(null));

        // different types -> returns false
        assertFalse(noteModelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(noteModelManager.equals(new NoteModelManager(differentNoteBook, userPrefs)));

        // different filteredList -> returns false
        // \\s+ splits on single or many whitespace
        noteModelManager = new NoteModelManager(addressBook, userPrefs);
        // using these strings as they are unique to INCIDENT but not found in ULTRON
        String[] keywords = {"Manhattan", "York", "Loki", "Chitauri"};
        noteModelManager.updateFilteredNoteList(new ContentContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(noteModelManager.equals(new NoteModelManager(addressBook, userPrefs)));

        // resets noteModelManager to initial state for upcoming tests
        noteModelManager.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);

        // different userPrefs -> returns false
        // TODO userPrefs feature not ready
        //UserPrefs differentUserPrefs = new UserPrefs();
        //differentUserPrefs.setNoteBookFilePath(Paths.get("differentFilePath"));
        //assertFalse(noteModelManager.equals(new NoteModelManager(addressBook, differentUserPrefs)));
    }
}
