package seedu.billboard.model.person;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.billboard.model.tag.Tag;

/**
 * Represents a Record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Record {

    // Identity fields
    private Name name;
    private Description description;
    protected Amount amount;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

//    /**
//     * Every field must be present and not null.
//     */
//    public Record(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
//        requireAllNonNull(name, phone, email, address, tags);
//        this.name = name;
//        this.phone = phone;
//        this.email = email;
//        this.address = address;
//        this.tags.addAll(tags);
//    }

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Description description, Amount amount, Set<Tag> tags) {
        requireAllNonNull(name, description, amount);
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public abstract Amount getAmount();

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both records of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getDescription().equals(getDescription())
                && (otherRecord.getAmount().equals(getAmount()));
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

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.getDescription().equals(getDescription())
                && otherRecord.getAmount().equals(getAmount());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount);
//        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return "Description: " +
                getDescription() +
                " Amount: " +
                getAmount();
    }
}
