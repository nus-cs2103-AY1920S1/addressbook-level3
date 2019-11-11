package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.flashcard.commons.util.InvalidationListenerManager;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.UniqueFlashcardList;
import seedu.flashcard.model.tag.Tag;

/**
 * Wraps all data at the flashcard list level
 * Duplicates are not allowed
 */
public class FlashcardList implements ReadOnlyFlashcardList {

    private final UniqueFlashcardList flashcards = new UniqueFlashcardList();
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    public FlashcardList() {}

    public FlashcardList(ReadOnlyFlashcardList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the flashcards of the flashcard list with {@code flashcards}
     * {@code} flashcards should not contain duplicate flashcards
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards.setFlashcards(flashcards);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code FlashcardList} with {@code newData}
     */
    public void resetData(ReadOnlyFlashcardList newData) {
        requireNonNull(newData);
        setFlashcards(newData.getFlashcardList());
        indicateModified(); // include here?
    }

    /**
     * Returns true if a flashcard with the same word as {@code flashcard} exists in the flashcard list.
     */
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcards.contains(flashcard);

    }

    /**
     * Adds a flashcard to the flashcard list.
     */
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
        indicateModified();
    }

    /**
     * Replaces the given flashcard {@code flashcard} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the flashcard list.
     * The flashcard word of {@code editedFlashcard} must not
     * be the same as another existing flashcard in the flashcard list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);
        flashcards.setFlashcard(target, editedFlashcard);
        indicateModified();
    }

    /**
     * Removes {@code key} from the flashcard list
     * {@code key} must exist in the flashcard list
     */
    public void removeFlashcard(Flashcard key) {
        flashcards.remove(key);
        indicateModified();
    }

    /**
     * Gets all the tags evert appeared in the system.
     */
    public Set<Tag> getAllFlashcardTags() {
        return flashcards.getAllTags();
    }

    /**
     * returns true if the given tag ever appears in the flashcard list
     */
    public boolean flashcardsHasTag(Tag tag) {
        return flashcards.anyFlashcardHasTag(tag);
    }

    /**
     * Removes the given tag from all flashcards.
     */
    public void flashcardsRemoveTag(Tag tag) {
        flashcards.removeTag(tag);
        indicateModified();
    }

    /**
     * Notifies listeners that the flashcard list has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    @Override
    public ObservableList<Flashcard> getFlashcardList() {
        return flashcards.asUnimodifiableObservableList();
    }

    @Override
    public String toString() {
        return flashcards.asUnimodifiableObservableList().size() + "flashcards";
        // TODO: refine later

    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlashcardList
                    && flashcards.equals(((FlashcardList) other).flashcards));
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }


}
