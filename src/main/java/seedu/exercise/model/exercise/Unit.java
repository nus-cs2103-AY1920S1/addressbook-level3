package seedu.exercise.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.ValidationRegex.ONLY_ALPHABETS;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

/**
 * Represents an Exercise's unit in the exercise book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit {
    public static final String PROPERTY_UNIT = "Unit";
    public static final String MESSAGE_CONSTRAINTS =
        "Units should only contain alphabets and it should not be blank";
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
        return test.matches(ONLY_ALPHABETS);
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
