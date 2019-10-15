package seedu.address.model.rule.expression;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * Represents a Attribute in an ExpressionPredicate
 * Guarantees: immutable; is valid as declared in {@link #isValidAttribute(String)}
 */
public enum Attribute {
    DESCRIPTION("description", String.class),
    AMOUNT("amount", Long.TYPE),
    DATE("date", Date.class);

    public static final String MESSAGE_CONSTRAINTS =
            "Attributes should be alphabetical and should not be blank";

    private final String representation;
    private final Type evaluatedType;

    Attribute(String representation, Type evaluatedType) {
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
    public Type getEvaluatedType() {
        return evaluatedType;
    }

    @Override
    public String toString() {
        return representation;
    }
}
