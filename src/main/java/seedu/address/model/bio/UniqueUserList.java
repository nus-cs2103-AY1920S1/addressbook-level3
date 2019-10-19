package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bio.exceptions.DuplicateUserException;
import seedu.address.model.bio.exceptions.UserNotFoundException;

/**
 * A list of users that enforces uniqueness between its elements and does not allow nulls.
 * A user is considered unique by comparing using {@code User#isSameUser(User)}. As such, adding and updating of
 * users uses User#isSameUser(User) for equality so as to ensure that the user being added or updated is
 * unique in terms of identity in the UniqueUserList. However, the removal of a user uses User#equals(Object) so
 * as to ensure that the user with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see User#isSameUser(User)
 */
public class UniqueUserList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();
    private final ObservableList<User> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent user as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameUser);
    }

    /**
     * Returns whether or not the user list is empty.
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Adds a user to the list.
     * The user must not already exist in the list.
     */
    public void add(User toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the user {@code target} in the list with {@code editedUser}.
     * {@code target} must exist in the list.
     * The user identity of {@code editedUser} must not be the same as another existing user in the list.
     */
    public void setUser(User target, User editedUser) {
        requireAllNonNull(target, editedUser);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new UserNotFoundException();
        }

        if (!target.isSameUser(editedUser) && contains(editedUser)) {
            throw new DuplicateUserException();
        }

        internalList.set(index, editedUser);
    }

    /**
     * Removes the equivalent user from the list.
     * The user must exist in the list.
     */
    public void remove(User toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new UserNotFoundException();
        }
    }

    public void setUsers(UniqueUserList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code users}.
     * {@code users} must not contain duplicate users.
     */
    public void setUsers(List<User> users) {
        requireAllNonNull(users);
        if (!usersAreUnique(users)) {
            throw new DuplicateUserException();
        }

        internalList.setAll(users);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<User> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                        && internalList.equals(((UniqueUserList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code users} contains only unique users.
     */
    private boolean usersAreUnique(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).isSameUser(users.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
