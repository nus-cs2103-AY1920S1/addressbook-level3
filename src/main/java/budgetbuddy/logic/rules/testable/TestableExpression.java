package budgetbuddy.logic.rules.testable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.rule.expression.Attribute;
import budgetbuddy.model.rule.expression.Value;

/**
 * Represents a predicate written as an expression.
 */
public abstract class TestableExpression implements Testable {
    protected final Attribute attribute;
    protected final Value value;
    protected final Logger logger = LogsCenter.getLogger(TestableExpression.class);

    /**
     * Constructs a TestableExpression given an attribute and a value.
     * The attribute and value must be able to evaluate to the same type.
     *
     * @param attribute the attribute to be tested with.
     * @param value the value to be tested against.
     */
    public TestableExpression(Attribute attribute, Value value) {
        requireAllNonNull(attribute, value);
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TestableExpression
                && attribute.equals(((TestableExpression) other).attribute))
                && value.equals(((TestableExpression) other).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }
}
