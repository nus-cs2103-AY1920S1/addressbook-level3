package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import seedu.address.model.util.Frequency;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should only contain alphanumeric characters and spaces, and it should not be blank";

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
            .appendOptional(DateTimeFormatter.ofPattern("d/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("d.MM.yyyy"))
            .toFormatter();

    private static final DateTimeFormatter OUTPUTFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate date;
    private String fullTime;

    /**
     * Converts String to LocalDate
     *
     * @param date in the format yyyy mm dd.
     */
    public Date(String date) {
        requireNonNull(date);
        //checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        LocalDate ldt = LocalDate.parse(date, INPUTFORMATTER);
        this.date = ldt;
        parseDate();
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

    public Date plus(Period period) {
        LocalDate newDate;
        long duration = period.getDuration();
        switch(period.getInterval()) {
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

    @Override
    public String toString() {
        return fullTime;
        //date.toString();
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
