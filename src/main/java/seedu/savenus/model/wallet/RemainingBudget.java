package seedu.savenus.model.wallet;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Wallet}'s {@code RemainingBudget} amount in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemainingBudget(String)}
 */
public class RemainingBudget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget amount should only contain numbers and have either 0 or 2 decimal places.\n"
                    + "For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{2,2})?";

    private final FloatProperty remainingBudgetProperty;

    /**
     * Constructs a {@code CurrentBalance}.
     *
     * @param newRemainingBudgetString A valid CurrentBalance string.
     */
    public RemainingBudget(String newRemainingBudgetString) {
        requireNonNull(newRemainingBudgetString);
        checkArgument(isValidRemainingBudget(newRemainingBudgetString), MESSAGE_CONSTRAINTS);
        remainingBudgetProperty = new SimpleFloatProperty(Float.parseFloat(newRemainingBudgetString));
    }

    /**
     * Returns true if a given string is a valid CurrentBudget number.
     */
    public static boolean isValidRemainingBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public FloatProperty getRemainingBudgetProperty() {
        return remainingBudgetProperty;
    }

    public float getRemainingBudget() {
        return remainingBudgetProperty.get();
    }

    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudgetProperty.setValue(newRemainingBudget.getRemainingBudget());
    }

    @Override
    public String toString() {
        return String.format("$%.02f", getRemainingBudget());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemainingBudget)) {
            return false;
        }

        RemainingBudget otherRemainingBudget = (RemainingBudget) other;
        return otherRemainingBudget.getRemainingBudget() == this.getRemainingBudget();
    }
}
