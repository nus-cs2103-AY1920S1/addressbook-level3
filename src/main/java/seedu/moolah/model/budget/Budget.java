package seedu.moolah.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

/**
 * Represents a Budget in MooLah.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    public static final Description DEFAULT_BUDGET_DESCRIPTION = new Description("Default Budget");
    private static final Price DEFAULT_BUDGET_AMOUNT = Price.MAX_PRICE;
    private static final Timestamp DEFAULT_BUDGET_START_DATE = Timestamp.EARLIEST_TIMESTAMP;
    private static final BudgetPeriod DEFAULT_BUDGET_PERIOD = BudgetPeriod.INFINITY;
    private static final ObservableList<Expense> DEFAULT_BUDGET_EXPENSES = FXCollections.observableArrayList();
    private static final boolean DEFAULT_BUDGET_IS_PRIMARY = true;

    public static final Budget DEFAULT_BUDGET = new Budget(DEFAULT_BUDGET_DESCRIPTION, DEFAULT_BUDGET_AMOUNT,
            DEFAULT_BUDGET_START_DATE, DEFAULT_BUDGET_PERIOD, DEFAULT_BUDGET_EXPENSES, DEFAULT_BUDGET_IS_PRIMARY);

    private static final Percentage IS_HALF_THRESHOLD = new Percentage(50);
    private static final Percentage IS_NEAR_THRESHOLD = new Percentage(90);

    private final Description description;
    private final Price amount;
    private final BudgetWindow window;
    private ObservableList<Expense> expenses;
    private boolean isPrimary;

    //Constructor for user input, four fields.
    public Budget(Description description, Price amount, Timestamp startDate, BudgetPeriod period) {
        requireAllNonNull(description, amount, startDate, period);

        this.description = description;
        this.amount = amount;
        this.window = new BudgetWindow(startDate, period);
        this.expenses = FXCollections.observableArrayList();
    }

    //Constructor for system, six fields.
    public Budget(Description description, Price amount, Timestamp startDate, BudgetPeriod period,
                  ObservableList<Expense> expenses, boolean isPrimary) {
        this(description, amount, startDate, period);

        requireNonNull(expenses);
        this.expenses = expenses;
        this.isPrimary = isPrimary;
    }

    public Description getDescription() {
        return this.description;
    }

    public Price getAmount() {
        return this.amount;
    }

    public Timestamp getWindowStartDate() {
        return this.window.getStartDate();
    }

    public Timestamp getWindowEndDate() {
        return this.window.getEndDate();
    }

    public BudgetPeriod getBudgetPeriod() {
        return this.window.getBudgetPeriod();
    }

    public ObservableList<Expense> getExpenses() {
        return this.expenses;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setToPrimary() {
        isPrimary = true;
    }

    public void setToNotPrimary() {
        isPrimary = false;
    }

    /**
     * Checks if this budget is default budget by comparison of budget descriptions.
     *
     * @return True if the budget is default budget, false otherwise.
     */
    public boolean isDefaultBudget() {
        return this.description.equals(DEFAULT_BUDGET_DESCRIPTION);
    }

    /**
     * Makes a deep copy of this budget.
     *
     * @return A deep copy of the budget, with identical attributes. The expense list is deep copied.
     */
    public Budget deepCopy() {
        ObservableList<Expense> expensesCopy = FXCollections.observableArrayList(this.expenses);
        Budget budget = new Budget(this.description, this.amount, this.getWindowStartDate(),
                this.getBudgetPeriod(), expensesCopy, this.isPrimary);
        return budget;
    }

    /**
     * Normalizes the budget window to the period containing the specified timestamp.
     *
     * @param anchor The timestamp to anchor the period.
     * @return A deep copy of this budget, normalized according to the anchor.
     */
    public Budget normalize(Timestamp anchor) {
        requireNonNull(anchor);

        if (this.isDefaultBudget()) {
            return this; // default budget has "infinity" period, no need to normalize
        }
        Budget copy = this.deepCopy();
        copy.window.normalize(anchor);
        return copy;
    }

    /**
     * Refreshes the budget window according to the current time.
     */
    public void refresh() {
        if (!this.isDefaultBudget()) {
            this.window.normalize(Timestamp.getCurrentTimestamp());
        }
    }

    /**
     * Adds the specified expense to this budget's expense list. Duplicates are not added.
     *
     * @param toAdd The expense to add.
     */
    public void addExpense(Expense toAdd) {
        requireNonNull(toAdd);

        if (!this.expenses.contains(toAdd)) {
            this.expenses.add(toAdd);
        }
    }

    /**
     * Removes the specified expense from this budget's expense list.
     *
     * @param toRemove The expense to remove.
     */
    public void removeExpense(Expense toRemove) {
        requireNonNull(toRemove);

        this.expenses.remove(toRemove);
    }

    /**
     * Transfer all expenses from this budget to another.
     *
     * @param other The other budget to accept all expenses from this budget.
     */
    public void transferExpensesTo(Budget other) {
        requireNonNull(other);
        if (other.isSameBudget(this)) {
            return;
        }
        for (Expense e : this.expenses) { // Change budget name in expenses
            e.setBudget(other);
        }
        if (other.expenses == this.expenses) { // Prevents concurrent modification
            return;
        }
        for (Expense e : this.expenses) { // Add expenses to other budget's expense list
            if (!other.expenses.contains(e)) {
                other.expenses.add(e);
            }
        }
    }

    /**
     * Replaces the specified expense in the expense list with an updated one.
     *
     * @param target The expense to be updated.
     * @param editedExpense The edited expense.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        if (this.expenses.contains(target)) {
            int index = this.expenses.indexOf(target);
            this.expenses.set(index, editedExpense);
        }
    }

    /**
     * Filters from the expense list those expenses that are within the current budgeting period.
     *
     * @return An ObservableList of expenses within the current budgeting period.
     */
    public ObservableList<Expense> getCurrentPeriodExpenses() {
        ObservableList<Expense> currentPeriodExpenses = FXCollections.observableArrayList();
        if (this.expenses != null) {
            this.expenses.stream().forEach(e -> {
                if (withinCurrentPeriod(e)) {
                    currentPeriodExpenses.add(e);
                }
            });
        }
        return currentPeriodExpenses;
    }

    /**
     * Checks if the specified expense is within the current budgeting period.
     *
     * @param expense The expense to be checked.
     * @return True if the expense is within the current budgeting period. False otherwise.
     */
    private boolean withinCurrentPeriod(Expense expense) {
        requireNonNull(expense);
        return this.window.contains(expense.getTimestamp());
    }

    /**
     * Calculates the sum of expenses within the current budgeting period.
     *
     * @return A double representing the sum of prices of expenses within the current budgeting period.
     */
    public double calculateExpenseSum() {
        List<Expense> currentExpenses = getCurrentPeriodExpenses();
        double sum = 0;
        for (Expense expense : currentExpenses) {
            sum += expense.getPrice().getAsDouble();
        }
        return sum;
    }

    /**
     * Calculates the proportion of budget used in the current period.
     *
     * @return A Percentage representing the proportion used.
     */
    public Percentage calculateProportionUsed() {
        return Percentage.calculate(this.calculateExpenseSum(), amount.getAsDouble());
    }

    /**
     * Checks if half the budget has been used up.
     *
     * @return True if proportion used reaches IS_HALF_THRESHOLD (50%), false otherwise.
     */
    public boolean isHalf() {
        return calculateProportionUsed().reach(IS_HALF_THRESHOLD);
    }

    /**
     * Checks if the budget limit is near.
     *
     * @return True if proportion used reaches IS_NEAR_THRESHOLD (90%), false otherwise.
     */
    public boolean isNear() {
        return calculateProportionUsed().reach(IS_NEAR_THRESHOLD);
    }

    /**
     * Checks whether the budget is exceeded.
     *
     * @return True if expense sum is more than budget limit, false otherwise.
     */
    public boolean isExceeded() {
        return calculateExpenseSum() > amount.getAsDouble();
    }

    /**
     * Returns true if both budgets have the same description, i.e. name.
     * Implication: all budgets should have different names.
     *
     * @param otherBudget The other budget to be compared.
     * @return True if this budget and the other budget are the same budget, false otherwise.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.description.equals(description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, getWindowStartDate(), getWindowEndDate(), getBudgetPeriod(),
                expenses, isPrimary);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget.description.equals(description)
                && otherBudget.amount.equals(amount)
                && otherBudget.window.equals(window)
                && otherBudget.expenses.equals(expenses)
                && otherBudget.isPrimary == isPrimary;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|| Description: ")
                .append(this.description)
                .append(" Amount: ")
                .append(this.amount)
                .append(this.window)
                .append(" ||");
        return builder.toString();
    }
}
