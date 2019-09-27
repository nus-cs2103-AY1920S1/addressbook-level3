package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.exceptions.DuplicatePersonException;
import seedu.address.model.flashcard.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A flashCard is considered unique by comparing using {@code FlashCard#isSamePerson(FlashCard)}. As such, adding and updating of
 * persons uses FlashCard#isSamePerson(FlashCard) for equality so as to ensure that the flashCard being added or updated is
 * unique in terms of identity in the UniqueFlashCardList. However, the removal of a flashCard uses FlashCard#equals(Object) so
 * as to ensure that the flashCard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see FlashCard#isSamePerson(FlashCard)
 */
public class UniqueFlashCardList implements Iterable<FlashCard> {

    private final ObservableList<FlashCard> internalList = FXCollections.observableArrayList();
    private final ObservableList<FlashCard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent flashCard as the given argument.
     */
    public boolean contains(FlashCard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a flashCard to the list.
     * The flashCard must not already exist in the list.
     */
    public void add(FlashCard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the flashCard {@code target} in the list with {@code editedFlashCard}.
     * {@code target} must exist in the list.
     * The flashCard identity of {@code editedFlashCard} must not be the same as another existing flashCard in the list.
     */
    public void setFlashcard(FlashCard target, FlashCard editedFlashCard) {
        requireAllNonNull(target, editedFlashCard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedFlashCard) && contains(editedFlashCard)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedFlashCard);
    }

    /**
     * Removes the equivalent flashCard from the list.
     * The flashCard must exist in the list.
     */
    public void remove(FlashCard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniqueFlashCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashCards}.
     * {@code flashCards} must not contain duplicate flashCards.
     */
    public void setPersons(List<FlashCard> flashCards) {
        requireAllNonNull(flashCards);
        if (!personsAreUnique(flashCards)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(flashCards);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<FlashCard> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<FlashCard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFlashCardList // instanceof handles nulls
                        && internalList.equals(((UniqueFlashCardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code flashCards} contains only unique flashCards.
     */
    private boolean personsAreUnique(List<FlashCard> flashCards) {
        for (int i = 0; i < flashCards.size() - 1; i++) {
            for (int j = i + 1; j < flashCards.size(); j++) {
                if (flashCards.get(i).isSamePerson(flashCards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
