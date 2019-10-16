package seedu.address.model.date;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents date used in attendance and performance recording.
 */
public class TrainingDate {

    private static final String WRONG_DATE_FORMAT = "Invalid date specified.";

    private int day;
    private int month;
    private int year;

    public TrainingDate(String date) throws ParseException {
        requireNonNull(date);
        processDate(date);
    }

    /**
     * Parses {@code date} and extracts the day, month and year.
     * @param date Date input by user.
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
            throw new ParseException(WRONG_DATE_FORMAT);
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
}
