package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.UniqueFlashcardList;

/**
 * Wraps all data at the flashcard list level
 * Duplicates are not allowed
 */
public class FlashcardList implements ReadOnlyFlashcardList {

    private final UniqueFlashcardList flashcards = new UniqueFlashcardList();

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
    }

    /**
     * Resets the existing data of this {@code FlashcardList} with {@code newData}
     */
    public void resetData(ReadOnlyFlashcardList newData) {
        requireNonNull(newData);
        setFlashcards(newData.getFlashcardList());
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
    }

    /**
     * Replaces the given flashcard {@code flashcard} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the flashcard list.
     * The flashcard word of {@code editedFlashcard} must not be the same as another existing flashcard in the flashcard list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireNonNull(editedFlashcard);
        flashcards.setFlashcard(target, editedFlashcard);
    }

    /**
     * Removes {@code key} from the flashcard list
     * {@code key} must exist in the flashcard list
     */
    public void removeFlashcard(Flashcard key) {
        flashcards.remove(key);
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
