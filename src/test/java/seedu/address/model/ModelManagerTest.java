package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.ALL_MEAT;
import static seedu.address.testutil.TypicalDiaries.BOB_DIARY;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.diary.DiaryNameContainsKeywordsPredicate;
import seedu.address.testutil.DiaryRecordBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DiaryRecords(), new DiaryRecords(modelManager.getDiaryRecords()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDiaryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDiaryFilePath(Paths.get("new/address/book/file/path"));
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
    public void setDiaryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDiaryFilePath(null));
    }

    @Test
    public void setDiaryFilePath_validPath_setsDiaryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDiaryFilePath(path);
        assertEquals(path, modelManager.getDiaryFilePath());
    }

    @Test
    public void hasDiary_nullDiary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasDiary(null));
    }

    @Test
    public void hasDiary_diaryNotInDukeCooks_returnsFalse() {
        assertFalse(modelManager.hasDiary(ALL_MEAT));
    }

    @Test
    public void hasDiary_diaryInDukeCooks_returnsTrue() {
        modelManager.addDiary(ALL_MEAT);
        assertTrue(modelManager.hasDiary(ALL_MEAT));
    }

    @Test
    public void getFilteredDiaryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDiaryList().remove(0));
    }

    @Test
    public void equals() {
        DiaryRecords diaryRecords = new DiaryRecordBuilder().withDiary(BOB_DIARY).build();
        DiaryRecords differentDiaryRecords = new DiaryRecords();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(diaryRecords, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(diaryRecords, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different diaryRecords -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDiaryRecords, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALL_MEAT.getDiaryName().fullName.split("\\s+");
        modelManager.updateFilteredDiaryList(new DiaryNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(diaryRecords, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDiaryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(diaryRecords, differentUserPrefs)));
    }
}
