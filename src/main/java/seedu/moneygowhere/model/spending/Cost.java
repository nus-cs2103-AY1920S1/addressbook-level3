package seedu.moneygowhere.model.spending;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

/**
 * Represents a Spending's cost in the MoneyGoWhere list.
 * Guarantees: immutable; is valid as declared in {@link #isValidCost(String)}
 */
public class Cost implements Comparable<Cost> {

    public static final String MESSAGE_CONSTRAINTS = "Cost must not have any leading symbols, "
            + " must be a positive number with at most 2 decimal places, "
            + "and it should not be blank. The maximum cost allowed is $1 billion (1000000000).";

    /*
     * The first character of the cost must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Cost must be a number with at most 2 decimal places.
     */
    public static final String VALIDATION_REGEX = "[0-9]+([.][0-9]{1,2})?";

    public final String value;

    /**
     * Constructs an {@code Cost}.
     *
     * @param cost A valid cost.
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        value = String.format("%.2f", Double.parseDouble(cost));
    }

    /**
     * Returns true if a given string is a valid cost.
     */
    public static boolean isValidCost(String test) {
        return test.matches(VALIDATION_REGEX) && Double.parseDouble(test) <= 1000000000 && Double.parseDouble(test) > 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cost // instanceof handles nulls
                && value.equals(((Cost) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Cost other) {
        return Double.compare(
                Double.parseDouble(value),
                Double.parseDouble(other.value)
        );
    }

}
