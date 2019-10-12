package com.dukeacademy.model;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.ALICE;
import static com.dukeacademy.testutil.TypicalQuestions.BENSON;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.GuiSettings;
import com.dukeacademy.model.question.TitleContainsKeywordsPredicate;
import com.dukeacademy.testutil.QuestionBankBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new QuestionBank(), new QuestionBank(modelManager.getQuestionBank()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setQuestionBankFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setQuestionBankFilePath(Paths.get("new/address/book/file/path"));
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
    public void setQuestionBankFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setQuestionBankFilePath(null));
    }

    @Test
    public void setQuestionBankFilePath_validPath_setsQuestionBankFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setQuestionBankFilePath(path);
        assertEquals(path, modelManager.getQuestionBankFilePath());
    }

    @Test
    public void hasQuestion_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasQuestion(null));
    }

    @Test
    public void hasQuestion_questionNotInQuestionBank_returnsFalse() {
        assertFalse(modelManager.hasQuestion(ALICE));
    }

    @Test
    public void hasQuestion_questionInQuestionBank_returnsTrue() {
        modelManager.addQuestion(ALICE);
        assertTrue(modelManager.hasQuestion(ALICE));
    }

    @Test
    public void getFilteredQuestionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredQuestionList().remove(0));
    }

    @Test
    public void equals() {
        QuestionBank questionBank = new QuestionBankBuilder().withQuestion(ALICE).withQuestion(BENSON).build();
        QuestionBank differentQuestionBank = new QuestionBank();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(questionBank, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(questionBank, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different questionBank -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentQuestionBank, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getTitle().fullTitle.split("\\s+");
        modelManager.updateFilteredQuestionList(new TitleContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(questionBank, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredQuestionList(Model.PREDICATE_SHOW_ALL_QUESTIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setQuestionBankFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(questionBank, differentUserPrefs)));
    }
}
