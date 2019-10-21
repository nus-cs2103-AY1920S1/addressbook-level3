package budgetbuddy.logic.rules.actionable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.Model;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set description expression.
 */
public class SetDescExpression extends ActionableExpression {
    /**
     * Constructs a SetDescExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetDescExpression(Value value) {
        super(value);
    }

    @Override
    public String perform(Model model, Transaction txn) {
        requireAllNonNull(model, txn);
        // todo: set description of transaction in model
        return "";
    }
}
