package seedu.weme.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.Meme;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Meme> PREDICATE_SHOW_ALL_MEMES = unused -> true;

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
     * Returns the user prefs' meme book file path.
     */
    Path getMemeBookFilePath();

    /**
     * Sets the user prefs' meme book file path.
     */
    void setMemeBookFilePath(Path memeBookFilePath);

    /**
     * Replaces meme book data with the data in {@code memeBook}.
     */
    void setMemeBook(ReadOnlyMemeBook memeBook);

    /** Returns the MemeBook */
    ReadOnlyMemeBook getMemeBook();

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in the meme book.
     */
    boolean hasMeme(Meme meme);

    /**
     * Deletes the given meme.
     * The meme must exist in the meme book.
     */
    void deleteMeme(Meme target);

    /**
     * Adds the given meme.
     * {@code meme} must not already exist in the meme book.
     */
    void addMeme(Meme meme);

    /**
     * Replaces the given meme {@code target} with {@code editedMeme}.
     * {@code target} must exist in the meme book.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in the meme book.
     */
    void setMeme(Meme target, Meme editedMeme);

    /** Returns an unmodifiable view of the filtered meme list */
    ObservableList<Meme> getFilteredMemeList();

    /**
     * Updates the filter of the filtered meme list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemeList(Predicate<Meme> predicate);

    /**
     * Returns the context of the model.
     */
    ModelContext getContext();

    /**
     * Sets the context of the model.
     * @param context Context to set.
     */
    void setContext(ModelContext context);
}
