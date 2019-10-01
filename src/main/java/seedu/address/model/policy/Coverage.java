package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.Period;

/**
 * Represents a Policy's coverage in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoverage(String, String, String)}
 */
public class Coverage {

    public static final String MESSAGE_CONSTRAINTS =
            "Coverage should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{N}] ]*";

    public final Period period;
    public final String coverage;

    /**
     * Constructs a {@code Coverage}.
     *
     * @param days A valid number of days the policy covers.
     * @param months A valid number of months the policy covers.
     * @param years A valid number of years the policy covers.
     */
    public Coverage(String days, String months, String years) {
        requireNonNull(days);
        requireNonNull(months);
        requireNonNull(years);
        checkArgument(isValidCoverage(days, months, years), MESSAGE_CONSTRAINTS);
        this.period = getPeriod(days, months, years);
        this.coverage = days + "." + months + "." + years;
    }

    public Period getPeriod(String days, String months, String years) {
        int numberOfDays = Integer.parseInt(days);
        int numberOfMonths = Integer.parseInt(months);
        int numberOfYears = Integer.parseInt(years);
        return Period.of(numberOfYears, numberOfMonths, numberOfDays);
    }

    /**
     * Returns true if a given string is a valid coverage declaration.
     */
    public static boolean isValidCoverage(String testDay, String testMonth, String testYear) {
        return testDay.matches(VALIDATION_REGEX) && testMonth.matches(VALIDATION_REGEX) && testYear.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        StringBuilder coverageString = new StringBuilder();
        boolean hasDaysSpecified = period.getDays() != 0;
        if (hasDaysSpecified) {
            coverageString.append(period.getDays() + " days");
        }

        boolean hasMonthsSpecified = period.getMonths() != 0;
        if (hasMonthsSpecified) {
            boolean isNotEmpty = coverageString.length() != 0;
            if (isNotEmpty) {
                coverageString.append(" ");
            }
            coverageString.append(period.getMonths() + " months");
        }

        boolean hasYearsSpecified = period.getYears() != 0;
        if (hasYearsSpecified) {
            boolean isNotEmpty = coverageString.length() != 0;
            if (isNotEmpty) {
                coverageString.append(" ");
            }
            coverageString.append(period.getYears() + " years");
        }
        return coverageString.toString();
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
