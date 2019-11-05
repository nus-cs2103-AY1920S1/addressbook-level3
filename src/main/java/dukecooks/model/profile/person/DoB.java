package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;
import dukecooks.logic.parser.DateParser;
import dukecooks.model.Date;

/**
 * Represents the user's date of birth.
 */
public class DoB {
    public static final String MESSAGE_CONSTRAINTS =
            "DoB should only contain numeric characters in the format of DD/MM/YYYY, and it should not be blank";

    public static final String MESSAGE_DATE_GREATER_THAN_NOW =
            "Date of birth specified is in the future! You are not born yet! Try again!";

    public final Date dateOfBirth;

    /**
     * Constructs a {@code date}.
     *
     * @param date A valid date in String form.
     */
    public DoB(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        AppUtil.checkArgument(isValidDatePeriod(date), MESSAGE_DATE_GREATER_THAN_NOW);
        dateOfBirth = Date.generateDate(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        return Date.isValidDate(date);
    }

    /**
     * Returns true if it is a valid date before present.
     * Prevents user from indicating dates from the future.
     */
    public static boolean isValidDatePeriod(String date) {
        Date d = Date.generateDate(date);
        return DateParser.getCurrentDayDiff(d) < 0;
    }

    @Override
    public String toString() {
        return dateOfBirth.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoB // instanceof handles nulls
                && dateOfBirth.equals(((DoB) other).dateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

}
