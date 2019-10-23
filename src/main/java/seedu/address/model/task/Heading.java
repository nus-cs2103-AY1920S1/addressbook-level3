package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the heading (title) for a task.
 * They are also immutable and guaranteed to be valid as declared in {@link #isValidHeading(String)}.
 */
public class Heading {
    public static final String MESSAGE_CONSTRAINTS = "Headings should begin with a non-whitespace character "
            + "and not contain newlines or tabs, but are otherwise unrestricted";

    private static final String VALIDATION_REGEX = "\\S[ \\S]*";

    private final String heading;

    /**
     * Constructs a {@code Heading}.
     *
     * @param heading A valid heading.
     */
    public Heading(String heading) {
        requireNonNull(heading);
        checkArgument(isValidHeading(heading), MESSAGE_CONSTRAINTS);
        this.heading = heading;
    }

    public String getHeading() {
        return heading;
    }

    /**
     * Returns true if a given string is a valid heading.
     */
    public static boolean isValidHeading(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return heading;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Heading // instanceof handles nulls
                && heading.equals(((Heading) other).heading)); // state check
    }

    @Override
    public int hashCode() {
        return heading.hashCode();
    }
}
