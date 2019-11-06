package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppData.ALGEBRA_QUESTION;
import static seedu.address.testutil.TypicalAppData.ALICE;
import static seedu.address.testutil.TypicalAppData.BENSON;
import static seedu.address.testutil.TypicalAppData.CONCEPT_QUESTION;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.model.question.QuestionContainsKeywordsPredicate;
import seedu.address.testutil.AppDataBuilder;

class ModelManagerTest {
    private ModelManager modelManager = new ModelManager();

    @Test
    void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AppData(), new AppData(modelManager.getAppData()));
    }

    @Test
    void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAppDataFilePath(Paths.get("nustudy/appdata"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAppDataFilePath(Paths.get("new/nustudy/appdata"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    void setAppDataFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAppDataFilePath(null));
    }

    @Test
    void setAppDataFilePath_validPath_setsAppDataFilePath() {
        Path path = Paths.get("nustudy/appdata");
        modelManager.setAppDataFilePath(path);
        assertEquals(path, modelManager.getAppDataFilePath());
    }

    @Test
    void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasNote(null));
    }

    @Test
    void hasNote_noteNotInAppData_returnsFalse() {
        assertFalse(modelManager.hasNote(ALICE));
    }

    @Test
    void hasNote_noteInAppData_returnsTrue() {
        modelManager.addNote(ALICE);
        assertTrue(modelManager.hasNote(ALICE));
    }

    @Test
    void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredNoteList().remove(0));
    }

    // question
    @Test
    void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasQuestion(null));
    }

    @Test
    void hasQuestion_noteNotInAppData_returnsFalse() {
        assertFalse(modelManager.hasQuestion(ALGEBRA_QUESTION));
    }

    @Test
    void hasQuestion_noteInAppData_returnsTrue() {
        modelManager.addQuestion(ALGEBRA_QUESTION);
        assertTrue(modelManager.hasQuestion(ALGEBRA_QUESTION));
    }

    @Test
    void getFilteredQuestionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredQuestionList().remove(0));
    }

    @Test
    void equals() {
        AppData appData = new AppDataBuilder().withNote(ALICE).withNote(BENSON)
                .withQuestion(ALGEBRA_QUESTION).withQuestion(CONCEPT_QUESTION).build();
        AppData differentAppData = new AppData();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(appData, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(appData, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different appData -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAppData, userPrefs));

        // different filteredList -> returns false
        String[] keywords = ALICE.getTitle().title.split("\\s+");
        modelManager.updateFilteredNoteList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(appData, userPrefs));

        // different filteredQuestionList -> returns false
        String[] questionkeywords = ALGEBRA_QUESTION.getQuestionBody().body.split("\\s+");
        modelManager.updateFilteredQuestionList(new QuestionContainsKeywordsPredicate(Arrays.asList(questionkeywords)));
        assertNotEquals(modelManager, new ModelManager(appData, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        modelManager.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAppDataFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(appData, differentUserPrefs));
    }
}
