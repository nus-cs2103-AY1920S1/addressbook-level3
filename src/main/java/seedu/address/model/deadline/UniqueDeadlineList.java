package seedu.address.model.deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Iterator;
import java.util.List;

import seedu.address.model.flashcard.FlashCard;
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
public class UniqueDeadlineList implements Iterable<Deadline> {

    private final ObservableList<Deadline> internalList = FXCollections.observableArrayList();
    private final ObservableList<Deadline> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Adds a deadline to the list.
     */
    public void add(Deadline toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent flashCard from the list.
     * The flashCard must exist in the list.
     */
    public void remove(Deadline toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FlashCardNotFoundException();
        }
    }

    public void setDeadline(UniqueDeadlineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashCards}.
     * {@code flashCards} must not contain duplicate flashCards.
     */
    public void setDeadline(List<Deadline> deadlines) {
        requireAllNonNull(deadlines);
        internalList.setAll(deadlines);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Deadline> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Deadline> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDeadlineList // instanceof handles nulls
                        && internalList.equals(((UniqueDeadlineList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
