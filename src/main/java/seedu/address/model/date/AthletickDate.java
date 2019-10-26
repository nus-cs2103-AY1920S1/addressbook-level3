package seedu.address.model.date;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents date used in attendance and performance recording.
 */
public class AthletickDate {

    public static final String MESSAGE_CONSTRAINTS = "Please specify date in either MMYYYY or "
            + "DDMMYYYY format.";
    private static final String WRONG_DATE_FORMAT = "Invalid date specified.";

    private int day;
    private int month;
    private int year;
    private int type;
    private String mth;

    public AthletickDate(String date) throws ParseException {
        requireNonNull(date);
        processDate(date);
    }

    /**
     * Parses {@code date} and extracts the day, month and year
     * @param date Date input by user
     * @throws ParseException if {@code date} specified by user is invalid.
     */
    private void processDate(String date) throws ParseException {
        SimpleDateFormat fullDate = new SimpleDateFormat("ddMMyyyy");
        fullDate.setLenient(false);
        SimpleDateFormat monthYear = new SimpleDateFormat("MMyyyy");
        monthYear.setLenient(false);
        try {
            if (date.length() == 8) {
                Date d = fullDate.parse(date);
                day = Integer.parseInt(new SimpleDateFormat("d").format(d));
                month = Integer.parseInt(new SimpleDateFormat("M").format(d));
                year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d));
                mth = new SimpleDateFormat("MMMM").format(d);
                type = 1;
            } else if (date.length() == 6) {
                Date d2 = monthYear.parse(date);
                day = Integer.parseInt("0");
                month = Integer.parseInt(new SimpleDateFormat("M").format(d2));
                year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d2));
                mth = new SimpleDateFormat("MMMM").format(d2);
                type = 2;
            }
        } catch (java.text.ParseException pe) {
            throw new ParseException(WRONG_DATE_FORMAT + " " + MESSAGE_CONSTRAINTS);
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

    public int getType() {
        return type;
    }

    public String getMth() {
        return mth;
    }
}
