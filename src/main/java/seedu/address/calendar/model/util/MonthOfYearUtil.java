package seedu.address.calendar.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.calendar.model.date.MonthOfYear;

/**
 * Handles manipulation of all {@code MonthOfYear} related operations.
 */

public class MonthOfYearUtil {
    private static final String MESSAGE_CONSTRAINTS_JAVA_MONTH = "All Java months should be between 0 and 11, "
            + "inclusive";
    static int NUM_MONTHS_IN_YEAR = 12;

    /**
     * Converts an {@code int} which represents a month in Java's default {@code Calendar} class to an instance of
     * {@code MonthOfYear}.
     * Guarantees: {@code javaMonth} is between 0 and 11 (inclusive)
     *
     * @param javaMonth {@code int} representation of a month in Java's default {@code Calendar} class
     * @return {@code MonthOfYear} equivalent of {@code javaMonth}
     */
    static MonthOfYear convertJavaMonth(int javaMonth) {
        checkArgument(isValidZeroBasedMonthNum(javaMonth), MESSAGE_CONSTRAINTS_JAVA_MONTH);
        return MonthOfYear.values()[javaMonth];
    }

    /**
     * Checks whether {@code monthNum} is a valid zero-based representation of a month, i.e.
     * the values should be between 0 and 11 (inclusive).
     *
     * @param monthNum Numerical value which represents a month
     * @return {@code true} if the numerical value is a valid zero-based representation of a month
     */
    static boolean isValidZeroBasedMonthNum(int monthNum) {
        return monthNum < MonthOfYear.values().length && monthNum >= 0;
    }

    /**
     * Converts a zero-based representation of a month to an instance of {@code MonthOfYear}.
     * Guarantees: {@code monthNum} is between 0 and 11 (inclusive)
     *
     * @param monthNum Zero-based, numerical representation of a month
     * @return @code MonthOfYear} equivalent of {@code monthNum}
     */
    static MonthOfYear convertZeroBasedNumToMonth(int monthNum) {
        return convertJavaMonth(monthNum);
    }

    /**
     * Checks whether the given string of nth-length matches any of the first nth-letters of a valid month.
     * The given string must contain at least 3 letters.
     *
     * @param monthStr The given string
     * @return {@code true} if {@code monthStr} is a valid representation of a valid month and contains at least 3
     *          letters
     */
    static boolean isValidMonthStr(String monthStr) {
        return Stream.of(MonthOfYear.values())
                .anyMatch(month -> {
                    String monthLowerCase = month.toString().toLowerCase();
                    String monthStrLowerCase = monthStr.toLowerCase().trim();
                    return isValidMonthStr(monthStrLowerCase, monthLowerCase);
                })
                && monthStr.length() >= 3;
    }

    /**
     * Compares the given string with the expected string from the start and ensure that they match from the 0th
     * position to the last position of the given string.
     * Note: This comparison is not case sensitive and ignores all trailing spaces
     *
     * @param given The given string to be matched with the expected string
     * @param expected The expected string
     * @return {@code true} if {@code given} is indeed merely a shorter/an identical version of {@code expected}
     */
    private static boolean isValidMonthStr(String given, String expected) {
        String givenFormatted = given.trim().toLowerCase();
        String expectedFormatted = expected.trim().toLowerCase();

        if (givenFormatted.length() > expectedFormatted.length()) {
            return false;
        }

        for (int i = 0; i < givenFormatted.length(); i++) {
            char currGiven = givenFormatted.charAt(i);
            char currExpected = expectedFormatted.charAt(i);

            if (currGiven != currExpected) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a valid {@code String} representation of a month to an instance of {@code MonthOfYear}.
     * Guarantees: The given string is a valid representation and contains at least 3 letters
     *
     * @param monthStr The valid {@code String} representation of a month
     * @return {@code MonthOfYear} instance that is represented by {@code monthStr}
     */
    static MonthOfYear convertStrToMonth(String monthStr) {
        checkArgument(isValidMonthStr(monthStr), "monthStr must contain at least 3 letters and "
                + "represent a valid month of year");
        Optional<MonthOfYear> monthOfYear = Stream.of(MonthOfYear.values())
                .filter(month -> {
                    String monthLowerCase = month.toString().toLowerCase();
                    String monthStrLowerCase = monthStr.toLowerCase().trim();
                    return isValidMonthStr(monthStrLowerCase, monthLowerCase);
                })
                .findFirst();

        if (monthOfYear.isEmpty()) {
            assert false : "monthStr should be a valid representation of a month";
        }

        return monthOfYear.get();
    }
}
