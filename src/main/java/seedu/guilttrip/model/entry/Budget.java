package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.guilttrip.model.tag.Tag;

/**
 * Represents a Budget.
 */
public class Budget extends Entry {
    private static final String ENTRY_TYPE = "Budget";

    private BudgetAmount spent;
    private final Period period;
    private final Date startDate;
    private final Date endDate;
    private FilteredList<Expense> filteredExpenses;
    private ExpenseMatchesBudgetPredicate expenseMatchesBudgetPredicate;

    public Budget(Category cat, Description desc, Date startDate, Period period, Amount amount, Set<Tag> tags) {
        super(cat, desc, startDate, amount, tags);
        this.startDate = startDate;
        this.period = period;
        endDate = startDate.plus(period);
        spent = new BudgetAmount("0");
        new Budget(cat, desc, startDate, period, amount, tags, spent);
    }

    public Budget(Category cat, Description desc, Date startDate, Period period, Amount amount,
                  Set<Tag> tags, BudgetAmount spent) {
        super(cat, desc, startDate, amount, tags);
        this.startDate = startDate;
        this.period = period;
        endDate = startDate.plus(period);
        this.spent = spent;
    }

    /**
     * Returns amount spent out of the budget.
     * @return amount Amount spent (calculated from expenses)
     */
    public BudgetAmount getSpent() {
        return spent;
    }

    public String getType() {
        return ENTRY_TYPE;
    }

    public Period getPeriod() {
        return period;
    }

    public Date getEndDate() {
        return endDate;
    }

    public FilteredList<Expense> getFilteredExpenses() {
        return filteredExpenses;
    }

    public void setSpentAmount(BudgetAmount amount) {
        requireNonNull(amount);
        this.spent = amount;
    }

    public void setSpent(FilteredList<Expense> filteredExpenses) {
        this.filteredExpenses = new FilteredList<>(filteredExpenses);
        expenseMatchesBudgetPredicate = new ExpenseMatchesBudgetPredicate(getCategory(), startDate, endDate);
        this.filteredExpenses.setPredicate(expenseMatchesBudgetPredicate);
    }

    /**
     * Updates the amount spent for a Budget
     */
    public Budget updateSpent() {
        double spentAmount = 0;
        for (Expense expense : filteredExpenses) {
            spentAmount += expense.getAmount().value;
        }
        spent = new BudgetAmount(spentAmount);
        return new Budget(getCategory(), getDesc(), getDate(), period, getAmount(), getTags(), spent);
    }

    /**
     * Returns a new Budget if and only if it's category is edited.
     */
    public Budget modifiedBudget(String newName) {
        Category newCategory = new Category(newName, super.getCategory().getCategoryType());
        return new Budget(newCategory, super.getDesc(), super.getDate(), this.getPeriod(),
                this.getAmount(), super.getTags(), this.spent);
    }

    /**
     * Returns true if both entries of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two entries.
     */
    public boolean isSameBudget(Budget otherEntry) {
        if (otherEntry == this) {
            return true;
        }

        return otherEntry != null
                && otherEntry.getCategory().equals(getCategory())
                && otherEntry.getDesc().equals(getDesc())
                && otherEntry.getAmount().equals(getAmount())
                && otherEntry.getTags().equals(getTags())
                && otherEntry.getDate().equals(getDate())
                && otherEntry.getPeriod().equals(getPeriod());
    }

    /**
     * Returns true if both budgets have the same data fields.
     * This defines a stronger notion of equality between two entries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getCategory().equals(getCategory())
                && otherBudget.getDesc().equals(getDesc())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getTags().equals(getTags())
                && otherBudget.getDate().equals(getDate());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Category: ")
                .append(getCategory())
                .append(" | Description: ")
                .append(getDesc())
                .append(" | Amount: ")
                .append(getAmount())
                .append(" | Tags: ");
        getTags().forEach(builder::append);
        builder.append(" (" + getDate() + ")");
        return builder.toString();
    }
}
