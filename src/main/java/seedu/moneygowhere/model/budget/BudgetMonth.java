package seedu.moneygowhere.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.Objects;

import seedu.moneygowhere.commons.util.DateUtil;

/**
 * Represents the month that the budget is valid for.
 * Also contains the year.
 */
public class BudgetMonth {

    public static final String MESSAGE_CONSTRAINTS = "BudgetMonth needs to have a format MM/YYYY";
    public static final String VALIDATION_REGEX = "^(1[0-2]|0[1-9]|\\d)\\/([2-9]\\d[1-9]\\d|[1-9]\\d)$";

    private int month;
    private int year;

    public BudgetMonth(LocalDate date) {
        this.month = date.getMonthValue();
        this.year = date.getYear();
    }

    public BudgetMonth(int month, int year) {
        this.month = month;
        this.year = year;
    }

    /**
     * Parses the given string to a BudgetMonth.
     * The input has to be of format MM/YYYY
     * throws exception if date has incorrect format.
     * @param date the string to be parsed.
     * @return A BudgetMonth representing the input String
     */
    public static BudgetMonth parse(String date) {
        requireNonNull(date);
        checkArgument(isValidBudgetMonth(date), MESSAGE_CONSTRAINTS);
        String[] dateSplit = date.split("/");
        int tempMonth = Integer.parseInt(dateSplit[0]);
        int tempYear = Integer.parseInt(dateSplit[1]);

        return new BudgetMonth(tempMonth, tempYear);
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    /**
     * Gets today's date as a BudgetMonth
     * @return A BudgetMonth representing today.
     */
    public static BudgetMonth now() {
        return new BudgetMonth(DateUtil.getTodayDate());
    }

    /**
     * Checks whether a string is a valid BudgetMonth based on Regex.
     * It must be of format MM/YYYY where the year is >= 2010.
     *
     * @param test The string to be tested.
     * @return whether the String is of format MM/YYYYor not.
     */
    public static boolean isValidBudgetMonth(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks whether a date is behind a particular month.
     *
     * @param date the date to check
     * @return whether or not the date was set before this month
     */
    public boolean isBehind(LocalDate date) {
        return this.month < date.getMonthValue()
                || this.year < date.getYear();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetMonth // instanceof handles nulls
                && month == ((BudgetMonth) other).month
                && year == ((BudgetMonth) other).year); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%2d", month));
        sb.append(String.format("/%4d", year));

        return sb.toString();
    }
}
