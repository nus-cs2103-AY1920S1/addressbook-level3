package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class to ensure that groups are unique. Necessary for UI to render group list in tabpanel.
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalGroupList = FXCollections.observableArrayList();
    private final ObservableList<Group> internallUnmodifiableGroupList =
            FXCollections.unmodifiableObservableList(internalGroupList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalGroupList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            //throw new DuplicatePersonException();
        }
        internalGroupList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Group target, Group editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalGroupList.indexOf(target);
        /* Create exception class for groups.
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.equals(editedPerson) && contains(editedPerson)) {
            throw new DuplicateGroupException();
        }
        */
        internalGroupList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalGroupList.remove(toRemove)) {
            //throw new GroupNotFoundException();
        }
    }

    public void setPersons(UniqueGroupList replacement) {
        requireNonNull(replacement);
        internalGroupList.setAll(replacement.internalGroupList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Group> persons) {
        requireAllNonNull(persons);
        if (!groupsAreUnique(persons)) {
            //throw new DuplicateGroupException();
        }

        internalGroupList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internallUnmodifiableGroupList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalGroupList.iterator();
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof UniqueGroupList
                && internalGroupList.equals(((UniqueGroupList) o).internalGroupList));
    }

    @Override
    public int hashCode() {
        return internalGroupList.hashCode();
    }

    /**
     * Method to check if the groups are all unique.
     * @param groups Group list to check.
     * @return boolean true or false.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        HashSet<Group> groupHashSet = new HashSet<>();
        boolean unique = true;
        for (Group g : groups) {
            if (groupHashSet.contains(g)) {
                unique = false;
                break;
            }
            groupHashSet.add(g);
        }
        return unique;
    }
}
