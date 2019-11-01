package budgetbuddy.logic.rules.performable;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.model.Model;
import budgetbuddy.model.account.Account;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.rule.expression.Value;
import budgetbuddy.model.transaction.Transaction;

/**
 * Represents a switch direction expression.
 */
public class SwitchDirectionExpression extends PerformableExpression {

    /**
     * Constructs a SwitchDirectionExpression with the given value.
     *
     * @param value the value to perform the action with.
     */
    public SwitchDirectionExpression(Value value) {
        super(value);
    }

    @Override
    public void perform(Model model, Index txnIndex, Account account) {
        requireAllNonNull(model, txnIndex);

        Transaction toEdit = account.getTransaction(txnIndex);
        Direction updatedDirection = toEdit.getDirection().equals(Direction.IN) ? Direction.OUT : Direction.IN;

        Transaction updatedTransaction = new Transaction(toEdit.getDate(), toEdit.getAmount(), updatedDirection,
                toEdit.getDescription(), toEdit.getCategories());

        account.updateTransaction(txnIndex, updatedTransaction);

        logger.info("Rule Execution———Direction updated in:\n" + updatedTransaction);
    }
}
