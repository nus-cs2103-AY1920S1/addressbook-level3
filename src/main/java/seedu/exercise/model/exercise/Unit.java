package seedu.exercise.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

/**
 * Represents an Exercise's unit in the exercise book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit extends Property {

    public static final String MESSAGE_CONSTRAINTS =
            "Units should only contain alphabets and spaces, and it should not be blank";

    /*
     * The first character of the unit must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[ A-Za-z]+$";

    public final String unit;

    /**
     * Constructs a {@code Unit}.
     *
     * @param unit A valid unit.
     */
    public Unit(String unit) {
        requireNonNull(unit);
        checkArgument(isValidUnit(unit), MESSAGE_CONSTRAINTS);
        this.unit = unit;
    }

    /**
     * Returns true if a given string is a valid unit.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return unit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Unit // instanceof handles nulls
                && unit.equals(((Unit) other).unit)); // state check
    }

    @Override
    public int hashCode() {
        return unit.hashCode();
    }

}
