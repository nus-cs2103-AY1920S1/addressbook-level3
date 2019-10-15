package seedu.address.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.answerable.exceptions.DuplicateAnswerableException;
import seedu.address.model.answerable.exceptions.AnswerableNotFoundException;

/**
 * A list of answerables that enforces uniqueness between its elements and does not allow nulls.
 * A answerable is considered unique by comparing using {@code Answerable#isSameAnswerable(Answerable)}. As such, adding and updating of
 * answerables uses Answerable#isSameAnswerable(Answerable) for equality so as to ensure that the answerable being added or updated is
 * unique in terms of identity in the UniqueAnswerableList. However, the removal of a answerable uses Answerable#equals(Object) so
 * as to ensure that the answerable with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Answerable#isSameAnswerable(Answerable)
 */
public class UniqueAnswerableList implements Iterable<Answerable> {

    private final ObservableList<Answerable> internalList = FXCollections.observableArrayList();
    private final ObservableList<Answerable> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent answerable as the given argument.
     */
    public boolean contains(Answerable toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAnswerable);
    }

    /**
     * Adds a answerable to the list.
     * The answerable must not already exist in the list.
     */
    public void add(Answerable toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAnswerableException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the answerable {@code target} in the list with {@code editedAnswerable}.
     * {@code target} must exist in the list.
     * The answerable identity of {@code editedAnswerable} must not be the same as another existing answerable in the list.
     */
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        requireAllNonNull(target, editedAnswerable);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AnswerableNotFoundException();
        }

        if (!target.isSameAnswerable(editedAnswerable) && contains(editedAnswerable)) {
            throw new DuplicateAnswerableException();
        }

        internalList.set(index, editedAnswerable);
    }

    /**
     * Removes the equivalent answerable from the list.
     * The answerable must exist in the list.
     */
    public void remove(Answerable toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AnswerableNotFoundException();
        }
    }

    public void setAnswerables(UniqueAnswerableList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code answerables}.
     * {@code answerables} must not contain duplicate answerables.
     */
    public void setAnswerables(List<Answerable> answerables) {
        requireAllNonNull(answerables);
        if (!answerablesAreUnique(answerables)) {
            throw new DuplicateAnswerableException();
        }

        internalList.setAll(answerables);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Answerable> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Answerable> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAnswerableList // instanceof handles nulls
                        && internalList.equals(((UniqueAnswerableList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code answerables} contains only unique answerables.
     */
    private boolean answerablesAreUnique(List<Answerable> answerables) {
        for (int i = 0; i < answerables.size() - 1; i++) {
            for (int j = i + 1; j < answerables.size(); j++) {
                if (answerables.get(i).isSameAnswerable(answerables.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
