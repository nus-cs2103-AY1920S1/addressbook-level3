package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's marking status in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidMark(String)}
 */
public class Marking {
    public static final String MESSAGE_CONSTRAINTS =
            "Marks should only be 'Y' or 'N'";

    private static boolean isMarked;
    public static String status;

    public Marking(String marking) {
        requireNonNull(marking);
        checkArgument(isValidMark(marking), MESSAGE_CONSTRAINTS);
        if (marking.equals("Y")) {
            isMarked = true;
        } else if (marking.equals("N")) {
            isMarked = false;
        }
        setStatus();
    }

    private void setStatus() {
        if (isMarked) {
            status = "marked";
        } else {
            status = "unmarked";
        }
    }

    /**
     * Returns true if a given string is a valid mark.
     */
    public static boolean isValidMark(String test) {
        return test.equals("Y") || test.equals("N");
    }

    @Override
    public String toString() {
        if (isMarked) {
            return "marked\n";
        } else {
            return "unmarked\n";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof Marking // instanceof handles nulls
                && ((Marking) other).isMarked == this.isMarked; // state check
    }
}
