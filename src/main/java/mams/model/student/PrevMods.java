package mams.model.student;

import static java.util.Objects.requireNonNull;

import mams.commons.util.AppUtil;

/**
 * Represents a Student's prevMods in MAMS.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrevMods(String)}
 */
public class PrevMods {

    public static final String MESSAGE_CONSTRAINTS = "PrevMods should be comma seperated module codes ";
    public static final String VALIDATION_REGEX = "^([\\p{Alnum}&\\s]+,)+[\\p{Alnum}&\\s]+$";

    public final String value;

    /**
     * Constructs an {@code PrevMods}.
     *
     * @param prevMods A valid prevMods address.
     */
    public PrevMods(String prevMods) {
        requireNonNull(prevMods);
        AppUtil.checkArgument(isValidPrevMods(prevMods), MESSAGE_CONSTRAINTS);
        value = prevMods;
    }

    /**
     * Returns if a given string is a valid prevMods.
     */
    public static boolean isValidPrevMods(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrevMods // instanceof handles nulls
                && value.equals(((PrevMods) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
