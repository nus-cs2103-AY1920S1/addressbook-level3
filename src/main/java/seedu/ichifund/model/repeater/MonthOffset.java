package seedu.ichifund.model.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Month Offset in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MonthOffset {

    public static final String MESSAGE_CONSTRAINTS =
        "Month offset should be an integer between 1 and 28 inclusive. An empty month offset is also allowed.";
    public static final String VALIDATION_REGEX = "$[0-9]*^";

    private Integer offset;

    public MonthOffset(String offset) {
        requireNonNull(offset);
        checkArgument(isValidOffset(offset), MESSAGE_CONSTRAINTS);
        if (offset.equals("")) {
            this.offset = null;
        } else {
            this.offset = Integer.parseInt(offset);
        }
    }

    private MonthOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns true if a given string is a valid offset.
     */
    public static boolean isValidOffset(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        } else if (test.equals("")) {
            return true;
        } else {
            int offset = Integer.parseInt(test);
            return offset >= 0 && offset <= 28;
        }
    }

    public Integer getOffset() {
        return this.offset;
    }

    @Override
    public String toString() {
        if (this.offset == null) {
            return "none";
        } else {
            return this.offset.toString();
        }
    }

}
