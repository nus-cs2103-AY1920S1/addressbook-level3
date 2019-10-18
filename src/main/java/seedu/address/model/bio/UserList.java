package seedu.address.model.bio;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyUserList;

/**
 * Wraps all data at the user-list level
 * Duplicates are not allowed (by .isSameUser comparison)
 */
public class UserList implements ReadOnlyUserList {

    private final UniqueUserList users;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        users = new UniqueUserList();
    }

    public UserList() {}

    /**
     * Creates an UserList using the Users in the {@code toBeCopied}
     */
    public UserList(ReadOnlyUserList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Returns whether or not the user list is empty.
     */    
    public boolean isEmpty() {
        return users.isEmpty();
    }
    
    /**
     * Replaces the contents of the user list with {@code users}.
     * {@code users} must not contain duplicate users.
     */
    public void setUsers(List<User> users) {
        this.users.setUsers(users);
    }

    /**
     * Resets the existing data of this {@code UserList} with {@code newData}.
     */
    public void resetData(ReadOnlyUserList newData) {
        requireNonNull(newData);

        setUsers(newData.getUserList());
    }

    //// user-level operations

    /**
     * Returns true if a user with the same identity as {@code user} exists in the user list.
     */
    public boolean hasUser(User user) {
        requireNonNull(user);
        return users.contains(user);
    }

    /**
     * Adds a user to the user list.
     * The user must not already exist in the user list.
     */
    public void addUser(User p) {
        users.add(p);
    }

    /**
     * Replaces the given user {@code target} in the list with {@code editedUser}.
     * {@code target} must exist in the user list.
     * The user identity of {@code editedUser} must not be the same as another existing user in the user list.
     */
    public void setUser(User target, User editedUser) {
        requireNonNull(editedUser);

        users.setUser(target, editedUser);
    }

    /**
     * Removes {@code key} from this {@code UserList}.
     * {@code key} must exist in the user list.
     */
    public void removeUser(User key) {
        users.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return users.asUnmodifiableObservableList().size() + " users";
        // TODO: refine later
    }

    @Override
    public ObservableList<User> getUserList() {
        return users.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserList // instanceof handles nulls
                && users.equals(((UserList) other).users));
    }

    @Override
    public int hashCode() {
        return users.hashCode();
    }
}
