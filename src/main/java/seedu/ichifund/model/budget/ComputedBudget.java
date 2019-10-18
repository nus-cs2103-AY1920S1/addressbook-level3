package seedu.ichifund.model.budget;

import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Represents a Budget that has been computed in the fund book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ComputedBudget extends Budget {

    // Identity fields
    private final Amount spending;

    /**
     * Description, amount, and spending must be present and not null.
     */
    public ComputedBudget(Description description, Amount amount, Month month,
                          Year year, Category category, Amount spending) {
        super(description, amount, month, year, category);
        requireAllNonNull(spending);
        this.spending = spending;
    }

    public ComputedBudget(Budget budget, Amount spending) {
        super(budget.getDescription(), budget.getAmount(), budget.getMonth(),
                budget.getYear(), budget.getCategory());
        requireAllNonNull(spending);
        this.spending = spending;
    }

    public Amount getSpending() {
        return spending;
    }

    /**
     * Returns true if both budgets have the same description and spending.
     * This defines a weaker notion of equality between two computed budgets.
     */
    public boolean isSameComputedBudget(ComputedBudget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null && otherBudget.isSameBudget(otherBudget)
                && otherBudget.getSpending().equals(getSpending());
    }

    /**
     * Returns true if both budgets have the same identity and data fields.
     * This defines a stronger notion of equality between two budgets.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ComputedBudget)) {
            return false;
        }

        ComputedBudget otherBudget = (ComputedBudget) other;
        return super.equals(otherBudget) && otherBudget.getSpending().equals(getSpending());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getDescription(), getAmount(), getMonth(), getYear(), getCategory(), getSpending());
    }

    @Override
    public String toString() {
        return super.toString() + " Spending: " + getSpending();
    }

}
