package seedu.address.model.customer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer implements Cloneable {

    // Identity fields
    private final CustomerName customerName;
    private final ContactNumber contactNumber;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Customer(CustomerName customerName, ContactNumber contactNumber, Email email, Set<Tag> tags) {
        requireAllNonNull(customerName, contactNumber, email, tags);
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.tags.addAll(tags);
    }

    public CustomerName getCustomerName() {
        return customerName;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public Email getEmail() {
        return email;
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
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
                && otherCustomer.getCustomerName().equals(getCustomerName())
                && (otherCustomer.getContactNumber().equals(getContactNumber())
                || otherCustomer.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     * This defines a stronger notion of equality between two customers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return otherCustomer.getCustomerName().equals(getCustomerName())
                && otherCustomer.getContactNumber().equals(getContactNumber())
                && otherCustomer.getEmail().equals(getEmail())
                && otherCustomer.getTags().equals(getTags());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(customerName, contactNumber, email, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCustomerName())
                .append(" ContactNumber: ")
                .append(getContactNumber())
                .append(" Email: ")
                .append(getEmail())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
