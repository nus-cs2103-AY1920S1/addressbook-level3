package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Date in the DiaryEntry.
 */
public class Date {


    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be of the format dd/mm/yyyy HHmm";
    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param phone A valid phone number.
     */
    public Date(String date) {
        requireNonNull(date);
        value = date;
    }

    /**
     */

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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
