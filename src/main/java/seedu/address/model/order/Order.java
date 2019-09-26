package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

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

    /**
     * Every field must be present and not null.
     */
    public Order(Customer customer, Phone phone, OrderId orderId) {
        requireAllNonNull(customer, phone, orderId);
        this.customer = customer;
        this.phone = phone;
        this.orderId = orderId;
        // ENUM STATUS
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
     * Returns true if both orders have the same order ID.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.orderId().equals(orderId());
    }

    /**
     * Returns true if both persons have the same order ID and data fields.
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
                && otherOrder.getOrderStatus().equals(getOrderStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(orderId, customer, phone, orderStatus);
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
                .append(getOrderStatus());
        return builder.toString();
    }
}
