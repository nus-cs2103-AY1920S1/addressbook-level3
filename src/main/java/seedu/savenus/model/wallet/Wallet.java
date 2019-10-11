package seedu.savenus.model.wallet;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;

/**
 * Represents a user's Wallet in the application.
 * Guarantees: mutable;
 * JSON File should have wallet: { remainingBudget: 0, daysToExpire: 0 } property
 */
public class Wallet {
    // Default Properties
    private RemainingBudget remainingBudget;
    private DaysToExpire daysToExpire;

    public Wallet() {
        this.remainingBudget = new RemainingBudget("0");
        this.daysToExpire = new DaysToExpire("0");
    }

    public Wallet(RemainingBudget remainingBudget, DaysToExpire daysToExpire) {
        this.remainingBudget = remainingBudget;
        this.daysToExpire = daysToExpire;
    }

    /**
     * Returns Budget property.
     */
    public FloatProperty getRemainingBudgetProperty() {
        return remainingBudget.getRemainingBudgetProperty();
    }


    /**
     * Return wallet's remaining budget
     */
    public RemainingBudget getRemainingBudget() {
        return remainingBudget;
    }

    /**
     * Returns remaining budget amount.
     */
    public float getRemainingBudgetAmount() {
        return remainingBudget.getRemainingBudget();
    }

    /**
     * Set RemainingBudget with user's input.
     * @param newRemainingBudget New Budget created from user's input
     */
    public final void setRemainingBudget(RemainingBudget newRemainingBudget) {
        remainingBudget.setRemainingBudget(newRemainingBudget);
    }

    /**
     * Returns DaysToExpire property.
     */
    public IntegerProperty getDaysToExpireProperty() {
        return daysToExpire.getDaysToExpireProperty();
    }

    /**
     * Return wallet's DaysToExpire
     */
    public DaysToExpire getDaysToExpire() {
        return daysToExpire;
    }

    /**
     * Returns number of days to expire.
     */
    public int getNumberOfDaysToExpire() {
        return daysToExpire.getDaysToExpire();
    }

    /**
     * Set DaysToExpire with user's input.
     * @param newDaysToExpire New DaysToExpire created from user's input
     */
    public final void setDaysToExpire(DaysToExpire newDaysToExpire) {
        daysToExpire.setDaysToExpire(newDaysToExpire);
    }

    @Override
    public String toString() {
        return "Current Budget: " + this.getRemainingBudgetAmount() + "\n"
                + "Days to Expire: " + this.getNumberOfDaysToExpire();
    }
}
