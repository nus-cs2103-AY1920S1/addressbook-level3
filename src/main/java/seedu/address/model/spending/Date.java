package seedu.address.model.spending;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Spending's Date in MoneyGoWhere.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {


    public static final String MESSAGE_CONSTRAINTS =
            "Date numbers can be today, yesterday, tomorrow or a formal date DD/MM/YYYY.";
    //public static final String VALIDATION_REGEX = "([0-9]{1,2})([/\\-])([0-9]{1,2})([/\\-])([0-9]{4})";
    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);

        try {
            java.util.Date parsedDate = DateUtil.parseDate(date);
            value = DateUtil.formatDate(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return DateUtil.isValidDate(test);
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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
