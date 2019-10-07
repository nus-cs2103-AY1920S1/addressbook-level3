package seedu.algobase.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Plan's description in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class PlanDescription {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and it should not be blank";
    public static final String DEFAULT_PLAN_DESCRIPTION_STRING = "";
    public static final PlanDescription DEFAULT_PLAN_DESCRIPTION = new PlanDescription();
    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code PlanDescription}.
     *
     * @param description A valid description.
     */
    public PlanDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    private PlanDescription() {
        value = DEFAULT_PLAN_DESCRIPTION_STRING;
    }

    /**
     * Returns true if a given string is a valid weblink.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlanDescription // instanceof handles nulls
                && value.equals(((PlanDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
