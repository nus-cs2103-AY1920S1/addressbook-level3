package seedu.address.model.incident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of the incident.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description can take any values, and it should not be blank";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String desc;

    /**
     * Creates a filled Description.
     * @param desc the description of the event filled in by the operator.
     */
    public Description(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.desc = desc;
    }

    /**
     * Creates a new Description that is empty.
     * Used to facilitate fast creation of incident reports, descriptions can be added during edits.
     */
    public Description() {
        this.desc = "";
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        boolean isEmptyDescription = test.isEmpty();
        return test.matches(VALIDATION_REGEX) && !isEmptyDescription;
    }

    @Override
    public String toString() {
        return desc;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        // state check
        Description e = (Description) other;

        return e.desc.equals(this.desc);
    }
}
