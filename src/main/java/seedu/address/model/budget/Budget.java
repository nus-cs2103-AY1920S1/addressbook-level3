package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;

/**
 * Represents a Budget in Moolah.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {

    private static final Description DEFAULT_BUDGET_DESCRIPTION = new Description("Default Budget");
    //private static final Price DEFAULT_BUDGET_AMOUNT = new Price(Double.toString(Double.MAX_VALUE));
    private static final Price DEFAULT_BUDGET_AMOUNT = new Price("999999999999999999999");
    //private static final LocalDate DEFAULT_BUDGET_START_DATE = LocalDate.MIN;
    private static final Timestamp DEFAULT_BUDGET_START_DATE = new Timestamp(LocalDate.of(2000, 1, 1));
    //private static final Period DEFAULT_BUDGET_PERIOD = Period.between(LocalDate.MIN, LocalDate.MAX);
    private static final Period DEFAULT_BUDGET_PERIOD = Period.ofYears(999);
    private static final Percentage IS_NEAR_THRESHOLD = new Percentage(90);

    private final Description description;
    private final Price amount;
    private Timestamp startDate;
    private Timestamp endDate;
    private final Period period;
    private final ObservableList<Expense> expenses;
    private boolean isPrimary;
    private Percentage proportionUsed;

    //Constructor for user.
    public Budget(Description description, Price amount, Timestamp startDate, Period period) {
        requireAllNonNull(description, startDate, period, amount);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = startDate.plus(period);
        this.expenses = FXCollections.observableArrayList();
        this.isPrimary = false;
        this.proportionUsed = new Percentage(0);
    }

    //Constructor for system.
    public Budget(Description description, Price amount, Timestamp startDate,
                  Period period, ObservableList<Expense> expenses) {
        requireAllNonNull(description, amount, startDate, period, expenses);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = startDate.plus(period);
        this.isPrimary = false;
        this.proportionUsed = new Percentage(0);
        this.expenses = expenses;
    }

    //Constructor for system.
    public Budget(Description description, Price amount, Timestamp startDate, Timestamp endDate, Period period,
                  ObservableList<Expense> expenses, boolean isPrimary, Percentage proportionUsed) {
        requireAllNonNull(description, amount, startDate, endDate, period, expenses, isPrimary, proportionUsed);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        this.expenses = expenses;
        this.isPrimary = isPrimary;
        this.proportionUsed = proportionUsed;
    }

    public Description getDescription() {
        return description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public Period getPeriod() {
        return period;
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

    public void removeExpense(Expense e) {
        expenses.remove(e);
    }

    /**
     * Dummy.
     * @return Dummy.
     */
    public static Budget createDefaultBudget() {
        return new Budget(DEFAULT_BUDGET_DESCRIPTION,
                DEFAULT_BUDGET_AMOUNT,
                DEFAULT_BUDGET_START_DATE,
                DEFAULT_BUDGET_PERIOD);
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

    public double getExpenseSum() {
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            sum += expenses.get(i).getPrice().getAsDouble();
        }
        return sum;
    }

    public Percentage calculateProportionUsed() {
        return Percentage.calculate(getExpenseSum(), amount.getAsDouble());
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
        return getExpenseSum() > amount.getAsDouble();
    }

    /**
     * dummy.
     * @param date
     * @return
     */
    public boolean expired(Timestamp date) {
        return date.isAfter(endDate) || date.isEqual(endDate);
    }

    /**
     * dymmy.
     * @param date
     */
    public void refresh(Timestamp date) {
        assert expired(date) : "Budget is refreshed only when expired";
        long daysDiff = ChronoUnit.DAYS.between(endDate.getTimestamp(), date.getTimestamp());
        long periodDays = ChronoUnit.DAYS.between(startDate.getTimestamp(), endDate.getTimestamp());
        long cycles = daysDiff / periodDays;
        long offset = cycles * periodDays;
        startDate = endDate.plusDays(offset);
        endDate = startDate.plus(period);
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary() {
        isPrimary = true;
    }

    public void setNotPrimary() {
        isPrimary = false;
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
        expenses.stream().forEach(expense -> {
            if (withinCurrentPeriod(expense.getTimestamp())) {
                currentPeriodExpenses.add(expense);
            }
        });
        return currentPeriodExpenses;
    }

    /**
     * Checks if a timestamp is within the current budget period.
     * @param timestamp The timestamp to be checked against the current period.
     * @return A boolean indicating whether the timestamp is within the current period.
     */
    public boolean withinCurrentPeriod(Timestamp timestamp) {
        return (timestamp.isAfter(startDate) || timestamp.isEqual(startDate))
                && (timestamp.isBefore(endDate));
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
                && otherBudget.startDate.equals(startDate)
                && otherBudget.period.equals(period)
                && otherBudget.endDate.equals(endDate)
                && otherBudget.expenses.equals(expenses)
                && otherBudget.isPrimary == isPrimary
                && otherBudget.proportionUsed.equals(proportionUsed);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, startDate, period, amount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|| Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Period: ")
                .append(ParserUtil.formatPeriod(getPeriod()))
                .append(" Start date: ")
                .append(startDate)
                .append(" End date: ")
                .append(endDate)
                .append(" ||");
        return builder.toString();
    }
}
