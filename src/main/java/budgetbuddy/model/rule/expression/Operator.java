package budgetbuddy.model.rule.expression;

import java.util.Arrays;
import java.util.Optional;

import budgetbuddy.logic.rules.RuleProcessingUtil;

/**
 * Represents an Operator in an expression.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    // Predicate operators
    LESS_THAN("<", RuleProcessingUtil.TYPE_AMOUNT),
    MORE_THAN(">", RuleProcessingUtil.TYPE_AMOUNT),
    LESS_EQUAL("<=", RuleProcessingUtil.TYPE_AMOUNT),
    MORE_EQUAL(">=", RuleProcessingUtil.TYPE_AMOUNT),
    EQUAL_TO("=", RuleProcessingUtil.TYPE_AMOUNT),
    CONTAINS("contains", RuleProcessingUtil.TYPE_DESC),

    // Action operators
    SET_CATEGORY("setcategory", RuleProcessingUtil.TYPE_CATEGORY),
    SET_DESC("setdesc", RuleProcessingUtil.TYPE_DESC);

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
