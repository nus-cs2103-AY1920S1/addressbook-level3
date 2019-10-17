package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the budget in the MoneyGoWhere list.
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Budget values should be positive number";
    public static final String VALIDATION_REGEX = "^[+]?([0-9]+(?:[.][0-9]*)?|\\.[0-9]+)$";

    private double value;

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value.
     */
    public Budget(double budget) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
    }

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value in String.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(budget);
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    public static boolean isValidBudget(double test) {
        return test >= 0.0;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && value == ((Budget) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("$%.2f", value);
    }
}
