package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.password.exceptions.DuplicatePasswordException;
import seedu.address.model.password.exceptions.PasswordNotFoundException;
/**
 * A list of password that enforces uniqueness between its elements and does not allow nulls.
 * A password is considered unique by comparing using {@code Password#isSamePassword(Password)}. As such, adding
 * and updating of passwords uses Password#isSamePassword(Password) for equality so as to ensure that the password
 * being added or updated is unique in terms of identity in the UniquePasswordList. However, the removal of a password
 * uses Password#equals(Object) so as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Password#isSamePassword(Password)
 */
public class UniquePasswordList implements Iterable<Password> {
    private final ObservableList<Password> internalList = FXCollections.observableArrayList();
    private final ObservableList<Password> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent password as the given argument.
     */
    public boolean contains(Password toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePassword);
    }

    /**
     * Adds a password to the list.
     * The password must not already exist in the list.
     */
    public void add(Password toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePasswordException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent password from the list.
     * The password must exist in the list.
     */
    public void remove(Password toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PasswordNotFoundException();
        }
    }

    public void setPasswords(UniquePasswordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code passwords}.
     * {@code passwords} must not contain duplicate passwords.
     */
    public void setPassword(List<Password> passwords) {
        requireAllNonNull(passwords);
        if (!passwordsAreUnique(passwords)) {
            throw new DuplicatePasswordException();
        }
        internalList.setAll(passwords);
    }

    /**
     * Replaces the password {@code target} in the list with {@code editedPassword}.
     * {@code target} must exist in the list.
     * The password identity of {@code edited password} must not be the same as another existing password in the list.
     */
    public void setPassword(Password target, Password editedPassword) {
        requireAllNonNull(target, editedPassword);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PasswordNotFoundException();
        }

        if (!target.isSamePassword(editedPassword) && contains(editedPassword)) {
            throw new DuplicatePasswordException();
        }

        internalList.set(index, editedPassword);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Password> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Password> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePasswordList // instanceof handles nulls
                && internalList.equals(((UniquePasswordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code passwords} contains only unique passwords.
     */
    private boolean passwordsAreUnique(List<Password> passwords) {
        for (int i = 0; i < passwords.size() - 1; i++) {
            for (int j = i + 1; j < passwords.size(); j++) {
                if (passwords.get(i).isSamePassword(passwords.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
