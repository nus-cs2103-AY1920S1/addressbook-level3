package dukecooks.model.health.components;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import dukecooks.commons.util.AppUtil;
import dukecooks.model.Date;
import dukecooks.model.Time;

/**
 * Represents timestamp which is a combination of date and time.
 * In the format of DD/MM/YYYY HH:MM.
 */
public class Timestamp {
    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp should only contain numeric characters of a valid date and time "
                    + "\nin the format of DD/MM/YYYY HH:mm, "
                    + "and it should not be blank";

    public static final String DATETIME_VALIDATION_REGEX =
            "(\\d{1,2})[\\/](\\d{1,2})[\\/](\\d{4})(\\s+)(\\d{2})[\\:](\\d{2})";

    private Date date;
    private Time time;

    /**
     * Constructs a {@code timestamp}.
     * @param timestamp A valid timestamp in String form.
     */
    public Timestamp(String timestamp) {
        requireNonNull(timestamp);
        AppUtil.checkArgument(isValidDateTime(timestamp));
        breakdown(timestamp);
    }

    /**
     * Deconstructs a timestamp into more granular components: Date and Time.
     */
    void breakdown(String timestamp) {
        String[] dt = timestamp.split(" ");

        this.date = Date.generateDate(dt[0]);
        this.time = Time.generateTime(dt[1]);
    }

    public Date getDate() {
        return this.date;
    }

    public Time getTime() {
        return this.time;
    }

    /**
     * Returns true if a given string is a valid timestamp.
     */
    public static boolean isValidDateTime(String timestamp) {
        String[] dt = timestamp.split(" ");
        return timestamp.matches(DATETIME_VALIDATION_REGEX) && Date.isValidDate(dt[0]) && Time.isValidTime(dt[1]);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDate().toString())
                .append(" ")
                .append(getTime().toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Timestamp otherTime = (Timestamp) other;
        return (otherTime.getDate().equals(getDate()))
                && (otherTime.getTime().equals(getTime()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

}
