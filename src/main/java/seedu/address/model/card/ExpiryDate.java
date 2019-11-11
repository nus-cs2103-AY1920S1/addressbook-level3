package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import seedu.address.commons.util.ExpiryUtil;

/**
 * Represents a Card's expiry date in the card book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry entered is invalid!\nIt should be in the format MM/YY, and it should not be blank";

    public static final String PAST_EXPIRY_ERROR =
            "You cannot add a card that has expired!";

    public static final String VALIDATION_REGEX = "(((0)[0-9])|((1)[0-2]))(\\/)\\d{2}";

    private static DateTimeFormatter dateTimeFormat = new DateTimeFormatterBuilder()
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .appendPattern("MM/yy")
            .toFormatter();

    public final String value;

    /**
     * Constructs an {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidDate(expiryDate), MESSAGE_CONSTRAINTS);
        this.value = expiryDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        return ExpiryUtil.getMonthToExp(test) > 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiryDate // instanceof handles nulls
                && value.equals(((ExpiryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
