package mams.commons.core.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple class wrapper around {@code java.util.Date}
 * and {@code java.text.SimpleDataFormat}
 * for basic encapsulation of
 * time handling in MAMS.
 */
public class TimeStamp {
    public static final String TIME_STAMP_FORMAT_STRING = "[yyyy-MM-dd|HH:mm:ss]";
    public static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat(TIME_STAMP_FORMAT_STRING);

    private final Date date;

    public TimeStamp(Date date) {
        // defensive copy
        this.date = new Date(date.getTime());
    }

    public TimeStamp(long unixTimeInMilliSeconds) {
        this(new Date(unixTimeInMilliSeconds));
    }

    public TimeStamp() {
        this.date = new Date();
    }

    /**
     * Returns Unix Time representation of {@code date} in milliseconds. This is important for Json
     * loading/saving since {@code Date} compares equality using its internal unix time.
     * If we save TimeStamp directly as its String form as defined in {@code TIME_STAMP_FORMAT}
     * we lose some precision when parsing it back to Date, hence resulting in two {@code TimeStamps}
     * with similar #toString() output, but #equals returns false since the unix time of both
     * dates are different.
     * @return
     */
    public long asUnixTime() {
        return date.getTime();
    }

    /**
     * Returns the date and time in a standardized format for MAMS usage.
     *
     * @return string representation of TimeStamp object
     */
    @Override
    public String toString() {
        return TIME_STAMP_FORMAT.format(this.date);
    }

    @Override
    public int hashCode() {
        return date.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof TimeStamp)) {
            return false;
        }

        // state check
        TimeStamp ts = (TimeStamp) obj;
        return this.date.toString().equals(ts.date.toString());
    }
}
