package seedu.flashcard.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flashcard.model.flashcard.exceptions.CardNotFoundException;
import seedu.flashcard.model.flashcard.exceptions.DuplicateCardException;

import java.util.Iterator;
import java.util.List;

/**
 * A list of flashcards that enforces uniqueness between its elements and does not allow nulls.
 * A flashcard is considered unique by comparing using {@code Flashcard#isSameFlashcard(Flashcard)}. As such, adding and updating of
 * flashcards uses Flashcard#isSameFlashcard(Flashcard) for equality so as to ensure that the flashcard being added or updated is
 * unique in terms of identity in the UniqueFlashcardList. However, the removal of a flashcard uses Flashcard#equals(Object) so
 * as to ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Flashcard#isSameFlashcard(Flashcard)
 */
public class UniqueFlashcardList implements Iterable<Flashcard> {

    private final ObservableList<Flashcard> internalList = FXCollections.observableArrayList();
    private final ObservableList<Flashcard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent flashcard as the given argument
     */
    public boolean contains(Flashcard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFlashcard);
    }

    /**
     * Add a flashcard to the flashcard list
     * The flashcard should not already exist in the flashcard list
     */
    public void add(Flashcard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The flashcard word of {@code editedFlashcard} must not be the same as another existing Flashcard in the list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CardNotFoundException();
        }
        if (!target.isSameFlashcard(editedFlashcard) && contains(editedFlashcard)) {
            throw new DuplicateCardException();
        }
        internalList.set(index, editedFlashcard);
    }

    /**
     * Remove the equivalent flashcard from the list.
     * This flashcard must exist in the list.
     */
    public void remove(Flashcard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CardNotFoundException();
        }
    }

    public void setFlashcards(UniqueFlashcardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replace the contents of the list with P{@code flashcards}
     * {@code flashcards} must not contain duplicate flashcards
     * Otherwise, DuplicateCardException will be thrown
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        requireNonNull(flashcards);
        if (!flashcardsAreUnique(flashcards)) {
            throw new DuplicateCardException();
        }
        internalList.setAll(flashcards);
    }

    /**
     * Returns the backing list as an unmodifiable {@code observableList}.
     */
    public ObservableList<Flashcard> asUnimodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Flashcard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof UniqueFlashcardList)
                && internalList.equals(((UniqueFlashcardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true of {@code flashcards} contains only unique flashcards.
     */
    private boolean flashcardsAreUnique(List<Flashcard> flashcards) {
        for (int i = 0; i < flashcards.size() - 1; i++) {
            for (int j = i + 1; j < flashcards.size(); j++) {
                if (flashcards.get(i).isSameFlashcard(flashcards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
