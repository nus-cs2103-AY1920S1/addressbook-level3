package seedu.deliverymans.model.order;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.deliverymans.model.addressbook.tag.Tag;
import seedu.deliverymans.model.food.Food;

/**
 * Represents an Order in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String orderName;

    // Identity fields
    private final String customer;
    private final String restaurant;
    private final String deliveryman;
    private final Set<Food> foods = new HashSet<>(); //implement food class

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a {@code Order}
     *
     * @param customer The customer who made the order.
     * @param restaurant The restaurant...
     */
    public Order(String orderName, String customer, String restaurant, String deliveryman) {
        // requireNonNull(orderName);
        // checkArgument(isValidOrderName(orderName), MESSAGE_CONSTRAINTS);
        this.orderName = orderName;
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryman = deliveryman;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public String getCustomer() {
        return customer;
    }

    public String getDeliveryman() {
        return deliveryman;
    }

    public String getFood() {
        StringBuilder sb = new StringBuilder();
        foods.forEach(sb::append);
        return sb.toString();
    }

    public String getRestaurant() {
        return restaurant;
    }

    public boolean isCompleted() {
        return false;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidOrderName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        // return other == this || (other instanceof Order && orderName.equals(((Order) other).orderName));

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
        return Objects.hash(customer, restaurant, deliveryman, foods);
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
