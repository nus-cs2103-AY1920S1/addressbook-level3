package seedu.guilttrip.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

import seedu.guilttrip.model.util.Frequency;

/**
 * Represents a Date in the guiltTrip. Guarantees: immutable; is
 * valid as declared in {@link #parseIfValidDate(String)} (@link #checkIfWithinTime(LocalDate)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS_FOR_ENTRIES = "Date is not in a valid format. Below are some of the "
            + "possible formats. \n [yyyy MM dd] , [yyyy/MM/dd], [dd/MM/yyyy]. \n Date also must not exceed the maximum"
            + " allowed days allowed for the month";

    public static final String MESSAGE_CONSTRAINTS_FOR_STATS = "Date is not in a valid format. Below are some of the "
            + "possible formats. \n [yyyy-MM] , [yyyy MM]. \n Date also cannot exceed the maximum allowed "
            + "months allowed for the year and must only contain in the format [yyyy-MM] without days.";

    public static final String MESSAGE_CONSTRAINTS_FOR_DATES = "Oops! We only allow dates between 2000-01-01 "
            + "and 2100-12-31.";

    private static final LocalDate START_DATE = LocalDate.of(1999, 12, 31);
    private static final LocalDate END_DATE = LocalDate.of(2101, 1, 1);

    /*
     * The first character of the guilttrip must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    private static final DateTimeFormatter INPUTFORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("uuuu MM dd"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu/MM/dd"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu/MM/d"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-d"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu.MM.dd"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu.MM.d"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/M/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d/MM/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d-MM-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d.MM.uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/M"))
            .appendOptional(DateTimeFormatter.ofPattern("d/MM"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM"))
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter();

    private static final DateTimeFormatter INPUTFORMATTERWITHRESOLVER = INPUTFORMATTER
            .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter OUTPUTFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter INPUTMONTHFORMATTER = new DateTimeFormatterBuilder()
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1).appendOptional(DateTimeFormatter.ofPattern("MM/uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("MM-uuuu")).appendOptional(DateTimeFormatter.ofPattern("MM/uu"))
            .appendOptional(DateTimeFormatter.ofPattern("M/uuuu")).appendOptional(DateTimeFormatter.ofPattern("M-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("M/uu")).appendOptional(DateTimeFormatter.ofPattern("uu/mm"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu/MM"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu MM"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM")).toFormatter();

    private LocalDate date;
    private String fullTime;

    /**
     * Converts String to LocalDate
     *
     * @param date in the format yyyy mm dd.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(parseIfValidDate(date), MESSAGE_CONSTRAINTS_FOR_ENTRIES);
        LocalDate ldt = LocalDate.parse(date, INPUTFORMATTERWITHRESOLVER);
        this.date = ldt;
        checkArgument(checkIfWithinTime(ldt), MESSAGE_CONSTRAINTS_FOR_DATES);
        parseDate();
    }

    /**
     * Converts String to LocalDate. Pads the month with a dummy day 1 value.
     *
     * @param date in the format yyyy mm dd.
     */
    public Date(String date, boolean isMonth) {
        requireNonNull(date);
        checkArgument(parseIfValidMonth(date), MESSAGE_CONSTRAINTS_FOR_STATS);
        LocalDate ldt = LocalDate.parse(date, INPUTMONTHFORMATTER);
        checkArgument(checkIfWithinTime(ldt), MESSAGE_CONSTRAINTS_FOR_DATES);
        this.date = ldt;
    }

    public Date(LocalDate date) {
        requireNonNull(date);
        // checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.date = date;
        parseDate();
    }

    /**
     * Checks if the date does not exceed the allowed date as well as in the correct format.
     * @param test the date to check.
     * @return boolean if the date is valid.
     */
    public static boolean parseIfValidDate(String test) {
        try {
            LocalDate.parse(test, INPUTFORMATTERWITHRESOLVER);
            return true;
        } catch (DateTimeParseException pe) {
            return false;
        }
    }

    /**
     * Checks if the date is within the specified range of time.
     * @param testDate the date to check.
     * @return boolean if the date is valid.
     */
    public static boolean checkIfWithinTime(LocalDate testDate) {
        return testDate.isAfter(START_DATE) && testDate.isBefore(END_DATE);
    }

    /**
     * Checks if the month does not exceed the allowed months as well as in the correct format.
     * @param test the date to check.
     * @return boolean if the month is valid.
     */
    public static boolean parseIfValidMonth(String test) {
        try {
            LocalDate.parse(test, INPUTMONTHFORMATTER);
            return true;
        } catch (DateTimeParseException pe) {
            return false;
        }
    }

    private void parseDate() {
        fullTime = date.format(OUTPUTFORMATTER);
    }

    public LocalDate getDate() {
        return date;
    }

    public static Date now() {
        return new Date(LocalDate.now());
    }

    public Date plus(Frequency freq) {
        return new Date(this.getDate().plus(freq.getPeriod()));
    }

    /**
     * Adds a specified number of days/ months/ years to a Date
     *
     * @param period Period
     * @return Date new Date after the addition of the period
     */
    public Date plus(Period period) {
        LocalDate newDate;
        long duration = period.getDuration();
        switch (period.getInterval()) {
        case 'd':
            newDate = date.plusDays(duration);
            break;
        case 'm':
            newDate = date.plusMonths(duration);
            break;
        case 'y':
            newDate = date.plusYears(duration);
            break;
        default:
            newDate = date;
        }
        return new Date(newDate);
    }

    public Date minus(Frequency freq) {
        return new Date(this.getDate().minus(freq.getPeriod()));
    }

    /**
     * Removes a specified number of days/ months/ years to a Date
     *
     * @param period Period
     * @return Date new Date after the addition of the period
     */
    public Date minus(Period period) {
        LocalDate newDate;
        long duration = period.getDuration();
        switch (period.getInterval()) {
        case 'd':
            newDate = date.minusDays(duration);
            break;
        case 'm':
            newDate = date.minusMonths(duration);
            break;
        case 'y':
            newDate = date.minusYears(duration);
            break;
        default:
            newDate = date;
        }
        return new Date(newDate);
    }

    public boolean isBefore(Date other) {
        return date.isBefore(other.date);
    }

    public boolean isAfter(Date other) {
        return date.isAfter(other.date);
    }

    public boolean isEqual(Date other) {
        return date.isEqual(other.date);
    }

    public boolean lessThanOrEqualsToday() {
        return this.isBefore(now()) || this.equals(now());
    }

    @Override
    public String toString() {
        return fullTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && fullTime.equals(((Date) other).fullTime)); // state check
    }

    @Override
    public int hashCode() {
        return fullTime.hashCode();
    }

}
