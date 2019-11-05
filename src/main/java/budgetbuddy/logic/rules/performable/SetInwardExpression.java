package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set inward expression.
 */
public class SetInwardExpression extends PerformableExpression {

    /**
     * Constructs a SetInwardExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetInwardExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Index txnIndex, Account account) {
        requireAllNonNull(model, txnIndex);

        Transaction toEdit = account.getTransaction(txnIndex);
        Direction updatedDirection = Direction.IN;

        Transaction updatedTransaction = new Transaction(toEdit.getLocalDate(), toEdit.getAmount(), updatedDirection,
                toEdit.getDescription(), toEdit.getCategories());

        account.updateTransaction(txnIndex, updatedTransaction);

        logger.info("Rule Execution———Direction updated in:\n" + updatedTransaction);
    }
}
