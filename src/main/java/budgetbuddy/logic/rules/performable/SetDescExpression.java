package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.model.Model;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set description expression.
 */
public class SetDescExpression extends PerformableExpression {

    /**
     * Constructs a SetDescExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetDescExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Transaction txn) {
        requireAllNonNull(model, txn);
        Description desc = new Description(value.toString());
    }
}
