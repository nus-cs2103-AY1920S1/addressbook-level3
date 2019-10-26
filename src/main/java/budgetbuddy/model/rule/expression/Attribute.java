package budgetbuddy.model.rule.expression;

import java.util.Arrays;

import budgetbuddy.logic.rules.RuleProcessingUtil;

/**
 * Represents a Attribute in an PredicateExpression
 * Guarantees: immutable; is valid as declared in {@link #isValidAttribute(String)}
 */
public enum Attribute {
    DESCRIPTION("desc", RuleProcessingUtil.TYPE_STRING),
    OUT_AMOUNT("outamt", RuleProcessingUtil.TYPE_NUMBER),
    IN_AMOUNT("inamt", RuleProcessingUtil.TYPE_NUMBER),
    DATE("date", RuleProcessingUtil.TYPE_DATE);

    public static final String MESSAGE_CONSTRAINTS =
            "Attributes should be valid and not be blank\n"
            + "Valid attributes: "
                    + Arrays.stream(Attribute.values())
                    .map(op -> op.representation)
                    .reduce((x, y) -> x + ", " + y)
                    .orElse("");

    private final String representation;
    private final String evaluatedType;

    Attribute(String representation, String evaluatedType) {
        this.representation = representation;
        this.evaluatedType = evaluatedType;
    }

    /**
     * Returns true if a given string is a valid attribute.
     */
    public static boolean isValidAttribute(String test) {
        return Arrays
                .stream(Attribute.values())
                .anyMatch(attribute -> attribute.representation.equals(test.toLowerCase()));
    }

    /**
     * Returns an {@code Attribute} given a valid string representation.
     */
    public static Attribute of(String rep) {
        return Arrays
                .stream(Attribute.values())
                .filter(attribute -> attribute.representation.equals(rep.toLowerCase()))
                .findFirst()
                .get();
    }

    /**
     * Returns the type that this attribute will be evaluated to.
     */
    public String getEvaluatedType() {
        return evaluatedType;
    }

    @Override
    public String toString() {
        return representation;
    }
}
