package seedu.savenus.model.wallet;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wallet's CurrentBalance number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(String)}
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget numbers should only contain numbers and have either 0 or 2 decimal places.\n"
                    + "For example: 1.50 or 200";
    public static final String VALIDATION_REGEX = "\\d+(\\.\\d{2,2})?";

    private final FloatProperty budgetProperty;

    /**
     * Constructs a {@code CurrentBalance}.
     *
     * @param newBudgetString A valid CurrentBalance string.
     */
    public Budget(String newBudgetString) {
        requireNonNull(newBudgetString);
        checkArgument(isValidBudget(newBudgetString), MESSAGE_CONSTRAINTS);
        budgetProperty = new SimpleFloatProperty(Float.parseFloat(newBudgetString));
    }

    /**
     * Returns true if a given string is a valid CurrentBudget number.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public FloatProperty getBudgetProperty() {
        return budgetProperty;
    }

    public float getBudget() {
        return budgetProperty.get();
    }

    public void setBudget(Budget newBudget) {
        budgetProperty.setValue(newBudget.getBudget());
    }

    @Override
    public String toString() {
        return String.format("$%.02f", getBudget());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getBudget() == this.getBudget();
    }
}
