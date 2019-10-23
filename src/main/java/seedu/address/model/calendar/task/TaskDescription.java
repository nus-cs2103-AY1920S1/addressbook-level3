package seedu.address.model.calendar.task;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class TaskDescription {

    //    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    // alphanumeric and special characters
    //    private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    //    private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]";
    // alphanumeric characters except underscore
    //    private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    //    private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";
    //    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@"
    //            + DOMAIN_FIRST_CHARACTER_REGEX + DOMAIN_MIDDLE_REGEX + DOMAIN_LAST_CHARACTER_REGEX;

    public static final String MESSAGE_CONSTRAINTS = "Description can take any value";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";


    public final String value;

    /**
     * Constructs an {@code TaskDescription}.
     *
     * @param description A valid description address.
     */
    public TaskDescription(String description) {
        // requireNonNull(description);
        checkArgument(isValidEmail(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && value.equals(((TaskDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
