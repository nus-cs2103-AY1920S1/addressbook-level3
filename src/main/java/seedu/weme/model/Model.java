package seedu.weme.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.Meme;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.StatsEngine;

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
     * Returns the user prefs' data file path.
     */
    Path getDataFilePath();

    /**
     * Sets the user prefs' data file path.
     */
    void setDataFilePath(Path dataFilePath);

    /**
     * Returns the user prefs' meme image path.
     */
    Path getMemeImagePath();

    /**
     * Sets the user prefs' meme image path.
     */
    void setMemeImagePath(Path memeImagePath);

    /**
     * Returns the user prefs' template image path.
     */
    Path getTemplateImagePath();

    /**
     * Sets the user prefs' template image path.
     */
    void setTemplateImagePath(Path templateImagePath);

    /**
     * Replaces meme book data with the data in {@code memeBook}.
     */
    void setMemeBook(ReadOnlyMemeBook memeBook);

    /**
     * Returns the MemeBook
     */
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
    SimpleObjectProperty<ModelContext> getContext();

    /**
     * Returns true if model has a previous state to restore.
     */
    boolean canUndoMemeBook();

    /**
     * Returns true if model has a undone state to restore.
     */
    boolean canRedoMemeBook();

    /**
     * Restores the model's meme book to its previous state.
     */
    void undoMemeBook();

    /**
     * Restores the mode's meme book to its previously undone state.
     */
    void redoMemeBook();

    /**
     * Saves the current meme book state for undo/redo.
     */
    void commitMemeBook();

    /**
     * Sets the context of the model.
     * @param context Context to set.
     */
    void setContext(ModelContext context);

    /**
     * Returns statistics data.
     */
    StatsEngine getStatsEngine();

    /**
     * Returns the like data.
     */
    LikeData getLikeData();

    ObservableMap<String, Integer> getObservableLikeData();

    /**
     * Increments likes of a meme by the Meme object.
     */
    void incrementLikesByMeme(Meme meme);

    /**
     * Deletes like data by meme.
     */
    void deleteLikesByMeme(Meme memeToDelete);

}
