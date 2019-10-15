package seedu.exercise.model.regime;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

import seedu.exercise.commons.core.ValidationRegex;

/**
 * Represents a Regime's name.
 */
public class RegimeName {

    public static final String MESSAGE_CONSTRAINTS =
            "Regime names should only contain alphabets, numbers and spaces, and it should not be blank";

    public final String name;

    public RegimeName(String name) {
        requireNonNull(name);
        checkArgument(isValidRegimeName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidRegimeName(String test) {
        return test.matches(ValidationRegex.ONLY_ALPHABETS_AND_NUMBERS);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegimeName // instanceof handles nulls
                && name.equals(((RegimeName) other).name)); // state check
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
