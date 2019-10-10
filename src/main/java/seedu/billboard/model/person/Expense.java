package seedu.billboard.model.person;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jdk.dynalink.NamedOperation;
import seedu.billboard.model.tag.Tag;

/**
 * Represents a Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {

    // Identity fields
    private Description description;
    private Amount amount;
//    private final Name name;
//    private final Phone phone;
//    private final Email email;

    // Data fields
//    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

//    /**
//     * Every field must be present and not null.
//     */
//    public Expense(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
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
    public Expense(Description description, Amount amount, Set<Tag> tags) {
        requireAllNonNull(description, amount);
        this.description = description;
        this.amount = amount;
        this.tags.addAll(tags);
    }

//    public Name getName() {
//        return name;
//    }
//
//    public Phone getPhone() {
//        return phone;
//    }
//
//    public Email getEmail() {
//        return email;
//    }
//
//    public Address getAddress() {
//        return address;
//    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
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
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
                && otherExpense.getDescription().equals(getDescription())
                && (otherExpense.getAmount().equals(getAmount()));
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

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getDescription().equals(getDescription())
                && otherExpense.getAmount().equals(getAmount());
//        return otherExpense.getName().equals(getName())
//                && otherExpense.getPhone().equals(getPhone())
//                && otherExpense.getEmail().equals(getEmail())
//                && otherExpense.getAddress().equals(getAddress())
//                && otherExpense.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount);
//        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount());
//        builder.append(getName())
//                .append(" Phone: ")
//                .append(getPhone())
//                .append(" Email: ")
//                .append(getEmail())
//                .append(" Address: ")
//                .append(getAddress())
//                .append(" Tags: ");
//        getTags().forEach(builder::append);
        return builder.toString();
    }

}
