package budgetbuddy.model.rule.expression;

import java.util.Arrays;
import java.util.Optional;

import budgetbuddy.logic.rules.RuleProcessingUtil;

/**
 * Represents a Attribute in an ExpressionPredicate
 * Guarantees: immutable; is valid as declared in {@link #isValidAttribute(String)}
 */
public enum Attribute {
    DESCRIPTION("description", RuleProcessingUtil.TYPE_DESC),
    AMOUNT("amount", RuleProcessingUtil.TYPE_AMOUNT),
    DATE("date", RuleProcessingUtil.TYPE_DATE);

    public static final String MESSAGE_CONSTRAINTS =
            "Attributes should be alphabetical and should not be blank";

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
     * Returns an {@code Optional<Attribute>} given a valid representation.
     */
    public static Optional<Attribute> of(String rep) {
        return Arrays
                .stream(Attribute.values())
                .filter(attribute -> attribute.representation.equals(rep.toLowerCase()))
                .findFirst();
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
