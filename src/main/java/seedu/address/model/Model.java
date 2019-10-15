package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.diary.Diary;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Diary> PREDICATE_SHOW_ALL_DIARIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' DiaryRecords file path.
     */
    Path getDiaryFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDiaryFilePath(Path diaryFilePath);

    /**
     * Replaces Duke Cooks data with the data in {@code diaryRecords}.
     */
    void setDiaryRecords(ReadOnlyDiary diaryRecords);

    /** Returns DiaryRecords */
    ReadOnlyDiary getDiaryRecords();

    /**
     * Returns true if a diary with the same identity as {@code diary} exists in Duke Cooks.
     */
    boolean hasDiary(Diary diary);

    /**
     * Deletes the given diary.
     * The diary must exist in Duke Cooks.
     */
    void deleteDiary(Diary target);

    /**
     * Adds the given diary.
     * {@code diary} must not already exist in Duke Cooks.
     */
    void addDiary(Diary diary);

    /**
     * Replaces the given diary {@code target} with {@code editedDiary}.
     * {@code target} must exist in Duke Cooks.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the Duke Cooks.
     */
    void setDiary(Diary target, Diary editedDiary);

    /** Returns an unmodifiable view of the filtered diary list */
    ObservableList<Diary> getFilteredDiaryList();

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiaryList(Predicate<Diary> predicate);
}
