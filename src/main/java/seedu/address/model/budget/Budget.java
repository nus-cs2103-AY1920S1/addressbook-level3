package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;

/**
 * Represents an expense in the expense list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    // Identity fields
    private final Name name;
    private final Currency currency;

    // Data Fields
    private final Date startDate;
    private final Date endDate;
    private final Amount amount;
    // Expense List
    private final ExpenseList expenseList;
    private Amount amountLeft;
    private boolean budgetAmountPositive;

    /**
     * Every field must be present and not null.
     */
    public Budget(Name name, Amount amount, Amount amountLeft, Currency currency, Date startDate, Date endDate,
                  ExpenseList expenseList) {
        requireAllNonNull(name, amount, startDate, endDate);
        this.name = name;
        this.currency = currency;
        this.amount = amount;
        this.amountLeft = amountLeft;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenseList = expenseList;

        recalculateAmountLeft();
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

    public Currency getCurrency() {
        return currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public ObservableList<Expense> getObservableExpenseList() {
        return expenseList.getExpenseList();
    }

    public void setExpenseListInBudget(ReadOnlyExpenseList expenseList) {
        this.expenseList.resetData(expenseList);
        recalculateAmountLeft();
    }

    /**
     * Adds an expense into the expenselist inside the budget.
     *
     * @param expense an expense to be added into a budget.
     */
    public void addExpenseIntoBudget(Expense expense) {
        expenseList.addExpense(expense);
        recalculateAmountLeft();
    }

    /**
     * @param expense target expense in the budget
     */
    public void deleteExpenseInBudget(Expense expense) {
        expenseList.removeExpense(expense);
        recalculateAmountLeft();
    }

    public boolean budgetHasExpense(Expense expense) {
        return expenseList.hasExpense(expense);
    }

    public boolean isBudgetPositive() {
        return this.budgetAmountPositive;
    }

    /**
     * Checks whether a given date falls within any budget period.
     *
     * @param date a valid date.
     * @return a boolean value.
     */
    public boolean isDateWithinBudgetPeriod(Date date) {
        return !date.localDate.isBefore(startDate.localDate) && !date.localDate.isAfter(endDate.localDate);
    }

    /**
     * Checks if a budget overlaps with the current instance of the budget.
     * @param otherBudget Budget to compare with
     * @return a boolean value.
     */
    public boolean doesOtherBudgetOverlap(Budget otherBudget) {
        return otherBudget.getStartDate().localDate.isBefore(this.startDate.localDate)
            && otherBudget.getEndDate().localDate.isAfter(this.endDate.localDate);
    }

    /**
     * Recalculates the amountLeft in budget after an expense is added into the budget.
     * This is to prevent accidental amendments directly in the data file to result in wrong amount left.
     */
    public void recalculateAmountLeft() {
        double amountLeft = this.amount.getValue();
        for (Expense expense : expenseList.getExpenseList()) {
            amountLeft -= expense.getAmount().getValue();
        }
        if (amountLeft <= 0.0) {
            this.amountLeft = new Amount(String.format("%.2f", 0 - amountLeft));
            this.budgetAmountPositive = false;
        } else {
            this.amountLeft = new Amount(String.format("%.2f", amountLeft));
            this.budgetAmountPositive = true;
        }
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
            && otherBudget.getName().equals(getName())
            && otherBudget.getAmount().equals(getAmount())
            && otherBudget.getCurrency().equals(getCurrency())
            && otherBudget.getStartDate().equals(getStartDate())
            && otherBudget.getEndDate().equals(getEndDate());
    }

    public void setExpenseInBudget(Expense target, Expense editedExpense) {
        this.expenseList.setExpense(target, editedExpense);
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
            .append("Name: " + getName())
            .append("\n")
            .append("Amount: " + getAmount())
            .append(" " + getCurrency())
            .append("\n")
            .append("Start: " + getStartDate())
            .append("\n")
            .append("End: " + getEndDate())
            .append("\n");
        return builder.toString();
    }
}
