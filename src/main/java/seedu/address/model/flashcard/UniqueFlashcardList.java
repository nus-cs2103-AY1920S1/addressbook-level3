package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardQuestionException;
import seedu.address.model.flashcard.exceptions.DuplicateFlashcardTitleException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;

/**
 * A list of flashcards that enforces uniqueness between its elements and does not allow nulls.
 * A flashcard is considered unique by comparing using {@code Flashcard#equals(Object)}. As such, adding and
 * updating of flashcards uses Flashcard#equals(Object) for equality so as to ensure that the flashcard being added or
 * updated is unique in the UniqueFlashcardList. The removal of a flashcard uses Flashcard#equals(Object) as well so
 * as to ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Flashcard#equals(Object)
 */
public class UniqueFlashcardList implements Iterable<Flashcard> {

    private final ObservableList<Flashcard> internalList = FXCollections.observableArrayList();
    private final ObservableList<Flashcard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Helper method to check if a flashcard contains the same question or title as any other flashcard.
     * @param toCheck Flashcard to be checked.
     * @param beingChecked Flashcard being checked.
     * @throws DuplicateFlashcardQuestionException if toCheck has same question as beingChecked
     * @throws DuplicateFlashcardTitleException if toCheck has same title as beingChecked
     * @throws DuplicateFlashcardException if toCheck is the same flascard as beingChecked
     */
    private void flashcardExceptionTypeHelper(Flashcard toCheck, Flashcard beingChecked)
        throws DuplicateFlashcardQuestionException, DuplicateFlashcardTitleException, DuplicateFlashcardException {
        boolean hasSameQuestion = beingChecked.getQuestion().equals(toCheck.getQuestion());
        boolean hasSameTitle = beingChecked.getTitle().equals(toCheck.getTitle());
        if (hasSameQuestion && hasSameTitle) {
            throw new DuplicateFlashcardException();
        } else if (hasSameQuestion) {
            throw new DuplicateFlashcardQuestionException();
        } else if (hasSameTitle) {
            throw new DuplicateFlashcardTitleException();
        }
    }

    /**
     * Returns true if the list contains an equivalent flashcard as the given argument.
     */
    public boolean contains(Flashcard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFlashcard);
    }

    /**
     * Adds a flashcard to the list.
     * The flashcard must not already exist in the list.
     */
    public void add(Flashcard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            for (Flashcard beingChecked : internalList) {
                flashcardExceptionTypeHelper(toAdd, beingChecked);
            }
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The flashcard's fields of {@code editedFlashcard} must not be the same as another existing flashcard in the list.
     */
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FlashcardNotFoundException();
        }

        if (!target.isSameFlashcard(editedFlashcard) && contains(editedFlashcard)) {
            for (Flashcard beingChecked : internalList) {
                flashcardExceptionTypeHelper(editedFlashcard, beingChecked);
            }
        }

        internalList.set(index, editedFlashcard);
    }

    /**
     * Removes the equivalent flashcard from the list.
     * The flashcard must exist in the list.
     */
    public void remove(Flashcard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FlashcardNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setFlashcards(UniqueFlashcardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setFlashcards(List<Flashcard> flashcards) {
        requireAllNonNull(flashcards);
        if (!flashcardsAreUnique(flashcards)) {
            throw new DuplicateFlashcardException();
        }

        internalList.setAll(flashcards);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Flashcard> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Flashcard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFlashcardList // instanceof handles nulls
                && internalList.equals(((UniqueFlashcardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code flashcards} contains only unique flashcards.
     */
    private boolean flashcardsAreUnique(List<Flashcard> flashcards) {
        for (int i = 0; i < flashcards.size() - 1; i++) {
            for (int j = i + 1; j < flashcards.size(); j++) {
                if (flashcards.get(i).equals(flashcards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
