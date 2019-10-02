package seedu.deliverymans.model;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order of food.
 */
public class Order {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String orderName;
    public final Set<Food> foods = new HashSet<>();   //implement food class

    /**
     * Constructs a {@code Order}
     *
     * @param orderName A valid order name.
     */
    public Order(String orderName) {
        requireNonNull(orderName);
        checkArgument(isValidOrderName(orderName), MESSAGE_CONSTRAINTS);
        this.orderName = orderName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidOrderName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Order && orderName.equals(((Order) other).orderName));
    }

    @Override
    public int hashCode() {
        return orderName.hashCode();
    }

    public String toString() {
        return '[' + orderName + ']';
    }
}
