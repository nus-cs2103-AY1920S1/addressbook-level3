package seedu.address.model.finance.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The date the repayment of the associated log entry was made.
 * Used in BORROW and LEND log entry types.
 * Guarantees: is valid as declared in {@link #isValidRepaidDate(String)}
 */
public class RepaidDate {


    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the form DD-MM-YYYY and should not be before transaction dates";

    public final String value;

    /**
     * Constructs a {@code RepaidDate}.
     *
     * @param repaidDate A valid transaction date.
     */
    public RepaidDate(String repaidDate, String tDate) {
        requireNonNull(repaidDate);
        checkArgument(isValidRepaidDate(repaidDate, tDate), MESSAGE_CONSTRAINTS);
        value = repaidDate;
    }

    public RepaidDate() {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");;
        value = validFormat.format(new Date());
    }

    /**
     * Returns true if a given string is a valid transaction date.
     */
    public static boolean isValidRepaidDate(String test, String tDate) {
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");;
        validFormat.setLenient(false); // date has to exist in calendar (i.e. not 31 Feb)
        try {
            Date testDate = validFormat.parse(test);
            Date transactionDate = validFormat.parse(tDate);
            boolean isSameDay = testDate.equals(transactionDate);
            boolean isDayAfter = testDate.after(transactionDate);
            return isSameDay || isDayAfter;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepaidDate // instanceof handles nulls
                && value.equals(((RepaidDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
