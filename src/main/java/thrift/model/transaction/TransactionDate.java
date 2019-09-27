package thrift.model.transaction;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents the Date whereby the Transaction is created on.
 */
public class TransactionDate {

    public static final String DATE_CONSTRAINTS =
            "Date should be specified in dd/MM/yyyy format";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    private Date date;
    private String rawDate;

    /**
     * Constructs a {@code TransactionDate}.
     *
     * @param date Datestamp for the Transaction.
     * @throws ParseException If invalid date String is supplied, but it will be handled in
     * {@link #isValidDate(String)}.
     */
    public TransactionDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), DATE_CONSTRAINTS);
        try {
            this.date = DATE_FORMATTER.parse(date);
            rawDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the supplied Date string is valid.
     *
     * @param date Date string to check for validity.
     * @return true if the supplied string is a valid Date.
     */
    public static boolean isValidDate(String date) {
        try {
            DATE_FORMATTER.setLenient(false);
            DATE_FORMATTER.parse(date);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Gets the Date object for the Transaction.
     *
     * @return Date object belonging to the Transaction.
     */
    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return rawDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionDate // instanceof handles nulls
                && date.equals(((TransactionDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
