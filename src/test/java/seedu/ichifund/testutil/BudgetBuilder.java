package seedu.ichifund.testutil;

import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.budget.Budget;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_DESCRIPTION = "Saving for my future";
    public static final String DEFAULT_AMOUNT = "13.37";

    private Description description;
    private Amount amount;

    public BudgetBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        description = budgetToCopy.getDescription();
        amount = budgetToCopy.getAmount();
    }

    /**
     * Sets the {@code Description} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public Budget build() {
        return new Budget(description, amount);
    }

}
