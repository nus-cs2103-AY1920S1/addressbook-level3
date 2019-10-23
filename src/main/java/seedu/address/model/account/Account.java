package seedu.address.model.account;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.authentication.Authentication;

/**
 * Represents a User's account.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {


    // Identity fields
    private Username username;
    private String password;

    /**
     * Every field must be present and not null.
     */
    public Account(Username userId, String pass) {
        requireAllNonNull(userId, pass);
        username = userId;
        password = pass;
    }

    public Username getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Hashes the password registered by
     * the user so that it is encrypted.
     */
    public void hashPassword() {
        Authentication authenticator = new Authentication();
        this.password = authenticator.hash(password.toCharArray());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(getUsername())
                && otherAccount.getPassword().equals(getPassword());
    }
}
