package seedu.address.model.budget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final Price DEFAULT_BUDGET_AMOUNT = new Price("100000000000");
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
    private final List<Expense> expenses;
    private boolean isPrimary;
    private Percentage proportionUsed;

    public Budget(Description description, Price amount, Timestamp startDate, Period period) {
        requireAllNonNull(description, startDate, period, amount);
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = startDate.plus(period);
        this.expenses = new ArrayList<>();
        this.isPrimary = false;
        this.proportionUsed = new Percentage(0);
    }

    public Budget(Description description, Price amount, Timestamp startDate, Period period, List<Expense> expenses) {
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense e) {
        expenses.add(e);
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

    public double getExpenseSum() {
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            sum = sum + expenses.get(i).getPrice().getAsDouble();
        }
        return sum;
    }

    public Percentage getProportionUsed() {
        return Percentage.calculate(getExpenseSum(), amount.getAsDouble());
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
        return endDate.isBefore(date);
    }

    /**
     * dymmy.
     * @param date
     */
    public void refresh(Timestamp date) {
        assert endDate.isBefore(date) : "Budget is refreshed only when expired";
        long daysDiff = ChronoUnit.DAYS.between(endDate.getTimestamp(), date.getTimestamp());
        int periodDays = period.getDays();
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

    public void setExpense(Expense target, Expense editedExpense) {
        if (expenses.contains(target)) {
            int index = expenses.indexOf(target);
            expenses.set(index, editedExpense);
        }
    }

    /**
     * Returns true if both budgets have the same description, amount, start date and period.
     */
    public boolean isSameBudget(Budget otherBudget) {
        if (otherBudget == this) {
            return true;
        }

        return otherBudget != null
                && otherBudget.getDescription().equals(getDescription())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getPeriod().equals(getPeriod());
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
        return otherBudget.getDescription().equals(getDescription())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getPeriod().equals(getPeriod())
                && otherBudget.getEndDate().equals(getEndDate())
                && otherBudget.isPrimary() == isPrimary()
                && otherBudget.getExpenses().equals(getExpenses());
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
                .append("||");
        return builder.toString();
    }
}
