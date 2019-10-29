package seedu.moneygowhere.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.moneygowhere.commons.core.LogsCenter;
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
     * @param budget A valid budget value.
     */
    public Budget(double budget) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
        this.sum = 0;
        this.month = BudgetMonth.now();
    }

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget value.
     * @param sum The current sum of all spending in the month.
     */
    public Budget(double budget, double sum, String month) {
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        this.value = budget;
        this.sum = sum;
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
     * Updates month {@code Budget} if the given date is in a different month than the on currently stored.
     *
     * @param date The current Date.
     */
    public void update(LocalDate date) {
        Logger logger = LogsCenter.getLogger(getClass());
        BudgetMonth temp = new BudgetMonth(date);
        if (!this.month.equals(temp) && this.month.isBehind(date)) {
            this.month = temp;
            this.sum = 0;
            logger.info("BudgetMonth has been updated");
        } else {
            logger.info("BudgetMonth is not updated");
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

    public String getSumString() {
        return String.format("%.2f", sum);
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
                && sum == ((Budget) other).sum // state check
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
        sb.append("\n current spending:");
        sb.append(String.format("$%.2f", sum));
        sb.append("\n Remaining Budget:");
        sb.append(String.format("$%.2f", getRemainingBudget()));
        return sb.toString();
    }
}
