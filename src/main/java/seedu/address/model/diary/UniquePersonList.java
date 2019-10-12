package seedu.address.model.diary;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.diary.exceptions.DuplicatePersonException;
import seedu.address.model.diary.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A diary is considered unique by comparing using {@code Diary#isSamePerson(Diary)}. As such, adding and updating of
 * persons uses Diary#isSamePerson(Diary) for equality so as to ensure that the diary being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a diary uses Diary#equals(Object) so
 * as to ensure that the diary with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Diary#isSamePerson(Diary)
 */
public class UniquePersonList implements Iterable<Diary> {

    private final ObservableList<Diary> internalList = FXCollections.observableArrayList();
    private final ObservableList<Diary> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent diary as the given argument.
     */
    public boolean contains(Diary toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a diary to the list.
     * The diary must not already exist in the list.
     */
    public void add(Diary toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the diary {@code target} in the list with {@code editedDiary}.
     * {@code target} must exist in the list.
     * The diary identity of {@code editedDiary} must not be the same as another existing diary in the list.
     */
    public void setPerson(Diary target, Diary editedDiary) {
        requireAllNonNull(target, editedDiary);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedDiary) && contains(editedDiary)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedDiary);
    }

    /**
     * Removes the equivalent diary from the list.
     * The diary must exist in the list.
     */
    public void remove(Diary toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code diaries}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setPersons(List<Diary> diaries) {
        requireAllNonNull(diaries);
        if (!personsAreUnique(diaries)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(diaries);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Diary> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Diary> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code diaries} contains only unique diaries.
     */
    private boolean personsAreUnique(List<Diary> diaries) {
        for (int i = 0; i < diaries.size() - 1; i++) {
            for (int j = i + 1; j < diaries.size(); j++) {
                if (diaries.get(i).isSamePerson(diaries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
