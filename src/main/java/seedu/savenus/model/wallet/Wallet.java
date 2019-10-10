package seedu.savenus.model.wallet;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;

/**
 * Represents a user's Wallet in the application.
 * Guarantees: mutable;
 * JSON File should have wallet: { currentBalance: 0, daysToExpire: 0 } property
 */
public class Wallet {
    // Default Properties
    private Budget budget;
    private DaysToExpire daysToExpire;

    public Wallet() {
        this.budget = new Budget("0");
        this.daysToExpire = new DaysToExpire("0");
    }

    public Wallet(Budget budget, DaysToExpire daysToExpire) {
        this.budget = budget;
        this.daysToExpire = daysToExpire;
    }

    /**
     * Returns Budget property.
     */
    public FloatProperty getBudgetProperty() {
        return budget.getBudgetProperty();
    }


    /**
     * Return wallet's Budget
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * Returns budget amount.
     */
    public float getBudgetAmount() {
        return budget.getBudget();
    }

    /**
     * Set Budget with user's input.
     * @param newBudget New Budget created from user's input
     */
    public final void setBudget(Budget newBudget) {
        budget.setBudget(newBudget);
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
        return "Current Budget: " + this.getBudgetAmount() + "\n"
                + "Days to Expire: " + this.getNumberOfDaysToExpire();
    }
}
