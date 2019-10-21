package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    //public final String fullDate;
    private LocalDate date;
    private String fullTime;
    private static DateTimeFormatter INPUTFORMATTER = new DateTimeFormatterBuilder()
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

    private static DateTimeFormatter OUTPUTFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */

    /**
     * Converts String to LocalDate
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
        //checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.date = date;
        parseDate();
    }
    private void parseDate() {
        fullTime = date.format(OUTPUTFORMATTER);
    }
    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test == null; // put this for now
        //return test.matches(VALIDATION_REGEX);
    }

    public LocalDate getDate() {
        return date;
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
