package seedu.address.model.finance.logEntry;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.finance.attributes.Tag;


/**
 * Represents an entry in the finance log.
 * Guarantees: details are present and not null, field values are validated, mutable.
 */
public class LogEntry {

    // Identity fields
    private final Amount amount;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public LogEntry(Amount amount, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(amount, phone, email, address, tags);
        this.amount = amount;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Amount getAmount() {
        return amount;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(LogEntry otherLogEntry) {
        if (otherLogEntry == this) {
            return true;
        }

        return otherLogEntry != null
                && otherLogEntry.getAmount().equals(getAmount())
                && (otherLogEntry.getPhone().equals(getPhone()) || otherLogEntry.getEmail().equals(getEmail()));
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

        if (!(other instanceof LogEntry)) {
            return false;
        }

        LogEntry otherLogEntry = (LogEntry) other;
        return otherLogEntry.getAmount().equals(getAmount())
                && otherLogEntry.getPhone().equals(getPhone())
                && otherLogEntry.getEmail().equals(getEmail())
                && otherLogEntry.getAddress().equals(getAddress())
                && otherLogEntry.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAmount())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
