package seedu.address.model.commonvariables;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@@author{weigenie}
/**
 * Represents a claim's/income's date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date entered should be a valid date in the form of dd-MM-yyyy";
    public static final String VALIDATION_REGEX = "(31[\\-.](0[13578]|1[02])[\\-.](18|19|20)[0-9]{2})|"
            + "((29|30)[\\-.](01|0[3-9]|1[1-2])[\\-.](18|19|20)[0-9]{2})|((0[1-9]|1[0-9]|2[0-8])[\\-.]"
            + "(0[1-9]|1[0-2])[\\-.](18|19|20)[0-9]{2})|(29[\\-.](02)[\\-.](((18|19|20)"
            + "(04|08|[2468][048]|[13579][26]))|2000))";
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final String text;
    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.text = date;
        this.date = LocalDate.parse(date, format);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return format.format(date);
    }

    public LocalDate getLocalDate() {
        return this.date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
