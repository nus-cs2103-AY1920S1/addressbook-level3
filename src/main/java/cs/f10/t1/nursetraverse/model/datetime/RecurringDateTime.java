package cs.f10.t1.nursetraverse.model.datetime;

import static cs.f10.t1.nursetraverse.model.datetime.DateTime.DATE_DISPLAY_FORMATTER;

import java.time.LocalDateTime;
import java.util.Arrays;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;

/**
 * Represents the frequency of a recurring appointment
 */
public class RecurringDateTime {

    public static final String MESSAGE_CONSTRAINTS = "Recurring " + DateTime.MESSAGE_CONSTRAINTS_BODY;
    public static final int EXPECTED_FREQUENCY_ARRAY_LENGTH = 6;

    private final long years;
    private final long months;
    private final long weeks;
    private final long days;
    private final long hours;
    private final long minutes;
    private final Long[] freqArray;

    /**
     * Constructs an {@code RecurringDateTime}.
     *
     * @param freqArray
     */
    public RecurringDateTime(Long[] freqArray) {
        CollectionUtil.requireAllNonNull(Arrays.asList(freqArray));
        this.years = freqArray[0];
        this.months = freqArray[1];
        this.weeks = freqArray[2];
        this.days = freqArray[3];
        this.hours = freqArray[4];
        this.minutes = freqArray[5];
        this.freqArray = freqArray;
    }

    public Long getYears() {
        return years;
    }

    public Long getMonths() {
        return months;
    }

    public Long getWeeks() {
        return weeks;
    }

    public Long getDays() {
        return days;
    }

    public Long getHours() {
        return hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public Long[] getFreqArray() {
        return freqArray;
    }

    /**
     * Converts the JSON storage String to an array of type long
     * @return Long[] array
     */
    public static Long[] frequencyStringToLong(String freq) {
        Long[] freqLongArray = Arrays.asList(freq.split("\n"))
                .stream()
                .map(x -> Long.parseLong(x))
                .toArray(Long[]::new);
        return freqLongArray;
    }

    /**
     * @return boolean, which is true if frequency is valid
     */
    public static boolean isValidSingleFrequency(String freq) {
        Long freqLong = Long.parseLong(freq);
        return freqLong >= 0;
    }

    /**
     * @return boolean, which is true if frequency is valid
     */
    public static boolean isValidFrequency(Long[] freq) {
        return freq.length == EXPECTED_FREQUENCY_ARRAY_LENGTH;
    }

    /**
     * @return boolean, which is true if frequency is non-zero
     */
    public boolean isRecurringFrequency() {
        int numZeros = 0;
        for (int i = 0; i < EXPECTED_FREQUENCY_ARRAY_LENGTH; i++) {
            if (freqArray[i] == 0) {
                numZeros++;
            }
        }
        return (numZeros != EXPECTED_FREQUENCY_ARRAY_LENGTH);
    }

    /**
     * @return Long, which is 0 if string passed in is null
     */
    public static Long getSingleFrequencyAsLong(String freq) {
        return Long.parseLong(freq);
    }

    /**
     * Gets recurring appointment's next date and time based on current one
     */
    public String getNextAppointmentDateTime(DateTime currentAppointmentDateTime) {
        LocalDateTime nextAppointmentLocalDateTime =
            currentAppointmentDateTime.dateTime
                    .plusYears(years)
                    .plusMonths(months)
                    .plusWeeks(weeks)
                    .plusDays(days)
                    .plusHours(hours)
                    .plusMinutes(minutes);

        return nextAppointmentLocalDateTime.format(DATE_DISPLAY_FORMATTER);
    }

    /**
     * Returns true if both frequencies have the same identity and data fields.
     * This defines a stronger notion of equality between two frequencies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RecurringDateTime)) {
            return false;
        }

        RecurringDateTime otherFrequency = (RecurringDateTime) other;

        return otherFrequency.getYears().equals(getYears())
                && otherFrequency.getMonths().equals(getMonths())
                && otherFrequency.getWeeks().equals(getWeeks())
                && otherFrequency.getDays().equals(getDays())
                && otherFrequency.getHours().equals(getHours())
                && otherFrequency.getMinutes().equals(getMinutes());
    }

    /**
     * Converts recurring date time to a string to be displayed in the staged appointments.
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(years == 0 ? "" : years + (years == 1 ? " year" : " years"))
                .append(months == 0 ? "" : ", " + months + (months == 1 ? " month" : ", months"))
                .append(days == 0 ? "" : ", " + days + (days == 1 ? ", day" : ", days"))
                .append(hours == 0 ? "" : ", " + hours + (hours == 1 ? ", hour" : ", hours"))
                .append(minutes == 0 ? "" : ", " + minutes + (minutes == 1 ? " minute" : " minutes"));
        return builder.toString();
    }
}
