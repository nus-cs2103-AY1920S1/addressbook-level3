package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;


/**
 * Represents a {@code Wallet}'s {@code RemainingBudget} amount in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemainingBudget(String)}
 */
public class RemainingBudget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget amount should only contain numbers and have either 0 or 2 decimal places.\n"
                    + "For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "((0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

    private final FloatProperty remainingBudgetProperty;

    /**
     * Constructs a {@code RemainingBudget}.
     *
     * @param newRemainingBudgetString A valid {@code RemainingBudget} string.
     */
    public RemainingBudget(String newRemainingBudgetString) {
        requireNonNull(newRemainingBudgetString);
        checkArgument(isValidRemainingBudget(newRemainingBudgetString), MESSAGE_CONSTRAINTS);
        remainingBudgetProperty = new SimpleFloatProperty(Float.parseFloat(newRemainingBudgetString));
    }

    /**
     * Returns true if a given string is a valid {@code RemainingBudget}.
     */
    public static boolean isValidRemainingBudget(String test) {
        if (test.equals("0")) {
            return true;
        } else {
            return test.matches(VALIDATION_REGEX);
        }
    }

    /**
     * Returns the {@code FloatProperty} of this instance.
     */
    public FloatProperty getRemainingBudgetProperty() {
        return remainingBudgetProperty;
    }

    /**
     * Returns the {@code float} value of this instance.
     */
    public float getRemainingBudget() {
        return remainingBudgetProperty.get();
    }

    /**
     */
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
