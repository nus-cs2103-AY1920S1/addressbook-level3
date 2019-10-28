package seedu.address.model.person;

import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Budget.
 */
public class Budget extends Entry {

    private static final String ENTRY_TYPE = "Budget";

    private Amount spent;
    private final Period period;
    private final Date startDate;
    private final Date endDate;


    /*
    Date - start date
    Period - time period for which budget is effective
    private end date = start date + period

    loop through income/ expense list
    - if income/budget is in same cat as budget && date is within start date - end date
    - budget add/minus income/budget

    LocalDate functions - plusDays(long daysToAdd)
                        - plusWeeks(long weeksToAdd)
                        - plusMonths(long monthsToAdd)
                        - plusYears(long yearsToAdd)

     Parser e.g. 10d/ 2m/ 1y
     */
    public Budget(Category cat, Description desc, Date startDate, Period period, Amount amount, Set<Tag> tags) {
        super(cat, desc, startDate, amount, tags);
        this.startDate = startDate;
        this.period = period;
        endDate = startDate.plus(period);
        spent = new Amount(0);
    }

    /**
     * Returns amount spent out of the budget.
     *
     * TODO: display on UI side panel with amount allocated to budget
     *
     * @return amount Amount spent (calculated from expenses)
     */
    public Amount getSpent() {
        return spent;
    }

    public String getType() {
        return this.ENTRY_TYPE;
    }

    public Period getPeriod() {
        return period;
    }

    public void setSpent(FilteredList<Expense> filteredExpenses, FilteredList<Income> filteredIncomes) {
        double spentAmount = 0;
        ExpenseMatchesBudgetPredicate expenseMatchesBudgetPredicate = new ExpenseMatchesBudgetPredicate(getCategory(), startDate, endDate);
        IncomeMatchesBudgetPredicate incomeMatchesBudgetPredicate = new IncomeMatchesBudgetPredicate(getCategory(), startDate, endDate);
        filteredExpenses.setPredicate(expenseMatchesBudgetPredicate);
        filteredIncomes.setPredicate(incomeMatchesBudgetPredicate);
        for (Expense expense : filteredExpenses) {
            spentAmount += expense.getAmount().value;
        }
        for (Income income : filteredIncomes) {
            spentAmount -= income.getAmount().value;
        }

        spent = new Amount(spentAmount);
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
        builder.append(ENTRY_TYPE + ": ")
                .append(" | Category: ")
                .append(getCategory())
                .append(" Description: ")
                .append(getDesc())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("(" + getDate() + ")");
        return builder.toString();
    }
}
