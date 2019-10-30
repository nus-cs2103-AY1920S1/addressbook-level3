package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;

/**
 * Represents a Budget in MooLah.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    private static final Description DEFAULT_BUDGET_DESCRIPTION = new Description("default budget");
    //private static final Price DEFAULT_BUDGET_AMOUNT = new Price(Double.toString(Double.MAX_VALUE));
    private static final Price DEFAULT_BUDGET_AMOUNT = new Price("999999999999999999999");
    //private static final LocalDate DEFAULT_BUDGET_START_DATE = LocalDate.MIN;
    private static final Timestamp DEFAULT_BUDGET_START_DATE = new Timestamp(LocalDateTime.of(2000, 1, 1, 0, 0));
    //private static final Period DEFAULT_BUDGET_PERIOD = Period.between(LocalDate.MIN, LocalDate.MAX);
    private static final Period DEFAULT_BUDGET_PERIOD = Period.ofYears(999);
    //private static final boolean DEFAULT_BUDGET_IS_PRIMARY = true;
    private static final Percentage IS_NEAR_THRESHOLD = new Percentage(90);

    private final Description description;
    private final Price amount;
    private final BudgetWindow window;
    private ObservableList<Expense> expenses;
    private boolean isPrimary;
    private Percentage proportionUsed;

    //Constructor for user.
    public Budget(Description description, Price amount, Timestamp startDate, Period period) {
        requireAllNonNull(description, startDate, period, amount);
        this.description = description;
        this.amount = amount;
        this.window = new BudgetWindow(startDate, period);
        this.expenses = FXCollections.observableArrayList();
        this.proportionUsed = calculateProportionUsed();
    }

    //Constructor for system.
    public Budget(Description description, Price amount, Timestamp startDate, Period period,
                  ObservableList<Expense> expenses) {
        requireAllNonNull(description, amount, startDate, period, expenses);
        this.description = description;
        this.amount = amount;
        this.window = new BudgetWindow(startDate, period);
        this.expenses = expenses;
        this.proportionUsed = calculateProportionUsed();
    }

    //Constructor for system.
    public Budget(Description description, Price amount, Timestamp startDate, Timestamp endDate, Period period,
                  ObservableList<Expense> expenses, boolean isPrimary, Percentage proportionUsed) {
        requireAllNonNull(description, amount, startDate, endDate, period, expenses, isPrimary, proportionUsed);
        this.description = description;
        this.amount = amount;
        this.window = new BudgetWindow(startDate, period);
        this.expenses = expenses;
        this.isPrimary = isPrimary;
        this.proportionUsed = proportionUsed;
    }

    public Description getDescription() {
        return description;
    }

    public Timestamp getStartDate() {
        return window.getStartDate();
    }

    public Timestamp getEndDate() {
        return window.getEndDate();
    }

    public Period getPeriod() {
        return window.getPeriod();
    }

    public Price getAmount() {
        return amount;
    }

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public Percentage getProportionUsed() {
        return proportionUsed;
    }

    /**
     * Dummy.
     * @return Dummy.
     */
    public static Budget createDefaultBudget() {
        Budget defaultBudget = new Budget(DEFAULT_BUDGET_DESCRIPTION,
                DEFAULT_BUDGET_AMOUNT,
                DEFAULT_BUDGET_START_DATE,
                DEFAULT_BUDGET_PERIOD);
        defaultBudget.setToPrimary();
        return defaultBudget;
    }

    /**
     * Normalizes the budget window to the current period.
     */
    public void normalize(Timestamp anchor) {
        window.normalize(anchor);
    }

    /**
     * Adds an expense to this budget's expense list.
     * @param e The expense to add.
     */
    public void addExpense(Expense e) {
        if (!expenses.contains(e)) {
            expenses.add(e);
        }
    }

    /**
     * Dummy.
     */
    public double calculateExpenseSum() {
        List<Expense> currentExpenses = getCurrentPeriodExpenses();
        double sum = 0;
        for (Expense expense : currentExpenses) {
            sum += expense.getPrice().getAsDouble();
        }
        return sum;
    }

    public Percentage calculateProportionUsed() {
        return Percentage.calculate(calculateExpenseSum(), amount.getAsDouble());
    }

    public void updateProportionUsed() {
        proportionUsed = calculateProportionUsed();
    }

    public boolean isNear() {
        return getProportionUsed().reach(IS_NEAR_THRESHOLD);
    }

    /**
     * Checks whether the budget is exceeded.
     */
    public boolean isExceeded() {
        return calculateExpenseSum() > amount.getAsDouble();
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

    public void setIsPrimary(boolean status) {
        isPrimary = status;
    }

    /**
     * Dummy.
     * @param otherExpense
     */
    public void removeIdentical(Expense otherExpense) {
        Expense toRemove = null;
        for (Expense expense : expenses) {
            if (expense.isSameExpense(otherExpense)) {
                toRemove = expense;
                break;
            }
        }
        expenses.remove(toRemove);
    }

    public void clearExpenses() {
        expenses.clear();
    }

    /**
     * Sets the specified expense in the expense list to an updated one.
     * @param target The expense to be updated.
     * @param editedExpense The edited expense.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        if (expenses.contains(target)) {
            int index = expenses.indexOf(target);
            expenses.set(index, editedExpense);
        }
    }

    public ObservableList<Expense> getCurrentPeriodExpenses() {
        ObservableList<Expense> currentPeriodExpenses = FXCollections.observableArrayList();
        if (expenses != null) {
            expenses.stream().forEach(expense -> {
                if (withinCurrentPeriod(expense)) {
                    currentPeriodExpenses.add(expense);
                }
            });
        }
        return currentPeriodExpenses;
    }

    /**
     * Makes a deep copy of a budget.
     * @param other The budget to be deep copied.
     * @return A deep copy of the budget, with identical attributes.
     */
    public static Budget deepCopy(Budget other) {
        Budget budget = new Budget(other.getDescription(), other.getAmount(), other.getStartDate(),
                other.getEndDate(), other.getPeriod(), other.getExpenses(), other.isPrimary(),
                other.getProportionUsed());
        return budget;
    }

    /**
     * Checks if an expense is within the current budget period.
     */
    public boolean withinCurrentPeriod(Expense expense) {
        return window.contains(expense.getTimestamp());
    }

    /**
     * Returns true if both budgets have the same description, i.e. name.
     * Implication: all budgets should have different names.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.description.equals(description);
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
                && otherBudget.isPrimary == isPrimary
                && otherBudget.proportionUsed.equals(proportionUsed);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount, window, expenses, isPrimary, proportionUsed);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|| Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(window)
                .append(" ||");
        return builder.toString();
    }
}
