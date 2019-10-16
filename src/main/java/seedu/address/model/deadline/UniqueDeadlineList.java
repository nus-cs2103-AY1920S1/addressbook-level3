package seedu.address.model.deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.deadline.exceptions.DeadlineNotFoundException;
import seedu.address.model.deadline.exceptions.DuplicateDeadlineException;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;

//@author dalsontws
/**
 * A list of Deadlines that enforces uniqueness between its elements and does not allow nulls.
 * A Deadline is considered unique by comparing using {@code Deadline#equals(Deadline)}.
 * As such, adding and updating of deadlines uses Deadline#equals(Deadline) for equality
 * so as to ensure that the flash card being added or updated
 * is unique in terms of identity in the UniqueDeadlineList.
 */
public class UniqueDeadlineList implements Iterable<Deadline> {

    private final ObservableList<Deadline> internalList = FXCollections.observableArrayList();
    private final ObservableList<Deadline> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Deadline as the given argument.
     */
    public boolean contains(Deadline toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a deadline to the list.
     */
    public void add(Deadline toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDeadlineException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the deadline {@code target} in the list with {@code editedDeadline}.
     * {@code target} must exist in the list.
     * The flashCard identity of {@code editedDeadline} must not be the same as another
     * existing deadline in the list.
     */
    public void setDeadline(Deadline target, Deadline editedDeadline) {
        requireAllNonNull(target, editedDeadline);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FlashCardNotFoundException();
        }

        if (!target.equals(editedDeadline) && contains(editedDeadline)) {
            throw new DuplicateDeadlineException();
        }

        internalList.set(index, editedDeadline);
    }

    public void setDeadline(UniqueDeadlineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent Deadline from the list.
     * The Deadline must exist in the list.
     */
    public void remove(Deadline toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DeadlineNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code flashCards}.
     * {@code flashCards} must not contain duplicate flashCards.
     */
    public void setDeadlines(List<Deadline> deadlines) {
        requireAllNonNull(deadlines);
        if (!deadlinesAreUnique(deadlines)) {
            throw new DuplicateDeadlineException();
        }
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


    /**
    /**
     * Returns true if {@code flashCards} contains only unique flashCards.
     */
    private boolean deadlinesAreUnique(List<Deadline> deadlines) {
        for (int i = 0; i < deadlines.size() - 1; i++) {
            for (int j = i + 1; j < deadlines.size(); j++) {
                if (deadlines.get(i).equals(deadlines.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
