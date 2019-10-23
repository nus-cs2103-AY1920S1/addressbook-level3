package seedu.deliverymans.model.order;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.deliverymans.model.Name;

/**
 * Represents an Order in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static int counter = 1;

    // Identity fields
    private final Name orderName;
    private final Name customer;
    private final Name restaurant;
    private Name deliveryman = new Name("unassigned");
    private boolean isCompleted;

    // Data fields
    private final Map<Name, Integer> foods = new HashMap<>();

    /**
     * Constructs a {@code Order}
     *
     * @param customer   The customer who made the order.
     * @param restaurant The restaurant.
     */
    public Order(Name customer, Name restaurant, Map<Name, Integer> foodList) {
        requireNonNull(customer);
        requireNonNull(restaurant);

        this.orderName = new Name(String.format("Order no %d", counter));
        ++counter;
        this.customer = customer;
        this.restaurant = restaurant;
        this.foods.putAll(foodList);
    }

    public Order(Name orderName, Name customer, Name restaurant, Map<Name, Integer> foodList) {
        requireNonNull(customer);
        requireNonNull(restaurant);

        this.orderName = orderName;
        this.customer = customer;
        this.restaurant = restaurant;
        this.foods.putAll(foodList);
    }

    public void addFood(Name food, int quantity) {
        foods.put(food, quantity);
    }

    public void addFood(Map<Name, Integer> foods) {
        this.foods.putAll(foods);
    }

    public Name getOrderName() {
        return orderName;
    }

    public Name getCustomer() {
        return customer;
    }

    public Name getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(Name deliveryman) {
        this.deliveryman = deliveryman;
    }

    /**
     * Returns an immutable food map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<Name, Integer> getFood() {
        return Collections.unmodifiableMap(foods);
    }

    public Name getRestaurant() {
        return restaurant;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void completeOrder() {
        isCompleted = true;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidOrderName(String test) {
        return test.matches(VALIDATION_REGEX);
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
                && otherOrder.getFood().equals(getFood());
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
        return otherOrder.getOrderName().equals(getOrderName())
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getDeliveryman().equals(getDeliveryman())
                && otherOrder.getFood().equals(getFood())
                && otherOrder.getRestaurant().equals(getRestaurant())
                && (otherOrder.isCompleted() == isCompleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, restaurant, deliveryman, foods);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getOrderName())
                .append(" Customer: ")
                .append(getCustomer())
                .append(" Restaurant: ")
                .append(getRestaurant())
                .append(" Deliveryman: ")
                .append(getDeliveryman())
                .append(" Food: ");

        // for (Map.Entry<Name, Integer> entry : getFood().entrySet()) {
        //    builder.append(String.format("%s x%d", entry.getKey().fullName, entry.getValue()));
        //}
        getFood().entrySet().forEach(entry -> {
            builder.append(String.format("%s x%d", entry.getKey().fullName, entry.getValue()));
        });
        builder.append(" Delivery status: ").append(isCompleted());
        return builder.toString();
    }
}
