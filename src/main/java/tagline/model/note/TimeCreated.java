package tagline.model.note;

import static java.util.Objects.requireNonNull;

import java.time.Instant;


/**
 * Represents a Note's time when it is created
 * Guarantees: immutable; is valid as declared in @link #(String)}
 */
public class TimeCreated {

    public static final String MESSAGE_CONSTRAINTS = "TimeCreated can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final Date date;

    /**
     * Constructs an {@code TimeCreated}.
     *
     */
    public TimeCreated() {
        date = new Date(Instant.now());
    }

    public TimeCreated(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    public Date getTime() {
        return this.date;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeCreated// instanceof handles nulls
                && date.equals(((TimeCreated) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
