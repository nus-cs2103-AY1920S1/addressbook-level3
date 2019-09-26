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
    private final OrderId orderId;

    // Data fields
    private final Customer customer;
    private final Phone phone;
    private final OrderStatus orderStatus;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(Customer customer, Phone phone, OrderId orderId, Set<Tag> tags) {
        requireAllNonNull(customer, phone, orderId, tags);
        this.customer = customer;
        this.phone = phone;
        this.orderId = orderId;
        orderStatus = OrderStatus.UNSCHEDULED;
        this.tags.addAll(tags);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Phone getPhone() {
        return phone;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
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
                && otherOrder.getOrderId().equals(getOrderId());
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
        return otherOrder.getOrderId().equals(getOrderId())
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getOrderStatus().equals(getOrderStatus())
                && otherOrder.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(orderId, customer, phone, orderStatus, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("#")
                .append(getOrderId())
                .append(" Customer: ")
                .append(getCustomer())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Order Status: ")
                .append(getOrderStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
