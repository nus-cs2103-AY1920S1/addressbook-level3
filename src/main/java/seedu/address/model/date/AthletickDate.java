package seedu.address.model.date;

import static java.util.Objects.requireNonNull;

/**
 * Represents date used in attendance and performance recording.
 */
public class AthletickDate {

    public static final String DATE_FORMAT = "DDMMYYYY";
    public static final String MESSAGE_CONSTRAINTS = "Please specify date in either MMYYYY or "
            + "DDMMYYYY format.";
    public static final String WRONG_DATE_FORMAT = "Invalid date specified.";

    private int day;
    private int month;
    private int year;
    private int type;
    private String mth;

    public AthletickDate(int day, int month, int year, int type, String mth) {
        requireNonNull(mth);
        this.day = day;
        this.month = month;
        this.year = year;
        this.type = type;
        this.mth = mth;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getType() {
        return type;
    }

    public String getMth() {
        return mth;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * Retrieves the suffix of this day.
     */
    private String getDaySuffix() {
        if (day == 1 || day == 21 || day == 31) {
            return "st";
        } else if (day == 2 || day == 22) {
            return "nd";
        } else if (day == 3 || day == 23) {
            return "rd";
        } else {
            return "th";
        }
    }

    /**
     * Retrieves the date in the format before it was parsed - for Json use.
     */
    public String getUnparsed() {
        return String.format("%02d", day) + String.format("%02d", month) + year;
    }

    @Override
    public String toString() {
        return day + getDaySuffix() + " " + mth + " " + year;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AthletickDate
                && day == ((AthletickDate) other).day
                && month == ((AthletickDate) other).month
                && year == ((AthletickDate) other).year
                && type == ((AthletickDate) other).type
                && mth.equals(((AthletickDate) other).mth));
    }
}
