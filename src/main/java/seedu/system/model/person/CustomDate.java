package seedu.system.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.system.commons.util.AppUtil.checkArgument;

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
    private final String date;
    private Date dateObj;

    public CustomDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date.trim()), MESSAGE_CONSTRAINTS);
        this.date = date.trim();
        try {
            dateObj = new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            dateObj = null;
        }
    }

    /**
     * Returns a {@code currDate} which is a CustomDate object which has information about today's date.
     */
    public static CustomDate obtainCurrentDate () {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date currentDate = new Date();
        CustomDate currDate = new CustomDate(formatter.format(currentDate));
        return currDate;
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

    public Date getDate() {
        return dateObj;
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
