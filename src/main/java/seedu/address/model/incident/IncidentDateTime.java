package seedu.address.model.incident;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Incident's IncidentDateTime in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncidentDateTime(String)}
 */
public class IncidentDateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "IncidentDateTimes should only take in an ISO Date Time String, return LocalDateTime, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final LocalDateTime incidentDateTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Constructs a {@code IncidentDateTime}.
     *
     * @param dateTimeString A valid ISO DateTime format string.
     */
    public IncidentDateTime(String dateTimeString) {
        requireNonNull(dateTimeString);
//        checkArgument(isValidIncidentDateTime(dateTimeString), MESSAGE_CONSTRAINTS);
        this.incidentDateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Constructs a {@code IncidentDateTime} for right now.
     */
    public IncidentDateTime() {
        this.incidentDateTime = LocalDateTime.now();
    }

    /**
     * Returns true if a given string is a valid VehicleNumber.
     */
    public static boolean isValidIncidentDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
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

