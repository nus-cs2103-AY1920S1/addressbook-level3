package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Order, with the Customer and Phone purchased included. .
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Identity fields
    private final Id id;

    // Data fields
    private final Customer customer;
    private final Phone phone;
    private final Status status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(Customer customer, Phone phone, Id id, Set<Tag> tags) {
        requireAllNonNull(customer, phone, id, tags);
        this.customer = customer;
        this.phone = phone;
        this.id = id;
        status = Status.UNSCHEDULED;
        this.tags.addAll(tags);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Phone getPhone() {
        return phone;
    }

    public Id getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both orders have the same identity fields.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getId().equals(getId())
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getStatus().equals(getStatus())
                && otherOrder.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, customer, phone, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("#")
                .append(getId())
                .append(" Customer: ")
                .append(getCustomer())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Order Status: ")
                .append(getStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
