package budgetbuddy.logic.rules.actionable;

import budgetbuddy.model.rule.expression.Value;

/**
 * Represents an action written as an expression.
 */
public abstract class ActionableExpression implements Actionable {
    protected final Value value;

    /**
     * Constructs an ActionableExpression given a value.
     *
     * @param value the value to perform the action with.
     */
    public ActionableExpression(Value value) {
        this.value = value;
    }
}
