package budgetbuddy.model.rule.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import budgetbuddy.logic.rules.RuleProcessingUtil;

/**
 * Represents an Operator in an expression.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperator(String)}
 */
public enum Operator {
    // Predicate operators
    LESS_THAN("<", RuleProcessingUtil.TYPE_NUMBER),
    MORE_THAN(">", RuleProcessingUtil.TYPE_NUMBER),
    LESS_EQUAL("<=", RuleProcessingUtil.TYPE_NUMBER),
    MORE_EQUAL(">=", RuleProcessingUtil.TYPE_NUMBER),
    EQUAL_TO("=", RuleProcessingUtil.TYPE_NUMBER),
    CONTAINS("contains", RuleProcessingUtil.TYPE_STRING, RuleProcessingUtil.TYPE_NUMBER),

    // Action operators
    SET_CATEGORY("setcategory", RuleProcessingUtil.TYPE_STRING),
    SET_DESC("setdesc", RuleProcessingUtil.TYPE_STRING);

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
