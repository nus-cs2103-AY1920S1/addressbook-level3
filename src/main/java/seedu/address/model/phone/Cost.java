package seedu.address.model.phone;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's cost in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidCost(double)}
 */
public class Cost {

    public static final String MESSAGE_CONSTRAINTS = "Costs must be non-negative.";

    public final double value;

    /**
     * Constructs a {@code Cost}.
     *
     * @param cost A valid cost.
     */
    public Cost(double cost) {
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        value = cost;
    }

    /**
     * Returns true if a given double is a valid cost.
     */
    public static boolean isValidCost(double cost) {
        return cost >= 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cost // instanceof handles nulls
                && value == ((Cost) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
