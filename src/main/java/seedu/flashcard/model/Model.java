package seedu.flashcard.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * The interface of models containing the whole flashcard list.
 * This is one of the communication windows of the model package to the outside.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /**
     * {@code Predicate} that evaluates true for flashcards contains the given tag.
     */
    Predicate<Flashcard> getHasTagPredicate(Set<Tag> tag);

    /**
     * Get the set of all tags in the system.
     */
    Set<Tag> getAllSystemTags();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns user prefs datat with the data in {@code userPrefs}
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Get the user prefs' GUI settings
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get the user prefs' flashcard list file path
     */
    Path getFlashcardListFilePath();

    /**
     * Sets the user prefs' flashcard list file path
     * @param flashcardListFilePath
     */
    void setFlashcardListFilePath(Path flashcardListFilePath);

    /**
     * Replaces flashcard list data with the data in {@code flashcardList}
     */
    void setFlashcardList(ReadOnlyFlashcardList flashcardList);

    /**
     * Get the flashcard list
     */
    ReadOnlyFlashcardList getFlashcardList();

    /**
     * Check if a flashcard exists in the flashcard list
     */
    boolean hasFlashcard(Flashcard flashcard);

    /**
     * Deletes the given flashcard. This flashcard mush exist in the flashcard list
     */
    void deleteFlashcard(Flashcard flashcard);

    /**
     * Adds the given flashcard.
     */
    void addFlashcard(Flashcard flashcard);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashcard}
     * {@code target} must exist in the flashcard list
     */
    void setFlashcard(Flashcard target, Flashcard editedFlashcard);

    /**
     * Returns an unmodifiable view of the filtered flashcard list
     */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Returns true if the given tag ever appeared in the system.
     */
    boolean systemHasTag(Tag tag);

    /**
     * Removes the given tag from all flashcards in the system.
     */
    void systemRemoveTag(Tag tag);

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}
     * @throws NullPointerException is {@code predicate} is null
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);
}
