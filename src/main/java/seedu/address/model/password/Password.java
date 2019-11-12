package seedu.address.model.password;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.DateUtil;

/**
 * Represents a Password in the password book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Password {
    // Identity fields
    private final PasswordDescription passwordDescription;

    // Data fields
    private final Username username;
    private final PasswordValue passwordValue;
    private final PasswordModifiedAt passwordModifiedAt;
    private final Website website;
    private final PasswordExpireAt passwordExpireAt;
    private ExpiryMode expiryMode;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Password(PasswordDescription passwordDescription, Username username, PasswordValue passwordValue,
                    PasswordModifiedAt passwordModifiedAt, Website website, Set<Tag> tags) {
        requireAllNonNull(passwordDescription, username, passwordValue, passwordModifiedAt);
        this.passwordDescription = passwordDescription;
        this.username = username;
        this.passwordValue = passwordValue;
        this.passwordModifiedAt = passwordModifiedAt;
        this.website = website;
        this.tags.addAll(tags);
        passwordExpireAt = new PasswordExpireAt(DateUtil.findPasswordExpireAt(this.passwordModifiedAt.value));
    }

    public PasswordDescription getPasswordDescription() {
        return passwordDescription;
    }

    public Username getUsername() {
        return username;
    }

    public PasswordValue getPasswordValue() {
        return passwordValue;
    }

    public PasswordModifiedAt getPasswordModifiedAt() {
        return passwordModifiedAt;
    }

    public Website getWebsite() {
        return website;
    }

    public ExpiryMode getExpiryMode() {
        return expiryMode;
    }

    public PasswordExpireAt getPasswordExpireAt() {
        return passwordExpireAt;
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
     * Updates the expiry mode of the password
     */
    public void updateExpiry() {
        long time = DateUtil.findDaysPasswordExpireAt(new Date(), passwordExpireAt.value);
        if (time < 0) {
            expiryMode = ExpiryMode.EXPIRED;
        } else if (time < 90) {
            expiryMode = ExpiryMode.EXPIRING;
        } else {
            expiryMode = ExpiryMode.HEALTHY;
        }
    }

    /**
     * Returns the non-encrypted Password
     * @return non-encrypted Password
     */
    public String toNonAsterixString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getPasswordDescription())
                .append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getNonEncryptedPasswordValue())
                .append(" Modified at: ")
                .append(getPasswordModifiedAt());

        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getPasswordDescription())
                .append(" Username: ")
                .append(getUsername())
                .append(" Password: ")
                .append(getNonEncryptedPasswordValue())
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
                && otherPassword.getUsername().equals(getUsername())
                && otherPassword.getPasswordDescription().equals(getPasswordDescription());
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
        return otherPerson.getPasswordDescription().equals(getPasswordDescription())
                && otherPerson.getUsername().equals(getUsername())
                && otherPerson.getPasswordValue().equals(getPasswordValue())
                && otherPerson.getTags().equals(getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(passwordDescription, username, passwordValue, passwordModifiedAt, website, tags);
    }
}
