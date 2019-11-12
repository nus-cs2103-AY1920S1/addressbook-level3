package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.password.Password;
import seedu.address.model.password.UniquePasswordList;

/**
 * Wraps all data at the password-book level
 */
public class PasswordBook implements ReadOnlyPasswordBook {

    private final UniquePasswordList passwords;

    public PasswordBook() {
        passwords = new UniquePasswordList();
    }

    /**
     * Creates an PasswordBook using the Passwords in the {@code toBeCopied}
     */
    public PasswordBook(ReadOnlyPasswordBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the password list with {@code passwords}.
     * {@code passwords} must not contain duplicate passwords.
     */
    public void setPasswords(List<Password> passwords) {
        this.passwords.setPassword(passwords);
    }

    /**
     * Resets the existing data of this {@code PasswordBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPasswordBook newData) {
        requireNonNull(newData);

        setPasswords(newData.getPasswordList());
    }

    /**
     * Returns true if a password with the same identity as {@code password} exists in the password book.
     */
    public boolean hasPassword(Password password) {
        requireNonNull(password);
        return passwords.contains(password);
    }

    /**
     * Adds a password to the password book.
     * The password must not already exist in the password book.
     */
    public void addPassword(Password p) {
        passwords.add(p);
    }

    /**
     * Replaces the given password {@code target} in the list with {@code editedPassword}.
     * {@code target} must exist in the password book.
     * The person identity of {@code editedPassword} must not be the same as another existing password
     * in the password book.
     */
    public void setPassword(Password target, Password editedPassword) {
        requireNonNull(editedPassword);

        passwords.setPassword(target, editedPassword);
    }

    /**
     * Removes {@code p} from this {@code Password}.
     * {@code p} must exist in the password book.
     */
    public void removePassword(Password p) {
        passwords.remove(p);
    }

    @Override
    public ObservableList<Password> getPasswordList() {
        return passwords.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordBook // instanceof handles nulls
                && passwords.equals(((PasswordBook) other).passwords));
    }
}
