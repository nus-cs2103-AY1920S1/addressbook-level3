package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;

/**
 * A list of flash cards that enforces uniqueness between its elements and does not allow nulls.
 * A flash card is considered unique by comparing using {@code FlashCard#isSameFlashCard(FlashCard)}.
 * As such, adding and updating of flash cards uses FlashCard#isSameFlashCard(FlashCard) for equality
 * so as to ensure that the flash card being added or updated
 * is unique in terms of identity in the UniqueFlashCardList.
 * However, the removal of a flashCard uses FlashCard#equals(Object) so
 * as to ensure that the flashCard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see FlashCard#isSameFlashCard(FlashCard)
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
        return internalList.stream().anyMatch(toCheck::isSameFlashCard);
    }

    /**
     * Adds a flashCard to the list.
     * The flashCard must not already exist in the list.
     */
    public void add(FlashCard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFlashCardException();
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
            throw new FlashCardNotFoundException();
        }

        if (!target.isSameFlashCard(editedFlashCard) && contains(editedFlashCard)) {
            throw new DuplicateFlashCardException();
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
            throw new FlashCardNotFoundException();
        }
    }

    public void setFlashCards(UniqueFlashCardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashCards}.
     * {@code flashCards} must not contain duplicate flashCards.
     */
    public void setFlashCards(List<FlashCard> flashCards) {
        requireAllNonNull(flashCards);
        if (!flashCardsAreUnique(flashCards)) {
            throw new DuplicateFlashCardException();
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
    private boolean flashCardsAreUnique(List<FlashCard> flashCards) {
        for (int i = 0; i < flashCards.size() - 1; i++) {
            for (int j = i + 1; j < flashCards.size(); j++) {
                if (flashCards.get(i).isSameFlashCard(flashCards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
