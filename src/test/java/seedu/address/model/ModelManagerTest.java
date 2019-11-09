package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CHEATSHEETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCheatSheets.CS1;
import static seedu.address.testutil.TypicalCheatSheets.CS2;
import static seedu.address.testutil.TypicalFlashcards.CS_ONE;
import static seedu.address.testutil.TypicalFlashcards.MATH_ONE;
import static seedu.address.testutil.TypicalNotes.PIPELINE;
import static seedu.address.testutil.TypicalNotes.POTATO;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.testutil.StudyBuddyProBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new StudyBuddyPro(), new StudyBuddyPro(modelManager.getStudyBuddyPro()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setStudyBuddyProFilePath(Paths.get("study/buddy/pro/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setStudyBuddyProFilePath(Paths.get("new/study/buddy/pro/file/path"));
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
    public void setStudyBuddyProFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setStudyBuddyProFilePath(null));
    }

    @Test
    public void setStudyBuddyProFilePath_validPath_setsStudyBuddyProFilePath() {
        Path path = Paths.get("study/buddy/pro/file/path");
        modelManager.setStudyBuddyProFilePath(path);
        assertEquals(path, modelManager.getStudyBuddyProFilePath());
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFlashcard(null));
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasNote(null));
    }

    @Test
    public void hasCheatSheet_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCheatSheet(null));
    }

    @Test
    public void hasFlashcard_flashcardNotInStudyBuddyPro_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(CS_ONE));
    }

    @Test
    public void hasNote_noteNotInStudyBuddyPro_returnsFalse() {
        assertFalse(modelManager.hasNote(PIPELINE));
    }

    @Test
    public void hasCheatSheet_cheatsheetNotInStudyBuddyPro_returnsFalse() {
        assertFalse(modelManager.hasCheatSheet(CS1));
    }

    @Test
    public void hasFlashcard_flashcardInStudyBuddyPro_returnsTrue() {
        modelManager.addFlashcard(CS_ONE);
        assertTrue(modelManager.hasFlashcard(CS_ONE));
    }

    @Test
    public void hasNote_noteInStudyBuddyPro_returnsTrue() {
        modelManager.addNote(PIPELINE);
        assertTrue(modelManager.hasNote(PIPELINE));
    }

    @Test
    public void hasCheatSheet_cheatsheetInStudyBuddyPro_returnsTrue() {
        modelManager.addCheatSheet(CS1);
        assertTrue(modelManager.hasCheatSheet(CS1));
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFlashcardList().remove(0));
    }

    @Test
    public void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredNoteList().remove(0));
    }

    @Test
    public void getFilteredCheatSheetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCheatSheetList().remove(0));
    }

    @Test
    public void equals() {
        StudyBuddyPro studyBuddyPro =
                new StudyBuddyProBuilder().withFlashcard(CS_ONE).withFlashcard(MATH_ONE).withNote(PIPELINE)
                        .withNote(POTATO).withCheatSheet(CS1).withCheatSheet(CS2).build();
        StudyBuddyPro differentStudyBuddyPro = new StudyBuddyPro();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(studyBuddyPro, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(studyBuddyPro, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different studyBuddyPro -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentStudyBuddyPro, userPrefs)));

        /* Unused, kept for reference
        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(studyBuddyPro, userPrefs)));
        */

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        modelManager.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        modelManager.updateFilteredCheatSheetList(PREDICATE_SHOW_ALL_CHEATSHEETS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setStudyBuddyProFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(studyBuddyPro, differentUserPrefs)));
    }
}
