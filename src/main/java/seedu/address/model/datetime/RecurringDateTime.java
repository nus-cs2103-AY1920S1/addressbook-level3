package seedu.address.model.datetime;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

/**
 * Represents the frequency of a recurring appointment
 */
public class RecurringDateTime {

    public static final String MESSAGE_CONSTRAINTS = "Recurring " + DateTime.MESSAGE_CONSTRAINTS_BODY;

    private final long days;
    private final long months;
    private final long years;
    private final long hours;
    private final long minutes;

    /**
     * Constructs an {@code RecurringDateTime}.
     *
     * @param days, months, years, hours, minutes
     */
    public RecurringDateTime(long days, long months, long years, long hours, long minutes) {
        requireAllNonNull(days, months, years, hours, minutes);
        this.days = days;
        this.months = months;
        this.years = years;
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Gets recurring appointment's next date and time based on current one
     */
    public DateTime getNextAppointmentDateTime(StartDateTime currentAppointmentDateTime) {
        LocalDateTime nextAppointmentDateTime =
            currentAppointmentDateTime.dateTime
                    .plusYears(years)
                    .plusMonths(months)
                    .plusDays(days)
                    .plusHours(hours)
                    .plusMinutes(minutes);

        return new DateTime(nextAppointmentDateTime.toString());
    }
}
