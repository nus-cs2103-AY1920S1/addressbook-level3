package seedu.address.model.password;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
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
    public Password(Description description, Username username, PasswordValue passwordValue) {
        requireAllNonNull(description, username, passwordValue);
        this.description = description;
        this.username = username;
        this.passwordValue = passwordValue;
        //this.tags.addAll(tags);
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

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getPasswordValue());

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
}
