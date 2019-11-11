package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEARS;

import java.time.Period;
import java.util.ArrayList;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

/**
 * Represents a Policy's coverage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoverage(String)}
 */
public class Coverage {

    public static final String MESSAGE_CONSTRAINTS =
        "Coverage should contain valid day, month or year numbers, and should not be blank.";

    public static final String DATA_TYPE = "COVERAGE";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{N}]+";
    private static final int dayIndex = 0;
    private static final int monthIndex = 1;
    private static final int yearIndex = 2;

    public final Period period;
    public final String coverage;

    /**
     * Constructs a {@code Coverage}.
     *
     * @param coverage A valid coverage declaration.
     */
    public Coverage(String coverage) {
        requireNonNull(coverage);
        checkArgument(isValidCoverage(coverage), MESSAGE_CONSTRAINTS);
        ArrayList<String> coverageBreakDown = getCoverageBreakDown(coverage);
        String days = coverageBreakDown.get(dayIndex);
        String months = coverageBreakDown.get(monthIndex);
        String years = coverageBreakDown.get(yearIndex);
        this.period = getPeriod(days, months, years);
        this.coverage = coverage;
    }

    /**
     * Returns true if a given string is a valid coverage declaration.
     */
    public static boolean isValidCoverage(String coverage) {
        requireNonNull(coverage);
        ArrayList<String> coverageBreakDown = getCoverageBreakDown(coverage);
        String days = coverageBreakDown.get(dayIndex);
        String months = coverageBreakDown.get(monthIndex);
        String years = coverageBreakDown.get(yearIndex);
        boolean emptyOrMissingAllPrefixes = days.equals("0") && months.equals("0") && years.equals("0");
        boolean matchesRegex = days.matches(VALIDATION_REGEX) && months.matches(VALIDATION_REGEX)
            && years.matches(VALIDATION_REGEX);
        if (!emptyOrMissingAllPrefixes && matchesRegex) {
            boolean isValidDay = Integer.parseInt(days) >= 0 && Integer.parseInt(days) <= 31;
            boolean isValidMonth = Integer.parseInt(months) >= 0 && Integer.parseInt(months) <= 12;
            boolean isValidYear = Integer.parseInt(years) >= 0;
            return isValidDay && isValidMonth && isValidYear;
        } else {
            return false;
        }
    }

    private static ArrayList<String> getCoverageBreakDown(String coverage) {
        ArrayList<String> coverageBreakDown = new ArrayList<>();
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(" " + coverage, PREFIX_DAYS, PREFIX_MONTHS, PREFIX_YEARS);
        String days = (argMultimap.getValue(PREFIX_DAYS).isPresent() ? argMultimap.getValue(PREFIX_DAYS).get() : "0");
        String months = (argMultimap.getValue(PREFIX_MONTHS).isPresent()
            ? argMultimap.getValue(PREFIX_MONTHS).get() : "0");
        String years = (argMultimap.getValue(PREFIX_YEARS).isPresent()
            ? argMultimap.getValue(PREFIX_YEARS).get() : "0");
        coverageBreakDown.add(days);
        coverageBreakDown.add(months);
        coverageBreakDown.add(years);
        return coverageBreakDown;
    }

    public Period getPeriod(String days, String months, String years) {
        int numberOfDays = Integer.parseInt(days);
        int numberOfMonths = Integer.parseInt(months);
        int numberOfYears = Integer.parseInt(years);
        return Period.of(numberOfYears, numberOfMonths, numberOfDays);
    }

    @Override
    public String toString() {
        return coverage;
    }

    /**
     * Returns the date in a format that is readable.
     */
    public String toReadableString() {
        ArrayList<String> coverageBreakDown = getCoverageBreakDown(coverage);
        String days = coverageBreakDown.get(dayIndex);
        String months = coverageBreakDown.get(monthIndex);
        String years = coverageBreakDown.get(yearIndex);
        return years + " years, " + months + " months, " + days + " days";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Coverage // instanceof handles nulls
            && coverage.equals(((Coverage) other).coverage)); // state check
    }

    @Override
    public int hashCode() {
        return coverage.hashCode();
    }

}
