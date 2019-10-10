package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Entry in the finance app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Entry {

    // Identity fields
    private final Description desc;
    private final Amount amt;
//    private final Phone phone;
//    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */

    public Entry(Description desc, Amount amount, Set<Tag> tags) {
        requireAllNonNull(desc, amount, tags);
        this.desc = desc;
        this.amt = amount;
//        this.phone = phone;
//        this.email = email;
        this.tags.addAll(tags);
    }

    public Description getDesc() {
        return desc;
    }

    public Amount getAmount() {
        return amt;
    }

    public String getType() {
        return "Not like this";
    }

//    public Phone getPhone() {
//        return phone;
//    }
//
//    public Email getEmail() {
//        return email;
//    }


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
    public boolean isSameEntry(Entry otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return otherEntry != null
                && otherEntry.getDesc().equals(getDesc())
                && otherEntry.getAmount().equals(getAmount());
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

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherEntry = (Entry) other;
        return otherEntry.getDesc().equals(getDesc())
                && otherEntry.getAmount().equals(getAmount())
                && otherEntry.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(desc, amt, tags);
    }

//    @Override
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(getDesc())
//                .append(" Amount: ")
//                .append()
//                .append(" Email: ")
//                .append(getEmail())
//                .append(" Address: ")
//                .append(" Tags: ");
//        getTags().forEach(builder::append);
//        return builder.toString();
//    }


}
