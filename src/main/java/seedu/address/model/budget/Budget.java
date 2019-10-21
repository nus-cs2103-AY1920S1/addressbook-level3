package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

import seedu.address.model.ExpenseList;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents an expense in the expense list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    // Identity fields
    private final Name name;

    // Data Fields
    private final Date startDate;
    private final Date endDate;
    private final Amount amount;
    private  Amount amountLeft;

    // Expense List
    private final ExpenseList expenseList;

    /**
     * Every field must be present and not null.
     */
    public Budget(Name name, Amount amount, Date startDate, Date endDate) {
        requireAllNonNull(name, amount, startDate, endDate);
        this.name = name;
        this.amount = amount;
        this.amountLeft = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenseList = new ExpenseList();
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public Amount getAmountLeft() {
        return amountLeft;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ExpenseList getExpenseList() { return expenseList; }

    public void addExpenseIntoBudget(Expense expense) {
        expenseList.addExpense(expense);
        deductAmountLeft(expense);
    }

    public boolean budgetHasExpense(Expense expense) {
        return expenseList.hasExpense(expense);
    }

    public boolean isDateWithinBudgetPeriod(Date date) {
        return !date.localDate.isBefore(startDate.localDate) && !date.localDate.isAfter(endDate.localDate);
    }

    public void deductAmountLeft(Expense expense) {
        double amountLeft = this.amountLeft.getValue();
        amountLeft -= expense.getAmount().getValue();
        this.amountLeft = new Amount("" + amountLeft);
    }


    /**
     * Returns true if both budgets have the same name.
     * This defines a weaker notion of equality between two budgets.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                       && otherBudget.getName().equals(getName());
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

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.getName().equals(getName())
                       && otherBudget.getAmount().equals(getAmount())
                       && otherBudget.getStartDate().equals(getStartDate())
                       && otherBudget.getEndDate().equals(getEndDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, startDate, endDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append(getName())
                .append(" ")
                .append(getAmount())
                .append("\n")
                .append(getStartDate())
                .append("\n")
                .append(getEndDate())
                .append("\n");
        return builder.toString();
    }
}
