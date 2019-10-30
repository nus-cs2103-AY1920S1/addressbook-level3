package seedu.savenus.model.wallet;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.savenus.model.util.Money;

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

    private final ObjectProperty<Money> remainingBudgetProperty;

    /**
     * Constructs a {@code RemainingBudget}.
     * Requires check whether budget amount is invalid (Less than 0 or more than 1 million dollars).
     * @param newRemainingBudgetString A valid {@code RemainingBudget} string.
     */
    public RemainingBudget(String newRemainingBudgetString) {
        requireNonNull(newRemainingBudgetString);
        checkArgument(isValidRemainingBudget(newRemainingBudgetString), MESSAGE_CONSTRAINTS);
        remainingBudgetProperty = new SimpleObjectProperty<>(new Money(newRemainingBudgetString));
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
        return getRemainingBudgetAmount().compareTo(new BigDecimal(1000000.00)) == 1;
    }

    /**
     * Returns the {@code StringProperty} of this instance.
     */
    public ObjectProperty<Money> getRemainingBudgetProperty() {
        return remainingBudgetProperty;
    }

    /**
     * Returns the {@code Money} value of this instance.
     */
    public BigDecimal getRemainingBudgetAmount() {
        return remainingBudgetProperty.get().getAmount();
    }

    /**
     * Set new user's {@code RemainingBudget}.
     */
    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudgetProperty.setValue(new Money(newRemainingBudget.getRemainingBudgetAmount()));
    }

    @Override
    public String toString() {
        return String.format("$%.02f", getRemainingBudgetAmount());
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
        return otherRemainingBudget.getRemainingBudgetAmount().compareTo(this.getRemainingBudgetAmount()) == 0;
    }
}
