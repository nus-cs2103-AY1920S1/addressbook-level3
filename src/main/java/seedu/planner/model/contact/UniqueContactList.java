package seedu.planner.model.contact;

import static java.util.Objects.requireNonNull;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.core.index.Index;
import seedu.planner.model.contact.exceptions.ContactNotFoundException;
import seedu.planner.model.contact.exceptions.DuplicateContactException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A contacts is considered unique by comparing using {@code Contact#isSameContact(Contact)}. As such, adding and
 * updating of persons uses Contact#isSameContact(Contact) for equality so as to ensure that the contacts being
 * added or updated is unique in terms of identity in the UniqueContactList. However, the removal of a
 * contacts uses Contact#equals(Object) so as to ensure that the contacts with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Contact#isSameContact(Contact)
 */
public class UniqueContactList implements Iterable<Contact> {

    private final ObservableList<Contact> internalList = FXCollections.observableArrayList();
    private final ObservableList<Contact> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent contacts as the given argument.
     */
    public boolean contains(Contact toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameContact);
    }

    /**
     * Returns the Index of the accommodation to find. Else returns empty optional.
     */
    public Optional<Index> indexOf(Contact toFind) {
        requireNonNull(toFind);
        int indexOfToFind = internalList.indexOf(toFind);
        if (indexOfToFind == -1) {
            return Optional.empty();
        } else {
            return Optional.of(Index.fromZeroBased(indexOfToFind));
        }
    }

    /**
     * Returns true if the list contains a contact with the same number as the given argument.
     */
    public boolean containsPhone(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(x -> x.getPhone().equals(toCheck));
    }

    /**
     * Adds a contact to the list.
     * The contacts must not already exist in the list.
     */
    public void add(Contact toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateContactException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a contact to the list at a specific index.
     * The contact must not already exist in the list.
     */
    public void addAtIndex(Index index, Contact toAdd) {
        requireAllNonNull(index, toAdd);
        if (contains(toAdd)) {
            throw new DuplicateContactException();
        }
        internalList.add(index.getZeroBased(), toAdd);
    }

    /**
     * Replaces the contacts {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the list.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the list.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ContactNotFoundException();
        }

        if (!target.isSameContact(editedContact) && contains(editedContact)) {
            throw new DuplicateContactException();
        }

        internalList.set(index, editedContact);
    }

    /**
     * Removes the equivalent contacts from the list.
     * The contacts must exist in the list.
     */
    public void remove(Contact toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ContactNotFoundException();
        }
    }

    public void setContacts(UniqueContactList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        requireAllNonNull(contacts);
        if (!contactsAreUnique(contacts)) {
            throw new DuplicateContactException();
        }

        internalList.setAll(contacts);
    }

    /**
     * Gets the first instance of the specified contact
     */
    public Optional<Contact> getContact(Contact contact) {
        requireNonNull(contact);
        return internalList.stream().filter(x -> x.equals(contact)).findFirst();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Contact> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Contact> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueContactList // instanceof handles nulls
                        && internalList.equals(((UniqueContactList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code contacts} contains only unique contacts.
     */
    private boolean contactsAreUnique(List<Contact> contacts) {
        for (int i = 0; i < contacts.size() - 1; i++) {
            for (int j = i + 1; j < contacts.size(); j++) {
                if (contacts.get(i).isSameContact(contacts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
