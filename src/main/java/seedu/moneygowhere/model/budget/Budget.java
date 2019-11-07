package seedu.moneygowhere.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import seedu.moneygowhere.model.spending.Spending;

/**
 * Represents the budget in the MoneyGoWhere list.
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Budget values should be positive number";
    public static final String VALIDATION_REGEX = "^[+]?([0-9]+(?:[.][0-9]*)?|\\.[0-9]+)$";

    private double value;
    private double sum;
    private BudgetMonth month;

    /**
     * Constructs a {@code Budget}.
     *
     * @param amount A valid budget value.
     */
    public Budget(double amount) {
        checkArgument(isValidBudget(amount), MESSAGE_CONSTRAINTS);
        this.value = amount;
        this.sum = 0;
        this.month = BudgetMonth.now();
    }

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value.
     * @param month The month where the budget is valid for.
     */
    public Budget(double budget, String month) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
        this.sum = 0;
        this.month = BudgetMonth.parse(month);
    }

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value in String.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(budget);
        this.sum = 0;
        this.month = BudgetMonth.now();
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    public static boolean isValidBudget(double test) {
        return test >= 0.0;
    }

    /**
     * initializes the Budget.
     * updates month {@code Budget} if the given date is in a different month than the on currently stored.
     * also updates the value of sum to be the same as those currently available in the spendingList.
     *
     * @param date The current Date.
     * @param spendings the spending list to initialize the budget with
     */
    public void initialize(LocalDate date, List<Spending> spendings) {
        BudgetMonth temp = new BudgetMonth(date);
        this.month = temp;

        this.sum = 0;
        for (Spending s : spendings) {
            addSpending(s);
        }
    }

    public double getValue() {
        return value;
    }

    public double getSum() {
        return sum;
    }

    public String getValueString() {
        return String.format("%.2f", value);
    }

    public String getMonthString() {
        return month.toString();
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void clearBudgetSum() {
        sum = 0;
    }

    /**
     * Checks whether a spending is in the same month of the Budget.
     * @param s the spending to check.
     * @return whether or not the spending is in the same month as the budget
     */
    public boolean inSameMonth(Spending s) {
        BudgetMonth temp = new BudgetMonth(s.getDateValue());
        return temp.equals(this.month);
    }

    /**
     * Adds the spending cost if it is in the same month.
     * @param s The spending to add.
     */
    public void addSpending(Spending s) {
        if (inSameMonth(s)) {
            sum += Double.parseDouble(s.getCost().toString());
        }
    }

    /**
     * deletes the spending cost if it is in the same month.
     * @param s The spending to delete.
     */
    public void deleteSpending(Spending s) {
        if (inSameMonth(s)) {
            sum -= Double.parseDouble(s.getCost().toString());
        }
    }

    public double getRemainingBudget() {
        return value - sum;
    }

    public void setBudgetAmount(Budget budget) {
        this.value = budget.value;
    }

    public void setBudget(Budget budget) {
        this.value = budget.value;
        this.sum = budget.sum;
        this.month = budget.month;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && value == ((Budget) other).value
                && month.equals(((Budget) other).month)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sum);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("$%.2f", value));
        return sb.toString();
    }

    /**
     * Return a complete description of the budget.
     * @return A string representing the set budget only.
     */
    public String getBudgetMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append("\n Current Spending:");
        sb.append(String.format("$%.2f", sum));
        sb.append("\n Remaining Budget:");
        sb.append(String.format("$%.2f", getRemainingBudget()));
        return sb.toString();
    }
}
