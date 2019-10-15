package seedu.savenus.model.wallet;

import java.math.BigDecimal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.food.Price;


/**
 * Represents a user's Wallet in the application.
 * Guarantees: mutable;
 * JSON File should have wallet: { remainingBudget: 0, daysToExpire: 0 } property
 */
public class Wallet {
    private RemainingBudget remainingBudget;
    private DaysToExpire daysToExpire;

    /**
     * Default constructor that sets {@code remainingBudget} and {@code daysToExpire} to both 0.
     */
    public Wallet() {
        this.remainingBudget = new RemainingBudget("0.00");
        this.daysToExpire = new DaysToExpire("0");
    }

    /**
     * Overloaded constructor that sets {@code remainingBudget} and {@code daysToExpire} according to input arguments.
     */
    public Wallet(String remainingBudgetStr, String daysToExpireStr) {
        this.remainingBudget = new RemainingBudget(remainingBudgetStr);
        this.daysToExpire = new DaysToExpire(daysToExpireStr);
    }

    /**
     * Overloaded constructor that sets {@code remainingBudget} and {@code daysToExpire} according to input arguments.
     */
    public Wallet(RemainingBudget remainingBudget, DaysToExpire daysToExpire) {
        this.remainingBudget = remainingBudget;
        this.daysToExpire = daysToExpire;
    }

    /**
     * Returns {@code remainingBudget}'s {@code StringProperty}.
     */
    public StringProperty getRemainingBudgetProperty() {
        return remainingBudget.getRemainingBudgetProperty();
    }

    /**
     * Returns the {@code wallet}'s {@code remainingBudget}.
     */
    public RemainingBudget getRemainingBudget() {
        return remainingBudget;
    }

    /**
     * Returns {@code remainingBudget}'s {@code double} value.
     */
    public BigDecimal getRemainingBudgetAmount() {
        return remainingBudget.getRemainingBudget();
    }

    /**
     * Set {@code remainingBudget} with user's input.
     * @param newRemainingBudget New {@code RemainingBudget} created from user's input
     */
    public void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudget.setRemainingBudget(newRemainingBudget);
    }

    /**
     * Overloaded method to allow setting with budget amount string.
     * @param newRemainingBudgetString New {@code RemainingBudget} string
     */
    public void setRemainingBudget(String newRemainingBudgetString) {
        setRemainingBudget(new RemainingBudget(newRemainingBudgetString));
    }

    /**
     * Returns {@code DaysToExpire}'s IntegerProperty.
     */
    public IntegerProperty getDaysToExpireProperty() {
        return daysToExpire.getDaysToExpireProperty();
    }

    /**
     * Returns the {@code wallet}'s {@code daysToExpire}.
     */
    public DaysToExpire getDaysToExpire() {
        return daysToExpire;
    }

    /**
     * Returns {@code daysToExpire}'s {@code int} value.
     */
    public int getNumberOfDaysToExpire() {
        return daysToExpire.getDaysToExpire();
    }

    /**
     * Set {@code daysToExpire} with user's input.
     * @param newDaysToExpire New {@code DaysToExpire} created from user's input
     */
    public final void setDaysToExpire(DaysToExpire newDaysToExpire) {
        daysToExpire.setDaysToExpire(newDaysToExpire);
    }

    /**
     * Update number of days left with respect to current time.
     */
    public void updateDaysToExpire() {
        daysToExpire.updateDaysToExpire();
    }

    /**
     * Pay the input {@code Price} out of wallet.
     * @param price {@code Price} to be deducted
     * @throws CommandException Throws {@code CommandException} if there are insufficient funds in user's {@code Wallet}
     */
    public void pay(Price price) throws CommandException {
        // Check whether wallet has enough funds
        if (new BigDecimal(price.toString()).compareTo(getRemainingBudgetAmount()) == 1) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_FUNDS);
        } else {
            BigDecimal remainingBudgetInCents = getRemainingBudgetAmount().subtract(new BigDecimal(price.toString()));
            setRemainingBudget(remainingBudgetInCents.toString());
        }
    }

    @Override
    public String toString() {
        return "Current Budget: " + this.getRemainingBudgetAmount() + "\n"
                + "Days to Expire: " + this.getNumberOfDaysToExpire();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Wallet // instanceof handles nulls
                && getRemainingBudget().equals(((Wallet) other).getRemainingBudget()) // state check
                && getDaysToExpire().equals(((Wallet) other).getDaysToExpire())); // state check
    }
}
