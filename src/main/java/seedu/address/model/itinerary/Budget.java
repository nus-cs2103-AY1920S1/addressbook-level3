package seedu.address.model.itinerary;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.currency.CustomisedCurrency;

/**
 * Generic abstraction of budget.
 */
public class Budget {
    public static final String MESSAGE_CONSTRAINTS = "Budget can take any positive numerical value with"
            + " no more than 2 decimal places, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "^[+]?[0-9]+([.][0-9]{1,2})?$";

    private final Double value;

    /**
     * Constructs an {@code Budget}.
     *
     * @param value A valid budget amount.
     */
    public Budget(String value) {
        requireNonNull(value);
        checkArgument(isValidBudget(value), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(value);
    }

    public Budget(double value) {
        this.value = (double) Math.round(value * 100) / 100;
    }

    /**
     * Returns true if a given string is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getValue() {
        return this.value;
    }

    public String getValueStringInCurrency(CustomisedCurrency customisedCurrency) {
        return customisedCurrency.getSymbol() + String.format("%.2f", (
                customisedCurrency.getRate().getValue() * value));
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && value.equals(((Budget) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
