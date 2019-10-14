package seedu.address.model.rule;

import java.util.Arrays;

/**
 * Represents an Operator in an ExpressionPredicate.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    LESS_THAN("LESS THAN");

    private final String representation;

    Operator(String representation) {
        this.representation = representation;
    }

    /**
     * Returns true if a given string is a valid operator.
     */
    public static boolean isValidOperator(String test) {
        return Arrays
                .stream(Operator.values())
                .anyMatch(operator -> operator.representation.equals(test.toUpperCase()));
    }

    @Override
    public String toString() {
        return representation;
    }
}
