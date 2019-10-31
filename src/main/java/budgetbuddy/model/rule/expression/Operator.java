package budgetbuddy.model.rule.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import budgetbuddy.logic.rules.RuleProcessor;

/**
 * Represents an Operator in an expression.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    // Predicate operators
    LESS_THAN("<", RuleProcessor.TYPE_AMOUNT),
    MORE_THAN(">", RuleProcessor.TYPE_AMOUNT),
    LESS_EQUAL("<=", RuleProcessor.TYPE_AMOUNT),
    MORE_EQUAL(">=", RuleProcessor.TYPE_AMOUNT),
    EQUAL_TO("=", RuleProcessor.TYPE_AMOUNT),
    CONTAINS("contains", RuleProcessor.TYPE_CATEGORY),

    // Action operators
    SET_CATEGORY("setcategory", RuleProcessor.TYPE_CATEGORY),
    SET_DESC("setdesc", RuleProcessor.TYPE_CATEGORY);

    public static final String MESSAGE_CONSTRAINTS =
            "Operators should be valid for their expression and not be blank\n"
            + "Valid operators: "
            + Arrays.stream(Operator.values())
                    .map(op -> op.representation)
                    .reduce((x, y) -> x + ", " + y)
                    .orElse("");

    private final String representation;
    private final Set<String> expectedTypes = new HashSet<>();

    Operator(String representation, String... expectedType) {
        this.representation = representation;
        this.expectedTypes.addAll(Arrays.asList(expectedType));
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
     * Returns an {@code Operator} given a valid representation.
     */
    public static Operator of(String rep) {
        return Arrays
                .stream(Operator.values())
                .filter(operator -> operator.representation.equals(rep.toLowerCase()))
                .findFirst()
                .get();
    }

    /**
     * Returns the set of types that this operator expect from its arguments.
     */
    public Set<String> getExpectedTypes() {
        return Collections.unmodifiableSet(expectedTypes);
    }

    @Override
    public String toString() {
        return representation;
    }
}
