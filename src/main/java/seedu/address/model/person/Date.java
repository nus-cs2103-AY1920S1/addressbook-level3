package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import seedu.address.model.util.Frequency;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS_FOR_ENTRIES = "Date is not in a valid format. Below are some of the "
            + "possible formats. [yyyy MM dd] , [yyyy/MM/dd], [dd/MM/yyyy]";

    public static final String MESSAGE_CONSTRAINTS_FOR_STATS = "Date is not in a valid format. Below are some of the "
            + "possible formats. [yyyy-MM] , [yyyy MM]";
    /*
     * The first character of the address must not be a whitespace, otherwise " " (a
     * blank string) becomes a valid input.
     */
    private static final DateTimeFormatter INPUTFORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy MM dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/d"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-d"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.d"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/M/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d/M/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d.MM.yyyy")).toFormatter();

    private static final DateTimeFormatter OUTPUTFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter INPUTMONTHFORMATTER = new DateTimeFormatterBuilder()
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1).appendOptional(DateTimeFormatter.ofPattern("MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("MM-yyyy")).appendOptional(DateTimeFormatter.ofPattern("MM/yy"))
            .appendOptional(DateTimeFormatter.ofPattern("M/yyyy")).appendOptional(DateTimeFormatter.ofPattern("M-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("M/yy")).appendOptional(DateTimeFormatter.ofPattern("yy/MM"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy MM"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM")).toFormatter();

    private LocalDate date;
    private String fullTime;


    //TODO: Hacky way of ensuring date is correct
    /**
     * Converts String to LocalDate
     *
     * @param date in the format yyyy mm dd.
     */
    public Date(String date) {
        requireNonNull(date);
        try {
            LocalDate ldt = LocalDate.parse(date, INPUTFORMATTER);
            this.date = ldt;
            parseDate();
        } catch (Exception pe) {
            checkArgument(false, MESSAGE_CONSTRAINTS_FOR_ENTRIES);
        }
    }

    public Date(String date, boolean isMonth) {
        requireNonNull(date);
        try {
            LocalDate ldt = LocalDate.parse(date, INPUTMONTHFORMATTER);
            this.date = ldt;
        } catch (Exception pe) {
            checkArgument(false, MESSAGE_CONSTRAINTS_FOR_STATS);
        }
    }

    public Date(LocalDate date) {
        requireNonNull(date);
        // checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.date = date;
        parseDate();
    }

    private void parseDate() {
        fullTime = date.format(OUTPUTFORMATTER);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        return test != null; // put this for now
        // return test.matches(VALIDATION_REGEX);
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
