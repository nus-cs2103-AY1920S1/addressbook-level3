package seedu.deliverymans.model.order;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.deliverymans.model.Name;

/**
 * Represents an Order in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";

    // Identity fields
    private final Name customer;
    private final Name restaurant;
    private final Name deliveryman;

    // Data fields
    private final ObservableMap<Name, Integer> foodList = FXCollections.observableHashMap();
    private final boolean isCompleted;

    /**
     * Constructs a {@code Order}
     *
     * @param customer    The customer who made the order.
     * @param restaurant  The restaurant to order from.
     * @param deliveryman The deliveryman delivering the order.
     * @param foodList    The list of food ordered with their respective quantities;
     * @param isCompleted The completion status of the order.
     */
    private Order(Name customer, Name restaurant, Name deliveryman,
                  Map<Name, Integer> foodList, boolean isCompleted) {
        requireAllNonNull(customer, restaurant, deliveryman, foodList);
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
        this.foodList.putAll(foodList);
        this.isCompleted = isCompleted;
    }

    public Name getCustomer() {
        return customer;
    }

    public Name getDeliveryman() {
        return deliveryman;
    }

    /**
     * Returns an immutable food map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<Name, Integer> getFoodList() {
        return Collections.unmodifiableMap(foodList);
    }

    public Name getRestaurant() {
        return restaurant;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getDeliveryman().equals(getDeliveryman())
                && otherOrder.getRestaurant().equals(getRestaurant())
                && otherOrder.getFoodList().equals(getFoodList());
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
        // return other == this || (other instanceof Order && orderName.equals(((Order) other).orderName));

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getDeliveryman().equals(getDeliveryman())
                && otherOrder.getFoodList().equals(getFoodList())
                && otherOrder.getRestaurant().equals(getRestaurant())
                && (otherOrder.isCompleted() == isCompleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, restaurant, deliveryman, foodList);
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
                .append(" Food: ");

        getFoodList().forEach((key, value) -> builder.append(String.format("%s x%d", key, value)));
        builder.append(" Delivery status: ").append(isCompleted());
        return builder.toString();
    }

    /**
     * OrderBuilder used to instantiate an Order.
     * Contains all relevant information regarding an Order,
     * and creates a new Order object once its completeOrder() function is called
     */
    public static class OrderBuilder {
        private Name customer;
        private Name restaurant;
        private Name deliveryman = new Name("Unassigned");
        private boolean isCompleted = false;
        private final Map<Name, Integer> foodList = new HashMap<>();

        public OrderBuilder setCustomer(Name customer) {
            this.customer = customer;
            return this;
        }

        public OrderBuilder setRestaurant(Name restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public OrderBuilder setDeliveryman(Name deliveryman) {
            this.deliveryman = deliveryman;
            return this;
        }

        public OrderBuilder setFood(Map<Name, Integer> foodList) {
            this.foodList.putAll(foodList);
            return this;
        }

        public OrderBuilder setCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Order completeOrder() {
            return new Order(customer, restaurant, deliveryman, foodList, isCompleted);
        }
    }
}
