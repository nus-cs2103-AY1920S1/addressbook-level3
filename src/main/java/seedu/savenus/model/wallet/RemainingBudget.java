package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a {@code Wallet}'s {@code RemainingBudget} amount in the application.
 */
public class RemainingBudget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget Amount should only contain numbers and have either 0 or 2 decimal places";
    public static final String FLOATING_POINT_CONSTRAINTS =
            "Due to Floating Point limitations, "
            + "this application will not accept Budget Amounts higher than 1 million dollars";
    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";

    private final StringProperty remainingBudgetProperty;

    /**
     * Constructs a {@code RemainingBudget}.
     * Requires check whether budget amount is invalid (Less than 0 or more than 1 million dollars).
     * @param newRemainingBudgetString A valid {@code RemainingBudget} string.
     */
    public RemainingBudget(String newRemainingBudgetString) {
        requireNonNull(newRemainingBudgetString);
        checkArgument(isValidRemainingBudget(newRemainingBudgetString), MESSAGE_CONSTRAINTS);
        if (!newRemainingBudgetString.contains(".")) {
            newRemainingBudgetString += ".00";
        }
        remainingBudgetProperty = new SimpleStringProperty("$" + newRemainingBudgetString);
    }

    /**
     * Returns true if a given string is a valid {@code RemainingBudget}.
     */
    public static boolean isValidRemainingBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if this instance of {@code RemainingBudget} is out of bounds.
     */
    public boolean isOutOfBounds() {
        return getRemainingBudget().compareTo(new BigDecimal(1000000.00)) == 1;
    }

    /**
     * Returns the {@code StringProperty} of this instance.
     */
    public StringProperty getRemainingBudgetProperty() {
        return remainingBudgetProperty;
    }

    /**
     * Returns the {@code double} value of this instance.
     */
    public BigDecimal getRemainingBudget() {
        return new BigDecimal(remainingBudgetProperty.get().substring(1));
    }

    /**
     * Set new user's {@code RemainingBudget}.
     */
    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudgetProperty.setValue("$" + newRemainingBudget.getRemainingBudget().toString());
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
        return otherRemainingBudget.getRemainingBudget().compareTo(this.getRemainingBudget()) == 0;
    }
}
