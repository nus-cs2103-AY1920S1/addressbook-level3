package seedu.address.model.incident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * Represents an Incident's IncidentDateTime in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncidentDateTimeFormat(String)}
 */
public class IncidentDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "IncidentDateTimes should only take in a LocalDateTime string with Medium Date and Time format and it"
                    + " should not be blank. E.g. 'Dec 20, 2016, 2:30:40 PM'";

    // format style settings
    private static final FormatStyle dateTimeStyle = FormatStyle.MEDIUM;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle);

    public final LocalDateTime incidentDateTime;

    /**
     * Constructs a {@code IncidentDateTime}.
     *
     * @param dateTimeString A valid ISO DateTime format string.
     */
    public IncidentDateTime(String dateTimeString) {
        requireNonNull(dateTimeString);
        checkArgument(isValidIncidentDateTimeFormat(dateTimeString), MESSAGE_CONSTRAINTS);
        this.incidentDateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Constructs a {@code IncidentDateTime} for right now.
     */
    public IncidentDateTime() {
        this.incidentDateTime = LocalDateTime.now();
    }

    /**
     * Returns true if a given string is in a valid DateTime format.
     */
    public static boolean isValidIncidentDateTimeFormat(String test) {
        try {
            LocalDateTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public int getMonth() {
        return incidentDateTime.getMonthValue();
    }

    public int getYear() {
        return incidentDateTime.getYear();
    }

    @Override
    public String toString() {
        return incidentDateTime.format(formatter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IncidentDateTime // instanceof handles nulls
                && incidentDateTime.equals(((IncidentDateTime) other).incidentDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return incidentDateTime.hashCode();
    }

}

