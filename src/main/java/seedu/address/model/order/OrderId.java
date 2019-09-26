package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's ID in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderId(String)}
 */
public class OrderId {

    public static final String MESSAGE_CONSTRAINTS = "Order ID can take any unique values, and it should not be blank";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String id;

    /**
     * Constructs an {@code ID}.
     *
     * @param orderId A valid order ID.
     */
    public OrderId(String orderId) {
        requireNonNull(orderId);
        checkArgument(isValidOrderId(orderId), MESSAGE_CONSTRAINTS);
        id = orderId;
    }

    /**
     * Returns true if a given string is a valid order ID.
     */
    public static boolean isValidOrderId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderId // instanceof handles nulls
                && id.equals(((OrderId) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
