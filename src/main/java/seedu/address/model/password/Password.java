package seedu.address.model.password;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Password in the password book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Password {
    // Identity fields
    private final Description description;

    // Data fields
    private final Username username;
    private final PasswordValue passwordValue;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Password(Description description, Username username, PasswordValue passwordValue, Set<Tag> tags) {
        requireAllNonNull(description, username, passwordValue);
        this.description = description;
        this.username = username;
        this.passwordValue = passwordValue;
        this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public Username getUsername() {
        return username;
    }

    public PasswordValue getPasswordValue() {
        return passwordValue;
    }

    /**
     * Returns the non-encrypted PasswordValue
     * @return non-encrypted PasswordValue
     */
    public String getNonEncryptedPasswordValue() {
        return passwordValue.getNonEncryptedPasswordValue();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the non-encrypted Password
     * @return non-encrypted Password
     */
    public String toNonAsterixString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getNonEncryptedPasswordValue());

        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getPasswordValue())
                .append("\n");

        return builder.toString();
    }

    /**
     * Returns true if both passwords have the same description.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePassword(Password otherPassword) {
        if (otherPassword == this) {
            return true;
        }

        return otherPassword != null
                && otherPassword.getDescription().equals(getDescription());
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

        if (!(other instanceof Password)) {
            return false;
        }

        Password otherPerson = (Password) other;
        return otherPerson.getDescription().equals(getDescription())
                && otherPerson.getUsername().equals(getUsername())
                && otherPerson.getPasswordValue().equals(getPasswordValue())
                && otherPerson.getTags().equals(getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, username, passwordValue, tags);
    }
}
