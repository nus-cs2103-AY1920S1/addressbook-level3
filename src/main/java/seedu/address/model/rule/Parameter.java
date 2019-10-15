package seedu.address.model.rule;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Parameter in an ExpressionPredicate
 * Guarantees: immutable; is valid as declared in {@link #isValidParameter(String)}
 */
public class Parameter {

    public final String value;

    /**
     * Constructs a {@code Parameter}.
     *
     * @param parameter A valid parameter.
     */
    public Parameter(String parameter) {
        requireNonNull(parameter);
        value = parameter;
    }

    /**
     * Returns true if a given string is a valid parameter.
     */
    public static boolean isValidParameter(String test) {
        return true;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Parameter // instanceof handles nulls
                && value.equals(((Parameter) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
