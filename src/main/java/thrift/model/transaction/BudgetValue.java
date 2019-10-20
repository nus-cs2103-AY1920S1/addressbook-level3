package thrift.model.transaction;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.AppUtil.checkArgument;
import static thrift.model.transaction.Value.DECIMAL_FORMATTER;

/**
 * Represents a Budget's monetary value in the Budgets list.
 * Guarantees: immutable; is valid as declared in {@link #isValidValue(String)}
 */
public class BudgetValue {

    public static final String VALUE_CONSTRAINTS =
            "Value should only contain non-negative numbers and an optional decimal point, which if specified, "
                    + "accepts up to 2 decimal digits.\nValue should also be lesser than 1 billion.";
    public static final String VALIDATION_REGEX = "^[0-9]\\d{0,8}(\\.\\d{0,2})?$";

    private Double amount;

    /**
     * Constructs a {@code BudgetValue} that allows $0.
     *
     * @param value Monetary cost describing the value.
     */
    public BudgetValue(String value) {
        requireNonNull(value);
        checkArgument(isValidValue(value), VALUE_CONSTRAINTS);
        this.amount = Double.parseDouble(value);
    }

    /**
     * Returns true if a given String is a valid monetary value.
     *
     * @return true if amount is a valid double.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns this object's value in double.
     */
    public Double getMonetaryValue() {
        return this.amount;
    }

    /**
     * Returns the value from {@link #getMonetaryValue()} in String type. This is useful for formatting to JSON and
     * storing.
     */
    public String getUnformattedString() {
        return String.format("%.2f", getMonetaryValue());
    }

    @Override
    public String toString() {
        return String.valueOf(DECIMAL_FORMATTER.format(amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetValue // instanceof handles nulls
                && amount.equals(((BudgetValue) other).amount)); // state check
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }
}
