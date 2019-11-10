package seedu.planner.model.field;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

//@@author KxxMxxx
/**
 * Represents a cost.
 */
public class Cost {
    public static final String MESSAGE_CONSTRAINTS =
            "Costs should only be a positive integer to the nearest 2 decimal places. They should also not be blank.";

    /*
     *
     */
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{2})?";

    public final String cost;

    /**
     * Constructs a {@code Cost}.
     *
     * @param cost A valid cost.
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        this.cost = cost;
    }

    /**
     * Returns true if a given string is a valid cost.
     */
    public static boolean isValidCost(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the double value of a valid cost.
     */
    public double getCost() {
        return Double.parseDouble(cost);
    }

    @Override
    public String toString() {
        return cost;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cost // instanceof handles nulls
                && cost.equals(((Cost) other).cost)); // state check
    }

    @Override
    public int hashCode() {
        return cost.hashCode();
    }
}
