package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The date the transaction of the associated log entry was made.
 * Guarantees: is valid as declared in {@link #isValidTransactionDate(String)}
 */
public class TransactionDate {


    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the form DD-MM-YYYY and should not be in the future";

    public final String value;

    /**
     * Constructs a {@code TransactionDate}.
     *
     * @param transactionDate A valid transaction date.
     */
    public TransactionDate(String transactionDate) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        requireNonNull(transactionDate);
        checkArgument(isValidTransactionDate(transactionDate), MESSAGE_CONSTRAINTS);
        value = transactionDate;
    }

    public TransactionDate() {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
        value = validFormat.format(new Date());
    }

    /**
     * Returns true if a given string is a valid transaction date.
     */
    public static boolean isValidTransactionDate(String test) {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");;
        validFormat.setLenient(false); // date has to exist in calendar (i.e. not 31 Feb)
        Date currentDate = new Date();
        try {
            Date testDate = validFormat.parse(test);
            boolean isToday = testDate.equals(currentDate);
            boolean isPast = testDate.before(currentDate);
            return isToday || isPast;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns whether date is earlier, later or same to another date
     * Sorts such that later dates come first.
     */
    public int compareTo(TransactionDate other) {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date otherDate = validFormat.parse(other.value);
            Date thisDate = validFormat.parse(value);
            return -thisDate.compareTo(otherDate);
        } catch (ParseException e) {
            // Don't sort
            return 0;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionDate // instanceof handles nulls
                && value.equals(((TransactionDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
