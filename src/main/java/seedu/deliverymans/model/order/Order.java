package seedu.deliverymans.model.order;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Represents an Order in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";

    // Identity fields
    private final Customer customer;
    private final Restaurant restaurant;
    private final Deliveryman deliveryman;

    // Data fields
    private final ObservableMap<Food, Integer> foodList = FXCollections.observableHashMap();
    private boolean isCompleted;

    /**
     * Constructs a {@code Order}
     *
     * @param customer    The customer who made the order.
     * @param restaurant  The restaurant to order from.
     * @param deliveryman The deliveryman delivering the order.
     */
    public Order(Customer customer, Restaurant restaurant, Deliveryman deliveryman) {
        requireAllNonNull(customer, restaurant, deliveryman);
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
    }

    /**
     * Constructs a {@code Order}
     *
     * @param customer    The customer who made the order.
     * @param restaurant  The restaurant to order from.
     * @param deliveryman The deliveryman delivering the order.
     * @param foodList    The list of food ordered with their respective quantities;
     */
    public Order(Customer customer, Restaurant restaurant, Deliveryman deliveryman,
                 ObservableMap<Food, Integer> foodList) {
        requireAllNonNull(customer, restaurant, deliveryman, foodList);
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
        this.foodList.putAll(foodList);
    }

    public void addFood(Map<Food, Integer> foods) {
        this.foodList.putAll(foods);
    }

    public Name getCustomer() {
        return customer.getName();
    }

    public Name getDeliveryman() {
        return deliveryman.getName();
    }

    public Order setDeliveryman(Deliveryman deliveryman) {
        return new Order(customer, restaurant, deliveryman, foodList);
    }

    /**
     * Returns an immutable food map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<Food, Integer> getFoodList() {
        return Collections.unmodifiableMap(foodList);
    }

    public Name getRestaurant() {
        return restaurant.getName();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void completeOrder() {
        isCompleted = true;
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

        getFoodList().forEach((key, value) -> builder.append(String.format("%s x%d", key.getName(), value)));
        builder.append(" Delivery status: ").append(isCompleted());
        return builder.toString();
    }
}
