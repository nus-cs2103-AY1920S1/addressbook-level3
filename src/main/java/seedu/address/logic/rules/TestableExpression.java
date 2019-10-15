package seedu.address.logic.rules;

import seedu.address.model.rule.expression.Attribute;
import seedu.address.model.rule.expression.Value;

/**
 * Represents a predicate written as an expression.
 */
public abstract class TestableExpression implements Testable {
    protected final Attribute attribute;
    protected final Value value;

    /**
     * Constructs a TestableExpression given an attribute and a value.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public TestableExpression(Attribute attribute, Value value) {
        this.attribute = attribute;
        this.value = value;
    }
}
