package budgetbuddy.model.rule.expression;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Value in an expression
 * Guarantees: immutable; is valid as declared in {@link #isValidValue(String)}
 */
public class Value {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_CONSTRAINTS =
            "Values should either be numerical or contain alphanumeric characters and these special characters, "
            + "excluding the parentheses, (" + SPECIAL_CHARACTERS + "), "
            + "and should not be blank";

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
        return true;
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
