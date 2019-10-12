package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.AppUtil.checkArgument;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Tutorial's Name.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TutName {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial name should not be blank and should not start or end with whitespace.";

    /*
     * The first character of the tutorial name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\S+.*\\S+$";

    public final String tutName;

    /**
     * Every field must be present and not null.
     */
    public TutName(String tutName) {
        requireAllNonNull(tutName);
        checkArgument(isValidTutName(tutName), MESSAGE_CONSTRAINTS);
        this.tutName = tutName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidTutName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return tutName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TutName // instanceof handles nulls
                && tutName.equalsIgnoreCase(((TutName) other).tutName)); // state check
    }

    @Override
    public int hashCode() {
        return tutName.hashCode();
    }

    public String toJsonString() {
        return tutName.toString();
    }

}
