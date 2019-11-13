package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a set outward expression.
 */
public class SetOutwardExpression extends PerformableExpression {

    /**
     * Constructs a SetOutwardExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SetOutwardExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Index txnIndex, Account account) {
        requireAllNonNull(model, txnIndex);

        Transaction toEdit = account.getTransaction(txnIndex);
        Direction updatedDirection = Direction.OUT;

        Transaction updatedTransaction = new Transaction(toEdit.getLocalDate(), toEdit.getAmount(), updatedDirection,
                toEdit.getDescription(), toEdit.getCategories());

        account.updateTransaction(txnIndex, updatedTransaction);

        logger.info("Rule Execution———Direction updated in:\n" + updatedTransaction);
    }
}
