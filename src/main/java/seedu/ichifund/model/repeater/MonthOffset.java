package seedu.ichifund.model.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Month Offset in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MonthOffset {

    public static final String MESSAGE_CONSTRAINTS =
        "Month offset should be an integer between 1 and 28 inclusive. A value of -1 indicates that the month "
        + "offset is ignored.";
    public static final String VALIDATION_REGEX = "(-1|[1-9]|1[0-9]|2[0-8])";
    public static final MonthOffset MONTH_OFFSET_IGNORED = new MonthOffset("-1");
    public static final MonthOffset MONTH_OFFSET_DEFAULT = new MonthOffset("-1");

    public final Integer value;

    public MonthOffset(String offset) {
        requireNonNull(offset);
        checkArgument(isValidMonthOffset(offset), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(offset);
    }

    /**
     * Returns true if a given string is a valid offset.
     */
    public static boolean isValidMonthOffset(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isIgnored() {
        return value == -1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthOffset // instanceof handles nulls
                && value.equals(((MonthOffset) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
