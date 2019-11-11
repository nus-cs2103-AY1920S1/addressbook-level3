package seedu.address.testutil;

import seedu.address.model.budget.Budget;
import seedu.address.model.BudgetList;

/**
 * A utility class to help with building BudgetList objects.
 * Example usage: <br>
 *     {@code BudgetList ab = new BudgetListBuilder().withBudget("Coffee", "Tea").build();}
 */
public class BudgetListBuilder {

    private BudgetList budgetList;

    public BudgetListBuilder() {
        budgetList = new BudgetList();
    }

    public BudgetListBuilder(BudgetList budgetList) {
        this.budgetList = budgetList;
    }

    /**
     * Adds a new {@code Budget} to the {@code BudgetList} that we are building.
     */
    public BudgetListBuilder withBudget(Budget budget) {
        budgetList.addBudget(budget);
        return this;
    }

    public BudgetList build() {
        return budgetList;
    }
}
