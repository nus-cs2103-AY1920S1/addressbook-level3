package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.ParserUtil;
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
    private Timestamp startDate;
    private Timestamp endDate;
    private final Period period;
    private ObservableList<Expense> expenses;
    private boolean isPrimary;
    private Percentage proportionUsed;

    //Constructor for user.
    public Budget(Description description, Price amount, Timestamp startDate, Period period) {
        requireAllNonNull(description, startDate, period, amount);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = calculateEndDate();
        this.expenses = FXCollections.observableArrayList();
        this.isPrimary = false;
        this.proportionUsed = calculateProportionUsed();
    }

    //Constructor for system.
    public Budget(Description description, Price amount, Timestamp startDate, Period period,
                  ObservableList<Expense> expenses) {
        requireAllNonNull(description, amount, startDate, period, expenses);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = calculateEndDate();
        this.isPrimary = false;
        this.proportionUsed = calculateProportionUsed();
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

    /**
     * Dummy.
     * @return Dummy.
     */
    public static Budget createDefaultBudget() {
        Budget defaultBudget = new Budget(DEFAULT_BUDGET_DESCRIPTION,
                DEFAULT_BUDGET_AMOUNT,
                DEFAULT_BUDGET_START_DATE,
                DEFAULT_BUDGET_PERIOD);
        defaultBudget.setPrimary();
        return defaultBudget;
    }

    /**
     * Calculates proper end date based on given start date and period.
     */
    private Timestamp calculateEndDate() {
        Timestamp endDate = startDate.plus(period);
        if (period.getMonths() == 1 && endDate.getDayOfMonth() < startDate.getDayOfMonth()) {
            endDate.plusDays(1);
        }
        return endDate.minusDays(1);
    }

    /**
     * Normalizes the budget window to the current period.
     */
    public void normalize(Timestamp anchor) {
        LocalDateTime now = anchor.fullTimestamp;

        if (period.getMonths() == 1) {

            int specifiedDayOfMonth = startDate.getDayOfMonth();
            int currentDayOfMonth = now.getDayOfMonth();
            LocalDateTime normalized;
            if (currentDayOfMonth >= specifiedDayOfMonth) {
                normalized = now.withDayOfMonth(specifiedDayOfMonth);
            } else {
                normalized = now.minusMonths(1).withDayOfMonth(specifiedDayOfMonth);
            }
            startDate = new Timestamp(normalized);
            endDate = calculateEndDate();

        } else if (period.getYears() == 1) {

            int specifiedDayOfYear = startDate.getDayOfYear();
            int currentDayOfYear = now.getDayOfYear();
            LocalDateTime normalized;
            if (currentDayOfYear >= specifiedDayOfYear) {
                normalized = now.withMonth(startDate.getMonthValue())
                        .withDayOfMonth(startDate.getDayOfMonth());
            } else {
                normalized = now.minusYears(1)
                        .withMonth(startDate.getMonthValue())
                        .withDayOfMonth(startDate.getDayOfMonth());
            }
            startDate = new Timestamp(normalized);
            endDate = calculateEndDate();

        } else if (period.getDays() == 1) {

            startDate = new Timestamp(now);
            endDate = calculateEndDate();

        } else if (period.getDays() == 7) {

            long daysDiff = ChronoUnit.DAYS.between(startDate.getFullTimestamp().toLocalDate(), now.toLocalDate());
            long offset = daysDiff >= 0 ? daysDiff % 7 : daysDiff % 7 + 7;
            startDate = new Timestamp(now.minusDays(offset));
            endDate = calculateEndDate();

        }
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
        List<Expense> currentExpenses = getCurrentPeriodExpenses();
        double sum = 0;
        for (int i = 0; i < currentExpenses.size(); i++) {
            sum += currentExpenses.get(i).getPrice().getAsDouble();
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

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary() {
        isPrimary = true;
    }

    public void setNotPrimary() {
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
                if (withinCurrentPeriod(expense.getTimestamp())) {
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
     * Checks if a timestamp is within the current budget period.
     * @param timestamp The timestamp to be checked against the current period.
     * @return A boolean indicating whether the timestamp is within the current period.
     */
    public boolean withinCurrentPeriod(Timestamp timestamp) {
        return timestamp.getDate().isAfter(startDate.getDate().minusDays(1))
                && timestamp.getDate().isBefore(endDate.getDate().plusDays(1));
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
                && otherBudget.startDate.getDate().equals(startDate.getDate())
                && otherBudget.period.equals(period)
                && otherBudget.endDate.getDate().equals(endDate.getDate())
                && otherBudget.expenses.equals(expenses)
                && otherBudget.isPrimary == isPrimary
                && otherBudget.proportionUsed.equals(proportionUsed);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount, startDate, endDate, period, expenses,
                isPrimary, proportionUsed);
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
