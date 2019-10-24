package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * represents the description of a transaction
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String fullDescription;

    /**
     * Constructs a {@code Description}.
     *
     * @param fullDescription A valid description.
     */
    public Description(String fullDescription) {
        requireNonNull(fullDescription);
        checkArgument(isValidDescription(fullDescription), MESSAGE_CONSTRAINTS);
        this.fullDescription = fullDescription;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

}
