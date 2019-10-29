package seedu.address.model.date;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents date used in attendance and performance recording.
 */
public class AthletickDate {

    public static final String DATE_FORMAT = "DDMMYYYY";
    private static final String WRONG_DATE_FORMAT = "Invalid date specified.";

    private int day;
    private int month;
    private int year;

    public AthletickDate(String date) throws ParseException {
        requireNonNull(date);
        processDate(date);
    }

    /**
     * Parses {@code date} and extracts the day, month and year
     *
     * @param date Date input by user
     * @throws ParseException if {@code date} specified by user is invalid.
     */
    private void processDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        simpleDateFormat.setLenient(false);
        try {
            Date d = simpleDateFormat.parse(date);
            day = Integer.parseInt(new SimpleDateFormat("d").format(d));
            month = Integer.parseInt(new SimpleDateFormat("M").format(d));
            year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d));
        } catch (java.text.ParseException pe) {
            throw new ParseException(WRONG_DATE_FORMAT + "\n" + "Date must be in the format:" + DATE_FORMAT);
        }
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

    @Override
    public String toString() {
        return String.format("%d%d%d", day, month, year);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof AthletickDate)) {
            return false;
        } else {
            AthletickDate otherDate = (AthletickDate) other;
            return (this.day == otherDate.getDay()) && (this.month == otherDate.getMonth())
                    && (this.year == otherDate.getYear());
        }
    }
}
