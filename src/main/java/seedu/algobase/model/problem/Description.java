package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's description in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and it should not be blank";
    public static final String DEFAULT_DESCRIPTION_STRING = "";
    public static final Description DEFAULT_DESCRIPTION = new Description();

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    private Description() {
        value = DEFAULT_DESCRIPTION_STRING;
    }

    /**
     * Returns true if a given string matches the default description string.
     */
    public static boolean isDefaultDescription(String test) {
        return test.equals(DEFAULT_DESCRIPTION_STRING);
    }

    /**
     * Returns true if a given {@code Description} is the default description.
     */
    public static boolean isDefaultDescription(Description test) {
        return test == DEFAULT_DESCRIPTION;
    }

    /**
     * Returns true if a given string is a valid description.
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
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
