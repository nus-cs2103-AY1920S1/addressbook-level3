package budgetbuddy.logic.rules.performable;

import static java.util.Objects.requireNonNull;

import budgetbuddy.model.rule.expression.Value;

/**
 * Represents an action written as an expression.
 */
public abstract class PerformableExpression implements Performable {
    protected final Value value;

    /**
     * Constructs an PerformableExpression given a value.
     *
     * @param value the value to perform the action with.
     */
    public PerformableExpression(Value value) {
        requireNonNull(value);
        this.value = value;
    }
}
