package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueReferenceIdList implements Iterable<ReferenceId> {

    private final ObservableList<ReferenceId> internalList = FXCollections.observableArrayList();
    private final ObservableList<ReferenceId> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReferenceId toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(ReferenceId toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    public ReferenceId get(int index) {
        return internalList.get(index);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(ReferenceId target, ReferenceId editedID) {
        requireAllNonNull(target, editedID);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedID) && contains(editedID)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedID);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(ReferenceId toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void remove(int index) {
        internalList.remove(index);
    }

    public void setPersons(UniqueReferenceIdList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setIds(List<ReferenceId> ids) {
        requireAllNonNull(ids);
        if (!personsAreUnique(ids)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(ids);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReferenceId> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ReferenceId> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReferenceIdList // instanceof handles nulls
                && internalList.equals(((UniqueReferenceIdList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<ReferenceId> ids) {
        for (int i = 0; i < ids.size() - 1; i++) {
            for (int j = i + 1; j < ids.size(); j++) {
                if (ids.get(i).equals(ids.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ReferenceId getFirst() {
        return internalList.get(0);
    }

    public int size() {
        return internalList.size();
    }
}
