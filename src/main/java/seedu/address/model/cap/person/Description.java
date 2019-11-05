package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Description of a module in the CAP log.
 * Guarantees: immutable.
 */
public class Description {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String description;
    /**
     * Constructs a {@code Phone}.
     *
     * @param description A valid phone number.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Description// instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    @Override
    public String toString() {
        return description;
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid Description.
     */
    public static boolean isValidDescription(String test) {
        try {
            requireNonNull(test);
            checkArgument(isValidName(test));
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(test);
            Boolean noSpecialCharacter = !m.find();

            return noSpecialCharacter;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
