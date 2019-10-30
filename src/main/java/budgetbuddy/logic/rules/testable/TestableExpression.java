package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;

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
        requireAllNonNull(attribute, value);
        this.attribute = attribute;
        this.value = value;
    }
}
