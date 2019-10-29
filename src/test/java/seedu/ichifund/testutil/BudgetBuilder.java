package seedu.ichifund.testutil;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_DESCRIPTION = "Saving for my future";
    public static final String DEFAULT_AMOUNT = "13.37";

    private Description description;
    private Amount amount;
    private Month month;
    private Year year;
    private Category category;

    public BudgetBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        amount = new Amount(DEFAULT_AMOUNT);
        month = null;
        year = null;
        category = null;
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        description = budgetToCopy.getDescription();
        amount = budgetToCopy.getAmount();
        month = budgetToCopy.getMonth();
        year = budgetToCopy.getYear();
        category = budgetToCopy.getCategory();
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

    /**
     * Sets the {@code Month} and {@code Year} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withMonthAndYear(String month, String year) {
        this.month = new Month(month);
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Budget build() {
        return new Budget(description, amount, month, year, category);
    }

}
