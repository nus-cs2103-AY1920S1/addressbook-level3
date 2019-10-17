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

public class NoteManagerTest {

    private NoteManager noteManager = new NoteManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), noteManager.getUserPrefs());
        assertEquals(new GuiSettings(), noteManager.getGuiSettings());
        assertEquals(new NoteBook(), new NoteBook(noteManager.getNoteBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteManager.setUserPrefs(null));
    }

    // TODO renable after userprefs is edited for Notes
    //@Test
    //public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
    //    UserPrefs userPrefs = new UserPrefs();
    //    userPrefs.setNoteBookFilePath(Paths.get("address/book/file/path"));
    //    userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
    //    noteManager.setUserPrefs(userPrefs);
    //    assertEquals(userPrefs, noteManager.getUserPrefs());

    //    // Modifying userPrefs should not modify noteManager's userPrefs
    //    UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
    //    userPrefs.setNoteBookFilePath(Paths.get("new/address/book/file/path"));
    //    assertEquals(oldUserPrefs, noteManager.getUserPrefs());
    //}

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        noteManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, noteManager.getGuiSettings());
    }

    @Test
    public void setNoteBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteManager.setNoteBookFilePath(null));
    }

    //TODO after implementing user prefs
    @Test
    public void setNoteBookFilePath_validPath_setsNoteBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        noteManager.setNoteBookFilePath(path);
        //assertEquals(path, noteManager.getNoteBookFilePath());
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> noteManager.hasNote(null));
    }

    @Test
    public void hasNote_personNotInNoteBook_returnsFalse() {
        assertFalse(noteManager.hasNote(INCIDENT));
    }

    @Test
    public void hasNote_personInNoteBook_returnsTrue() {
        noteManager.addNote(INCIDENT);
        assertTrue(noteManager.hasNote(INCIDENT));
    }

    @Test
    public void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> noteManager.getFilteredNoteList().remove(0));
    }

    @Test
    public void equals() {
        NoteBook addressBook = new NoteBookBuilder().withNote(INCIDENT).withNote(ULTRON).build();
        NoteBook differentNoteBook = new NoteBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        noteManager = new NoteManager(addressBook, userPrefs);
        NoteManager noteManagerCopy = new NoteManager(addressBook, userPrefs);
        assertTrue(noteManager.equals(noteManagerCopy));

        // same object -> returns true
        assertTrue(noteManager.equals(noteManager));

        // null -> returns false
        assertFalse(noteManager.equals(null));

        // different types -> returns false
        assertFalse(noteManager.equals(5));

        // different addressBook -> returns false
        assertFalse(noteManager.equals(new NoteManager(differentNoteBook, userPrefs)));

        // different filteredList -> returns false
        // \\s+ splits on single or many whitespace
        noteManager = new NoteManager(addressBook, userPrefs);
        // using these strings as they are unique to INCIDENT but not found in ULTRON
        String[] keywords = {"Manhattan", "York", "Loki", "Chitauri"};
        noteManager.updateFilteredNoteList(new ContentContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(noteManager.equals(new NoteManager(addressBook, userPrefs)));

        // resets noteManager to initial state for upcoming tests
        noteManager.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);

        // different userPrefs -> returns false
        // TODO userPrefs feature not ready
        //UserPrefs differentUserPrefs = new UserPrefs();
        //differentUserPrefs.setNoteBookFilePath(Paths.get("differentFilePath"));
        //assertFalse(noteManager.equals(new NoteManager(addressBook, differentUserPrefs)));
    }
}
