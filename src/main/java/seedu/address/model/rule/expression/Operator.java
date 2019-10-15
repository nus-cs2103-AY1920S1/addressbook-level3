package seedu.address.model.rule.expression;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.parser.RuleParserUtil;

/**
 * Represents an Operator in an ExpressionPredicate.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    LESS_THAN("<", RuleParserUtil.TYPE_LONG);

    public static final String MESSAGE_CONSTRAINTS =
            "Operators are restricted to only the ones already pre-defined "
            + "and should not be blank";

    private final String representation;
    private final String expectedType;

    Operator(String representation, String expectedType) {
        this.representation = representation;
        this.expectedType = expectedType;
    }

    /**
     * Returns true if a given string is a valid operator.
     */
    public static boolean isValidOperator(String test) {
        return Arrays
                .stream(Operator.values())
                .anyMatch(operator -> operator.representation.equals(test.toLowerCase()));
    }

    /**
     * Returns an {@code Optional<Operator>} given a representation.
     */
    public static Optional<Operator> of(String rep) {
        return Arrays
                .stream(Operator.values())
                .filter(operator -> operator.representation.equals(rep.toLowerCase()))
                .findFirst();
    }

    /**
     * Returns the type that this operator expects from its arguments.
     */
    public String getExpectedType() {
        return expectedType;
    }

    @Override
    public String toString() {
        return representation;
    }
}
