package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.savenus.model.util.Money;

/**
 * Represents the {@code: CurrentSavings} amount within the user's {@code: SavingsAccount}.
 */
public class CurrentSavings {
    public static final String MESSAGE_CONSTRAINTS =
            "Current Savings can only have 0 or 2 decimal places!";
    public static final String FLOATING_POINT_CONSTRAINTS =
            "Due to limitations of Floating Point, we are unable to allow your savings to exceed $1,000,000";


    public static final String VALIDATION_REGEX = "(0|(0(\\.\\d{2,2}))|[1-9]+(\\d*(\\.\\d{2,2})?))";


    private final ObjectProperty<Money> currentSavingsProperty;

    /**
     * Constructor that initializes the money value of Current Savings.
     * Construction of {@code CurrentSavings}.
     */
    public CurrentSavings(Money currSavings) {
        requireNonNull(currSavings);
        currentSavingsProperty = new SimpleObjectProperty<>(currSavings);
    }

    /**
     * Checks the validity of a current saving amount.
     */
    public static boolean isValidCurrentSaving(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *
     * @return ObjectProperty of Money
     */
    ObjectProperty<Money> getCurrentSavingsProperty() {
        return currentSavingsProperty;
    }

    /**
     * @return Money object of Current Savings.
     */
    Money getCurrentSavingsMoney() {
        return currentSavingsProperty.get();
    }

    /**
     * Set current savings to a new Money value.
     * @param newAmount
     */
    void setCurrentSavings(Money newAmount) {
        currentSavingsProperty.setValue(newAmount);
    }

    /**
     *
     * @return A string in decimal form (with 2 dp)
     */
    @Override
    public String toString() {
        return currentSavingsProperty.get().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CurrentSavings
                && this.getCurrentSavingsMoney().equals(((CurrentSavings) other).getCurrentSavingsMoney()));
    }
}
