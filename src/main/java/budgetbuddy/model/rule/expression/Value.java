package budgetbuddy.model.rule.expression;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Value in an expression
 * Guarantees: immutable; is valid as declared in {@link #isValidValue(String)}
 */
public class Value {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+=?`_/\\[{|}\\]~^.-";
    private static final int MAX_LENGTH = 180;
    public static final String MESSAGE_CONSTRAINTS =
            "Values, if present, should either be numerical, "
            + "or a string of alphanumeric characters and spaces, including these special characters,\n"
            + "excluding the parentheses, (" + SPECIAL_CHARACTERS.replace("\\", "") + "),\n"
            + "of length not more than " + MAX_LENGTH + " characters";
    private static final String VALIDATION_REGEX = "^[\\w\\s" + SPECIAL_CHARACTERS + "]{0," + MAX_LENGTH + "}$";

    private final String value;

    /**
     * Constructs a {@code Value}.
     *
     * @param value A valid value.
     */
    public Value(String value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid value.
     */
    public static boolean isValidValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Value // instanceof handles nulls
                && value.equals(((Value) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
