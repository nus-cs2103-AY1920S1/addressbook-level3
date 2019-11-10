package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.ObjectProperty;
import seedu.savenus.model.savings.exceptions.SavingsOutOfBoundException;
import seedu.savenus.model.util.Money;

//@@author fatclarence
/**
 * Represents a user's {@code: SavingsAccount} in the application.
 */
public class SavingsAccount implements ReadOnlySavingsAccount {

    private CurrentSavings currentSavings;

    /**
     * Default constructor when app initialises without an existing readable SavingsAccount file.
     */
    public SavingsAccount() {
        this.currentSavings = new CurrentSavings(new Money("0.00"));
    }

    /**
     * Overloaded constructor that sets {@code CurrentSavings} with the provided currentSavings.
     */
    public SavingsAccount(CurrentSavings currSavings) {
        this.currentSavings = currSavings;
    }

    /**
     * Overloaded constructor that resets the account data with the provided data to be copied.
     */
    public SavingsAccount(ReadOnlySavingsAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code SavingsAccount} with {@code toBeCopied}.
     * @param toBeCopied
     */
    private void resetData(ReadOnlySavingsAccount toBeCopied) {
        requireNonNull(toBeCopied);

        // Get money value from the Savings Account to be copied
        Money toCopy = toBeCopied.getCurrentSavings().get();
        // Overwrite current savings value with value toCopy
        currentSavings.setCurrentSavings(toCopy);
    }

    /**
     * Check whether adding a saving amount will make the value of current savings > 1000000
     * @param toAdd savings to be added into current savings
     * @param currentSavings savings in the bank account
     * @return true if the new current savings amount will be greater than 1000000
     */
    public static boolean testOutOfBound(Savings toAdd, CurrentSavings currentSavings) {
        Money amountToAdd = toAdd.getSavingsAmount();
        Money currentSavingsMoney = new Money(currentSavings.getCurrentSavingsMoney().getAmount());
        Money newCurrentSavings = currentSavingsMoney.add(amountToAdd);
        return newCurrentSavings.isOutOfBounds();
    }

    /**
     * Get the current savings from this account
     */
    public CurrentSavings retrieveCurrentSavings() {
        return this.currentSavings;
    }

    /**
     * Add to the current savings.
     */
    public void addToSavings(Savings savings) throws SavingsOutOfBoundException {
        Money amountToAdd = savings.getSavingsAmount();
        Money currentSavingsMoney = this.currentSavings.getCurrentSavingsMoney();
        Money newCurrentSavings = currentSavingsMoney.add(amountToAdd);
        if (newCurrentSavings.isOutOfBounds()) {
            throw new SavingsOutOfBoundException();
        } else {
            this.currentSavings.setCurrentSavings(newCurrentSavings);
        }
    }

    /**
     * Deduct money from the current savings
     */
    public void deductFromSavings(Savings savings) {
        Money toSubtract = savings.getSavingsAmount();
        Money currentSavingsMoney = this.currentSavings.getCurrentSavingsMoney();
        currentSavings.setCurrentSavings(currentSavingsMoney.add(toSubtract));
    }

    /**
     * Get the current savings amount in the savings account
     * @return Unmodifiable Current Savings Amount
     */
    @Override
    public ObjectProperty<Money> getCurrentSavings() {
        return this.currentSavings.getCurrentSavingsProperty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SavingsAccount
                && currentSavings.equals(((SavingsAccount) other).currentSavings));
    }
}
