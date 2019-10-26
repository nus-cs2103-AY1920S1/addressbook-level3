package seedu.moneygowhere.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

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

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value.
     */
    public Budget(double budget) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
        this.sum = 0;
    }

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value.
     * @param sum The current sum of all spending in the month.
     */
    public Budget(double budget, double sum) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
        this.sum = sum;
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

    public double getValue() {
        return value;
    }

    public double getSum() {
        return sum;
    }

    public String getValueString() {
        return String.format("%.2f", value);
    }

    public String getSumString() {
        return String.format("%.2f", sum);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void clearBudgetSum() {
        sum = 0;
    }

    public void addSpending(Spending s) {
        sum += Double.parseDouble(s.getCost().toString());
    }

    public void deleteSpending(Spending s) {
        sum -= Double.parseDouble(s.getCost().toString());
    }

    public double getRemainingBudget() {
        return value - sum;
    }

    public void setBudget(Budget budget) {
        this.value = budget.value;
        this.sum = budget.sum;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && value == ((Budget) other).value
                && sum == ((Budget) other).sum);  // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, sum);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("$%.2f", value));
        sb.append("\n current spending:");
        sb.append(String.format("$%.2f", sum));
        return sb.toString();
    }
}
