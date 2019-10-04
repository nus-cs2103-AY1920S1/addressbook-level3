package seedu.deliverymans.model.order;

import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an Order in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Identity fields
    private final String customer;
    private final String restaurant;
    private final String deliveryman;
    private final String food;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    private Order(String customer, String restaurant, String deliveryman, String food) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
        this.food = food;
    }

    public String getCustomer() {
        return customer;
    }

    public String getDeliveryman() {
        return deliveryman;
    }

    public String getFood() {
        return food;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public boolean isCompleted() {
        return false;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both orders have the same identity and data fields.
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
        return otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getDeliveryman().equals(getDeliveryman())
                && otherOrder.getFood().equals(getFood())
                && otherOrder.getRestaurant().equals(getRestaurant());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(customer, restaurant, deliveryman, food);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Customer: ")
                .append(getCustomer())
                .append(" Restaurant: ")
                .append(getRestaurant())
                .append(" Deliveryman: ")
                .append(getDeliveryman())
                .append(" Food: ")
                .append(getFood());
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
