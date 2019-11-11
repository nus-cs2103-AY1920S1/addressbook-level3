package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Date of an expense.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain numerals and be written in the DDMMYYYY format.\n"
                    + "Valid formats: 25102019\n"
                    + "Invalid formats: 25/10/2019 or 25-10-2019 or others..";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])(1[0-2]|0[1-9])([1-3])[0-9]{3}$";
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyyy");
    public final String value;
    public final String storageDate;
    private java.util.Date valueToCompare;

    public Date(String date) {
        requireNonNull(date);
        assert isValidDate(date) : "date format should be always correct";
        try {
            this.valueToCompare = sdfDate.parse(date);
        } catch (ParseException e) {
            assert false : "date format should be always correct";
        }
        this.storageDate = date;
        this.value = formatDate(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            String day = test.substring(0, 2);
            String month = test.substring(2, 4);
            String year = test.substring(4);
            if (day.equals("29") && month.equals("02")) {
                int numYear = Integer.parseInt(year);
                if (numYear % 400 == 0) {
                    return true;
                } else if (numYear % 100 == 0) {
                    return false;
                } else {
                    return numYear % 4 == 0;
                }
            } else if ((day.equals("30") || day.equals("31")) && month.equals("02")) {
                return false;
            } else if (day.equals("31") && (month.equals("04") || month.equals("06")
                    || month.equals("09") || month.equals("11"))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Formats the date in the event to dd/MM/yyyy.
     * @param date attribute in the given event.
     * @return formatted date based on dd/MM/yyyy.
     */
    private String formatDate(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(2, 4);
        String year = date.substring(4);
        return day + "/" + month + "/" + year;
    }

    public static Date getCurrentDate() {
        java.util.Date now = new java.util.Date();
        String value = sdfDate.format(now);
        return new Date(value);
    }

    public java.util.Date getDateToCompare() {
        return valueToCompare;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }
}
