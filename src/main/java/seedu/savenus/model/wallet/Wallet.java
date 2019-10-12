package seedu.savenus.model.wallet;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

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
        this.remainingBudget = new RemainingBudget("0");
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
     * Returns {@code remainingBudget}'s {@code DoubleProperty}.
     */
    public DoubleProperty getRemainingBudgetProperty() {
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
    public double getRemainingBudgetAmount() {
        return remainingBudget.getRemainingBudget();
    }

    /**
     * Set {@code remainingBudget} with user's input.
     * @param newRemainingBudget New {@code RemainingBudget} created from user's input
     */
    public final void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudget.setRemainingBudget(newRemainingBudget);
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

    @Override
    public String toString() {
        return "Current Budget: " + this.getRemainingBudgetAmount() + "\n"
                + "Days to Expire: " + this.getNumberOfDaysToExpire();
    }
}
