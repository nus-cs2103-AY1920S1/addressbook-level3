package seedu.moneygowhere.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import seedu.moneygowhere.model.spending.Spending;

/**
 * Represents the budget in the MoneyGoWhere list.
 * A budget cannot must be non negative number, no grater than 1000000000.
 * A budget should have at most 2 digits after the decimal point.
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "The budget amount provided is invalid.\n"
        + "Budget values must be a non negative number "
        + "no greater than 100000000 and have at most 2 digits after the decimal point.";
    private static final String VALIDATION_REGEX = "^[+]?([0-9]+(?:[.][0-9]*)?|\\.[0-9]+)$";
    private static final int MAX_BUDGET_LIMIT = 1000000000;

    private double amount;
    private double sum;
    private BudgetMonth month;

    /**
     * Constructs a {@code Budget}.
     *
     * @param amount A valid budget value.
     */
    public Budget(double amount) {
        checkArgument(isValidBudget(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
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
        this.amount = budget;
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
        this.amount = Double.parseDouble(budget);
        this.sum = 0;
        this.month = BudgetMonth.now();
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX)
                && isValidBudget(Double.parseDouble(test))
                && digitsAfterPoint(test) <= 2;
    }

    /**
     * Returns true if a given string is a valid budget value. (Not non-negative)
     */
    private static boolean isValidBudget(double test) {
        return test >= 0.0 && test <= MAX_BUDGET_LIMIT;
    }

    /**
     * Counts the number of digits in a double in string format.
     *
     * @param str The input string to count the number of characters after the decimal point.
     * @return The number of characters after the first decimal point.
     */
    private static int digitsAfterPoint(String str) {
        int output = 0;
        if (str.contains(".")) {
            String[] splitPoint = str.split("\\.");
            output = splitPoint[1].length();
        }
        return output;
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
        this.month = new BudgetMonth(date);

        this.sum = 0;
        for (Spending s : spendings) {
            addSpending(s);
        }
    }

    public double getAmount() {
        return amount;
    }

    public double getSum() {
        return sum;
    }

    public String getValueString() {
        return String.format("%.2f", amount);
    }

    public String getMonthString() {
        return month.toString();
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

    //@@author jonathantjendana
    /**
     * Adds those spending cost if they are in the same month.
     * @param spendingList The spending list to add.
     */
    public void addSpending(List<Spending> spendingList) {
        sum += spendingList.parallelStream()
                .filter(s -> inSameMonth(s))
                .reduce(0.0, (x, y) -> x + Double.parseDouble(y.getCost().toString()), Double::sum);
    }

    //@@author austinsantoso
    /**
     * deletes the spending cost if it is in the same month.
     * @param s The spending to delete.
     */
    public void deleteSpending(Spending s) {
        if (inSameMonth(s)) {
            sum -= Double.parseDouble(s.getCost().toString());
        }
    }

    private double getRemainingBudget() {
        return amount - sum;
    }

    public void setBudgetAmount(Budget budget) {
        this.amount = budget.amount;
    }

    public void setBudget(Budget budget) {
        this.amount = budget.amount;
        this.sum = budget.sum;
        this.month = budget.month;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && amount == ((Budget) other).amount
                && month.equals(((Budget) other).month)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, sum);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.2f", amount));
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
