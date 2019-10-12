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
    Predicate<Diary> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' DukeCooks file path.
     */
    Path getDukeCooksFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDukeCooksFilePath(Path dukeCooksFilePath);

    /**
     * Replaces Duke Cooks data with the data in {@code dukeCooks}.
     */
    void setDukeCooks(ReadOnlyDukeCooks dukeCooks);

    /** Returns DukeCooks */
    ReadOnlyDukeCooks getDukeCooks();

    /**
     * Returns true if a diary with the same identity as {@code diary} exists in Duke Cooks.
     */
    boolean hasPerson(Diary diary);

    /**
     * Deletes the given diary.
     * The diary must exist in Duke Cooks.
     */
    void deletePerson(Diary target);

    /**
     * Adds the given diary.
     * {@code diary} must not already exist in Duke Cooks.
     */
    void addPerson(Diary diary);

    /**
     * Replaces the given diary {@code target} with {@code editedDiary}.
     * {@code target} must exist in Duke Cooks.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the Duke Cooks.
     */
    void setPerson(Diary target, Diary editedDiary);

    /** Returns an unmodifiable view of the filtered diary list */
    ObservableList<Diary> getFilteredPersonList();

    /**
     * Updates the filter of the filtered diary list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Diary> predicate);
}
