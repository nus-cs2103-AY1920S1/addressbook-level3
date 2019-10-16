package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a CustomDate object based on DATE_FORMAT
 */
public class CustomDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should follow the following format DD/MM/YYYY.";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public final String date;

    public CustomDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date.trim()), MESSAGE_CONSTRAINTS);
        this.date = date.trim();
    }

    /**
     * Returns true if a given string has a valid date format.
     */
    public static boolean isValidDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            Date dateObj = format.parse(date);
            if (date.equals(format.format(dateObj))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns true if both customs have the same date string.
     *
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CustomDate)) {
            return false;
        }

        CustomDate otherDate = (CustomDate) other;
        return this.date.equals(otherDate.toString());
    }

    @Override
    public String toString() {
        return this.date;
    }
}
